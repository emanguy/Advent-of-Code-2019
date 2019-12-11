package io.cloudtamer.adventofcode.lib.day2

const val INTCODE_FALSE = 0L
const val INTCODE_TRUE = 1L

enum class ParameterMode {
    POSITION {
        override fun retrieveValue(memory: List<Long>, paramValue: Long, relativeBase: Int): Long {
            return memory.getOrElse(paramValue.toInt()) { 0L }
        }

        override fun getWriteTarget(paramValue: Int, relativeBase: Int): Int {
            return paramValue
        }
    }, // Interpret as position in memory
    IMMEDIATE {
        override fun retrieveValue(memory: List<Long>, paramValue: Long, relativeBase: Int): Long {
            return paramValue
        }

        override fun getWriteTarget(paramValue: Int, relativeBase: Int): Int {
            error("Immediate mode is invalid for write targets!")
        }
    }, // Interpret as literal value
    RELATIVE {
        override fun retrieveValue(memory: List<Long>, paramValue: Long, relativeBase: Int): Long {
            return memory.getOrElse(relativeBase + paramValue.toInt()) { 0L }
        }

        override fun getWriteTarget(paramValue: Int, relativeBase: Int): Int {
            return paramValue + relativeBase
        }
    };

    abstract fun retrieveValue(memory: List<Long>, paramValue: Long, relativeBase: Int ): Long
    abstract fun getWriteTarget(paramValue: Int, relativeBase: Int): Int
}

data class Opcode(
    val code: Int,
    val param1Mode: ParameterMode,
    val param2Mode: ParameterMode,
    val param3Mode: ParameterMode
) {
    companion object {
        fun fromMemory(memory: List<Long>, instructionPtr: Int): Opcode {
            val rawCode = memory[instructionPtr].toString()
            val correctlySizedCode = rawCode.padStart(5, '0')

            fun charToMode(input: Char) = when (input) {
                '0' -> ParameterMode.POSITION
                '1' -> ParameterMode.IMMEDIATE
                '2' -> ParameterMode.RELATIVE
                else -> error("Illegal memory mode!")
            }

            // Code is the last 2 digits of the instruction
            val code = correctlySizedCode.substring(3).toInt()
            // Param modes go right to left
            val param1Mode = charToMode(correctlySizedCode[2])
            val param2Mode = charToMode(correctlySizedCode[1])
            val param3Mode = charToMode(correctlySizedCode[0])
            return Opcode(code, param1Mode, param2Mode, param3Mode)
        }
    }
}

// Instruction width should always take the opcode into account
sealed class ParameterSet(val instructionWidth: Int)
data class OperationParams(val param1: Long, val param2: Long, val destination: Int) : ParameterSet(4) {
    companion object {
        fun fromMemory(memory: List<Long>, instructionPtr: Int) =
            OperationParams(memory[instructionPtr + 1], memory[instructionPtr + 2], memory[instructionPtr + 3].toInt())
    }
}
data class JumpParams(val param1: Long, val destination: Int) : ParameterSet(3) {
    companion object {
        fun fromMemory(memory: List<Long>, instructionPtr: Int) =
            JumpParams(memory[instructionPtr + 1], memory[instructionPtr + 2].toInt())
    }
}
data class IOParams(internal val memTarget: Long) : ParameterSet(2) {
    companion object {
        fun fromMemory(memory: List<Long>, instructionPtr: Int) =
            IOParams(memory[instructionPtr + 1])
    }
}
data class RelativeBaseParams(val relativeBaseAdjustment: Int) : ParameterSet(2) {
    companion object {
        fun fromMemory(memory: List<Long>, instructionPtr: Int) =
            RelativeBaseParams(memory[instructionPtr + 1].toInt())
    }
}

data class IntcodeOutput(
    val memory: List<Long>,
    val outputs: List<Long>,
    val remainingInputs: List<Long>,
    val instructionPtr: Int,
    val relativeBase: Int,
    val complete: Boolean = true
) {
    val firstOutput: Long?
        get() = outputs.firstOrNull()
}

/**
 * If the intended destination is within the list, returns the list. Otherwise,
 * returns the list sized to 2x the address of the intended destination padded with zeroes
 */
fun MutableList<Long>.atSafeSize(destination: Int): MutableList<Long> = when {
    this.size > destination -> this
    else -> MutableList(destination * 2) { index -> if (index < this.size) this[index] else 0 }
}

// Input formatted as OP, P1, P2, D (typically)
// OP = 1 -> D = P1 + P2
// OP = 2 -> D = P1 * P2
// OP = 3 -> Save user input to D
// OP = 4 -> Print memory value @ D
// OP = 5 -> If P1 != 0 jump to D
// OP = 6 -> If P1 == 0 jump to D
// OP = 7 -> If P1 < P2 D = 1 (true) else D = 0 (false)
// OP = 8 -> If P1 == P2 D = 1 (true) else D = 0 (false)
// OP = 9 -> Relative Base + P1
// OP = 99 -> QUIT
//
// May exit in one of three cases:
//   1. Ran out of inputs. Since this returns a snapshot of the memory and the instruction pointer, you can supply these back to this function to "resume". In this case the return will specify the program isn't complete.
//   2. Hit an EXIT instruction.
//   3. Was passed an instruction pointer out of range of the data.
fun processProgram(data: List<Long>, intcodeInputs: List<Long> = emptyList(), startAtInstruction: Int = 0, startingRelativeBase: Int = 0, debug: Boolean = false): IntcodeOutput {
    Int.MAX_VALUE
    var memory = data.toMutableList()
    val inputQueue = intcodeInputs.toMutableList()
    val outputs = mutableListOf<Long>()
    var instructionPointer = startAtInstruction
    var relativeBase = startingRelativeBase
    if (instructionPointer !in data.indices) return IntcodeOutput(memory, outputs, inputQueue, instructionPointer, relativeBase)

    evalLoop@ while (true) {
        val opcode = Opcode.fromMemory(memory, instructionPointer)
        var performedJump = false
        // For debugging
        val currentInstructionPointer = instructionPointer

        val genericInstruction: ParameterSet = when (opcode.code) {
            // ADD
            1 -> {
                val inputs = OperationParams.fromMemory(memory, instructionPointer)
                val writeTarget = opcode.param3Mode.getWriteTarget(inputs.destination, relativeBase)
                memory = memory.atSafeSize(writeTarget)
                memory[writeTarget] =
                    opcode.param1Mode.retrieveValue(memory, inputs.param1, relativeBase) +
                    opcode.param2Mode.retrieveValue(memory, inputs.param2, relativeBase)
                inputs
            }
            // MUL
            2 -> {
                val inputs = OperationParams.fromMemory(memory, instructionPointer)
                val writeTarget = opcode.param3Mode.getWriteTarget(inputs.destination, relativeBase)
                memory = memory.atSafeSize(writeTarget)
                memory[writeTarget] =
                    opcode.param1Mode.retrieveValue(memory, inputs.param1, relativeBase) *
                    opcode.param2Mode.retrieveValue(memory, inputs.param2, relativeBase)
                inputs
            }
            // Input
            3 -> {
                if (debug) println("Instruction pointer: $instructionPointer performing a read operation.")
                // Suspend the program if we need more input
                if (inputQueue.isEmpty()) {
                    return IntcodeOutput(memory, outputs, inputQueue, instructionPointer, relativeBase, false)
                }
                val userInput = inputQueue.removeAt(0)
                val programInput = IOParams.fromMemory(memory, instructionPointer)
                val realTarget = opcode.param1Mode.getWriteTarget(programInput.memTarget.toInt(), relativeBase)
                memory = memory.atSafeSize(realTarget)
                memory[realTarget] = userInput
                programInput
            }
            // Output
            4 -> {
                val input = IOParams.fromMemory(memory, instructionPointer)
                val toPrint = opcode.param1Mode.retrieveValue(memory, input.memTarget, relativeBase)
                outputs += toPrint
                input
            }
            // Jump if true
            5 -> {
                val input = JumpParams.fromMemory(memory, instructionPointer)
                if (opcode.param1Mode.retrieveValue(memory, input.param1, relativeBase) != INTCODE_FALSE) {
                    instructionPointer = opcode.param2Mode.retrieveValue(memory, input.destination.toLong(), relativeBase).toInt()
                    performedJump = true
                }
                input
            }
            // Jump if false (where true is nonzero)
            6 -> {
                val input = JumpParams.fromMemory(memory, instructionPointer)
                if (opcode.param1Mode.retrieveValue(memory, input.param1, relativeBase) == INTCODE_FALSE) {
                    instructionPointer = opcode.param2Mode.retrieveValue(memory, input.destination.toLong(), relativeBase).toInt()
                    performedJump = true
                }
                input
            }
            // Less than
            7 -> {
                val input = OperationParams.fromMemory(memory, instructionPointer)
                val valueOne = opcode.param1Mode.retrieveValue(memory, input.param1, relativeBase)
                val valueTwo = opcode.param2Mode.retrieveValue(memory, input.param2, relativeBase)
                val writeTarget = opcode.param3Mode.getWriteTarget(input.destination, relativeBase)
                memory = memory.atSafeSize(writeTarget)
                memory[writeTarget] = if (valueOne < valueTwo) INTCODE_TRUE else INTCODE_FALSE
                input
            }
            // Equals
            8 -> {
                val input = OperationParams.fromMemory(memory, instructionPointer)
                val valueOne = opcode.param1Mode.retrieveValue(memory, input.param1, relativeBase)
                val valueTwo = opcode.param2Mode.retrieveValue(memory, input.param2, relativeBase)
                val writeTarget = opcode.param3Mode.getWriteTarget(input.destination, relativeBase)
                memory = memory.atSafeSize(writeTarget)
                memory[writeTarget] = if (valueOne == valueTwo) INTCODE_TRUE else INTCODE_FALSE
                input
            }
            // Adjust relative base
            9 -> {
                val input = RelativeBaseParams.fromMemory(memory, instructionPointer)
                relativeBase += opcode.param1Mode.retrieveValue(memory, input.relativeBaseAdjustment.toLong(), relativeBase).toInt()
                input
            }
            // Exit
            99 -> break@evalLoop
            else -> throw IllegalArgumentException("Unrecognized opcode: $opcode! Instruction pointer @ $instructionPointer relative base @ $relativeBase")
        }

        if (debug) println("Instruction Pointer: $currentInstructionPointer Relative base pointer: $relativeBase Performing operation: $opcode with inputs: $genericInstruction")
        if (!performedJump) instructionPointer += genericInstruction.instructionWidth
        if (debug) println("Instruction pointer moved to $instructionPointer")
    }

    return IntcodeOutput(memory, outputs, inputQueue, instructionPointer, relativeBase)
}
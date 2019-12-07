package io.cloudtamer.adventofcode.lib.day2

const val INTCODE_FALSE = 0
const val INTCODE_TRUE = 1

enum class ParameterMode {
    POSITION {
        override fun retrieveValue(memory: List<Int>, paramValue: Int): Int {
            return memory[paramValue]
        }
    }, // Interpret as position in memory
    IMMEDIATE {
        override fun retrieveValue(memory: List<Int>, paramValue: Int): Int {
            return paramValue
        }
    }; // Interpret as literal value

    abstract fun retrieveValue(memory: List<Int>, paramValue: Int): Int
}
data class Opcode(
    val code: Int,
    val param1Mode: ParameterMode,
    val param2Mode: ParameterMode,
    val param3Mode: ParameterMode
) {
    companion object {
        fun fromMemory(memory: List<Int>, instructionPtr: Int): Opcode {
            val rawCode = memory[instructionPtr].toString()
            val correctlySizedCode = rawCode.padStart(5, '0')

            fun charToMode(input: Char) = if (input == '0') ParameterMode.POSITION else ParameterMode.IMMEDIATE

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
data class OperationParams(val param1: Int, val param2: Int, val destination: Int) : ParameterSet(4) {
    companion object {
        fun fromMemory(memory: List<Int>, instructionPtr: Int) =
            OperationParams(memory[instructionPtr + 1], memory[instructionPtr + 2], memory[instructionPtr + 3])
    }
}
data class JumpParams(val param1: Int, val destination: Int) : ParameterSet(3) {
    companion object {
        fun fromMemory(memory: List<Int>, instructionPtr: Int) =
            JumpParams(memory[instructionPtr + 1], memory[instructionPtr + 2])
    }
}
data class IOParams(val memTarget: Int) : ParameterSet(2) {
    companion object {
        fun fromMemory(memory: List<Int>, instructionPtr: Int) =
            IOParams(memory[instructionPtr + 1])
    }
}

data class IntcodeOutput(val memory: List<Int>, val outputs: List<Int>, val instructionPtr: Int, val complete: Boolean = true) {
    val firstOutput: Int?
        get() = outputs.firstOrNull()
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
// OP = 99 -> QUIT
//
// May exit in one of three cases:
//   1. Ran out of inputs. Since this returns a snapshot of the memory and the instruction pointer, you can supply these back to this function to "resume". In this case the return will specify the program isn't complete.
//   2. Hit an EXIT instruction.
//   3. Was passed an instruction pointer out of range of the data.
fun processProgram(data: List<Int>, intcodeInputs: List<Int> = emptyList(), startAtInstruction: Int = 0, debug: Boolean = false): IntcodeOutput {
    val memory = data.toMutableList()
    val inputQueue = intcodeInputs.toMutableList()
    val outputs = mutableListOf<Int>()
    var instructionPointer = startAtInstruction
    if (instructionPointer !in data.indices) return IntcodeOutput(memory, outputs, instructionPointer)

    evalLoop@ while (true) {
        val opcode = Opcode.fromMemory(memory, instructionPointer)
        var performedJump = false
        // For debugging
        val currentInstructionPointer = instructionPointer

        val genericInstruction: ParameterSet = when (opcode.code) {
            // ADD
            1 -> {
                val inputs = OperationParams.fromMemory(memory, instructionPointer)
                memory[inputs.destination] = opcode.param1Mode.retrieveValue(memory, inputs.param1) + opcode.param2Mode.retrieveValue(memory, inputs.param2)
                inputs
            }
            // MUL
            2 -> {
                val inputs = OperationParams.fromMemory(memory, instructionPointer)
                memory[inputs.destination] = opcode.param1Mode.retrieveValue(memory, inputs.param1) * opcode.param2Mode.retrieveValue(memory, inputs.param2)
                inputs
            }
            // Input
            3 -> {
                if (debug) println("Instruction pointer: $instructionPointer performing a read operation.")
                // Suspend the program if we need more input
                if (inputQueue.isEmpty()) {
                    return IntcodeOutput(memory, outputs, instructionPointer, false)
                }
                val userInput = inputQueue.removeAt(0)
                val programInput = IOParams.fromMemory(memory, instructionPointer)
                memory[programInput.memTarget] = userInput
                programInput
            }
            // Output
            4 -> {
                val input = IOParams.fromMemory(memory, instructionPointer)
                val toPrint = opcode.param1Mode.retrieveValue(memory, input.memTarget)
                outputs += toPrint
                input
            }
            // Jump if true
            5 -> {
                val input = JumpParams.fromMemory(memory, instructionPointer)
                if (opcode.param1Mode.retrieveValue(memory, input.param1) != INTCODE_FALSE) {
                    instructionPointer = opcode.param2Mode.retrieveValue(memory, input.destination)
                    performedJump = true
                }
                input
            }
            // Jump if false (where true is nonzero)
            6 -> {
                val input = JumpParams.fromMemory(memory, instructionPointer)
                if (opcode.param1Mode.retrieveValue(memory, input.param1) == INTCODE_FALSE) {
                    instructionPointer = opcode.param2Mode.retrieveValue(memory, input.destination)
                    performedJump = true
                }
                input
            }
            // Less than
            7 -> {
                val input = OperationParams.fromMemory(memory, instructionPointer)
                val valueOne = opcode.param1Mode.retrieveValue(memory, input.param1)
                val valueTwo = opcode.param2Mode.retrieveValue(memory, input.param2)
                memory[input.destination] = if (valueOne < valueTwo) INTCODE_TRUE else INTCODE_FALSE
                input
            }
            // Equals
            8 -> {
                val input = OperationParams.fromMemory(memory, instructionPointer)
                val valueOne = opcode.param1Mode.retrieveValue(memory, input.param1)
                val valueTwo = opcode.param2Mode.retrieveValue(memory, input.param2)
                memory[input.destination] = if (valueOne == valueTwo) INTCODE_TRUE else INTCODE_FALSE
                input
            }
            // Exit
            99 -> break@evalLoop
            else -> throw IllegalArgumentException("Unrecognized opcode: $opcode! Instruction pointer @ $instructionPointer")
        }

        if (debug) println("Instruction Pointer: $currentInstructionPointer Performing operation: $opcode with inputs: $genericInstruction")
        if (!performedJump) instructionPointer += genericInstruction.instructionWidth
        if (debug) println("Instruction pointer moved to $instructionPointer")
    }

    return IntcodeOutput(memory, outputs, instructionPointer)
}
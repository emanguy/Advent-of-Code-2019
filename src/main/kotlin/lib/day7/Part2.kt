package io.cloudtamer.adventofcode.lib.day7

import io.cloudtamer.adventofcode.lib.day2.IntcodeOutput
import io.cloudtamer.adventofcode.lib.day2.processProgram

fun main() {
    val program = listOf(
        3,26, // Input to 26
        1001,26,-4,26, // Subtract 4 from 26
        3,27, // Input to 27
        1002,27,2,27, // Multiply 27 by 2
        1,27,26,27, // Add 26 and 27
        4,27, // Output 27
        1001,28,-1,28, // Subtract 1 from 28
        1005,28,6, // If 28 != 0 jump to 6
        99,
        // 26, 27, and 28
        0,0,5)
    val result = optimizeProgram(program, listOf(5, 6, 7, 8, 9), ::feedbackAmplify)
    println(result)
}

fun feedbackAmplify(program: List<Int>, inputSet: List<Int>): Int {
    var mostRecentFinalAmpOutput = IntcodeOutput(emptyList(), emptyList(), 0, false)
    var mostRecentAmpOutput = IntcodeOutput(emptyList(), emptyList(), 0, false)
    // Get a separate memory space for each amplifier and places to store previous instruction pointer to pick up where we left off
    val amplifierMemories = inputSet.map { program.toList() }.toMutableList()
    val amplifierInstructionPointers = inputSet.map { 0 }.toMutableList()
    // First amplifier input is the first element of the input set, then zero
    val amplifierInputs = mutableListOf(listOf(inputSet[0], 0))
    // Next amplifier inputs are just their respective pieces of the input set because they just get the output of the previous amplifier
    amplifierInputs.addAll(inputSet.subList(1, inputSet.size).map { listOf(it) })


    while(!mostRecentFinalAmpOutput.complete) {
        for ((index, input) in amplifierInputs.withIndex()) {
            // Run and stash internal state
            mostRecentAmpOutput = processProgram(amplifierMemories[index], input, startAtInstruction = amplifierInstructionPointers[index])
            amplifierMemories[index] = mostRecentAmpOutput.memory
            amplifierInstructionPointers[index] = mostRecentAmpOutput.instructionPtr
            amplifierInputs[index] = emptyList()

            // Save output to next amplifier
            val nextAmpIndex = (index + 1) % amplifierInputs.size
            amplifierInputs[nextAmpIndex] = amplifierInputs[nextAmpIndex] + mostRecentAmpOutput.outputs
        }

        mostRecentFinalAmpOutput = mostRecentAmpOutput
    }

    return mostRecentFinalAmpOutput.outputs.last()
}
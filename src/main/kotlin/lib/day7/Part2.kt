package io.cloudtamer.adventofcode.lib.day7

import io.cloudtamer.adventofcode.lib.day2.IntcodeOutput
import io.cloudtamer.adventofcode.lib.day2.processProgram

fun feedbackAmplify(program: List<Int>, inputSet: List<Int>): Int {
    var mostRecentFinalAmpOutput = IntcodeOutput(emptyList(), emptyList(), emptyList(), 0, false)
    var mostRecentAmpOutput = IntcodeOutput(emptyList(), emptyList(), emptyList(), 0, false)
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
            amplifierInputs[index] = mostRecentAmpOutput.remainingInputs

            // Save output to next amplifier
            val nextAmpIndex = (index + 1) % amplifierInputs.size
            amplifierInputs[nextAmpIndex] = amplifierInputs[nextAmpIndex] + mostRecentAmpOutput.outputs
        }

        mostRecentFinalAmpOutput = mostRecentAmpOutput
    }

    return mostRecentFinalAmpOutput.outputs.last()
}
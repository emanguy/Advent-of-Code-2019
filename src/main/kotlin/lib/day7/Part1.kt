package io.cloudtamer.adventofcode.lib.day7

import io.cloudtamer.adventofcode.lib.day2.processProgram

data class OptimizedInput(val inputSet: List<Long>, val maxResult: Long)

fun optimizeProgram(
    program: List<Long>,
    possibleInputs: List<Long>,
    amplifyAlgorithm: (List<Long>, List<Long>) -> Long
): OptimizedInput {
    val inputPermutations = generatePermutations(possibleInputs)

    val maxInput = inputPermutations.maxBy { amplifyAlgorithm(program, it) } ?: throw IllegalStateException("Tried to find max on empty list!")
    val maxResult = amplifyAlgorithm(program, maxInput)

    return OptimizedInput(maxInput, maxResult)
}

fun amplify(program: List<Long>, inputSet: List<Long>): Long {
    var lastOutput = 0L
    for (input in inputSet) {
        lastOutput = processProgram(program, listOf(input, lastOutput)).firstOutput ?: throw IllegalStateException("Amplifier didn't produce output!")
    }
    return lastOutput
}

fun generatePermutations(toSelect: List<Long>, selected: List<Long> = emptyList()): List<List<Long>> {
    return if (toSelect.isEmpty()) {
        listOf(selected)
    } else {
        val permutations = mutableListOf<List<Long>>()
        for (option in toSelect) {
            permutations += generatePermutations(toSelect - option, selected + option)
        }
        permutations
    }
}



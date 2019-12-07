package io.cloudtamer.adventofcode.lib.day7

import io.cloudtamer.adventofcode.lib.day2.processProgram

data class OptimizedInput(val inputSet: List<Int>, val maxResult: Int)

fun optimizeProgram(
    program: List<Int>,
    possibleInputs: List<Int>,
    amplifyAlgorithm: (List<Int>, List<Int>) -> Int
): OptimizedInput {
    val inputPermutations = generatePermutations(possibleInputs)

    val maxInput = inputPermutations.maxBy { amplifyAlgorithm(program, it) } ?: throw IllegalStateException("Tried to find max on empty list!")
    val maxResult = amplifyAlgorithm(program, maxInput)

    return OptimizedInput(maxInput, maxResult)
}

fun amplify(program: List<Int>, inputSet: List<Int>): Int {
    var lastOutput = 0
    for (input in inputSet) {
        lastOutput = processProgram(program, listOf(input, lastOutput)).firstOutput ?: throw IllegalStateException("Amplifier didn't produce output!")
    }
    return lastOutput
}

fun generatePermutations(toSelect: List<Int>, selected: List<Int> = emptyList()): List<List<Int>> {
    return if (toSelect.isEmpty()) {
        listOf(selected)
    } else {
        val permutations = mutableListOf<List<Int>>()
        for (option in toSelect) {
            permutations += generatePermutations(toSelect - option, selected + option)
        }
        permutations
    }
}



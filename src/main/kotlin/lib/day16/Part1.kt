package io.cloudtamer.adventofcode.lib.day16

import kotlin.math.abs

fun applyFFT(inputs: List<Int>, generations: Int): List<Int> {
    var currentGeneration = inputs
    repeat(generations) {
        val nextGeneration = mutableListOf<Int>()

        for (index in currentGeneration.indices) {
            val patternIterator = getPattern(index + 1).drop(1).iterator()
            val total = currentGeneration.fold(0) { currentTotal, nextInGeneration ->
                currentTotal + patternIterator.next() * nextInGeneration
            }
            nextGeneration += abs(total % 10)
        }

        currentGeneration = nextGeneration
    }

    return currentGeneration
}

fun getPattern(repeatDigits: Int = 1) = sequence {
    val pattern = listOf(0, 1, 0, -1)
    var index = 0
    var timesRepeated = 0

    while (true) {
        yield(pattern[index])
        timesRepeated++

        if (timesRepeated == repeatDigits) {
            timesRepeated = 0
            index = (index + 1) % pattern.size
        }
    }
}
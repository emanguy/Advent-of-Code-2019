package io.cloudtamer.adventofcode.lib.day16

import kotlin.math.abs

fun getRealMessage(inputSequence: List<Int>): List<Int> {
    val offset = inputSequence.take(8).fold(0) { total, currentValue -> total * 10 + currentValue }
    val resultSequence = applyFFTForRealMessage(inputSequence, 100)
    return resultSequence.drop(offset).take(8)
}

fun applyFFTForRealMessage(inputs: List<Int>, generations: Int): List<Int> {
    var currentGeneration = inputs
    repeat(generations) {
        val nextGeneration = mutableListOf<Int>()

        repeat(currentGeneration.size * 10_000) { index ->
            val pattern = getPattern(index + 1).drop(1)
            val generatedCoefficients = pattern.take(10000)
                .chunked(currentGeneration.size)
                .fold(MutableList(currentGeneration.size) { 0 }) { coefficients, patternChunk ->
                    for ((patternIndex, patternValue) in patternChunk.withIndex()) {
                        coefficients[patternIndex] = coefficients[patternIndex] + patternValue
                    }
                    coefficients
                }
            println(generatedCoefficients)
            val coefficientPairs = generatedCoefficients.zip(currentGeneration)
            val total = coefficientPairs.fold(0) { currentTotal, (coefficient, generationValue) ->
                currentTotal + coefficient * generationValue
            }
            nextGeneration += abs(total % 10)
        }

        currentGeneration = nextGeneration
    }

    return currentGeneration
}

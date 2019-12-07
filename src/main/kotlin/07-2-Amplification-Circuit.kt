package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day7.feedbackAmplify
import io.cloudtamer.adventofcode.lib.day7.optimizeProgram
import java.io.File

fun main() {
    val inputProgram = File("inputs/07-1-input.txt").readText().split(",").map { it.toInt() }
    val bestOutput = optimizeProgram(inputProgram, listOf(5, 6, 7, 8, 9), ::feedbackAmplify)

    println("Highest output: ${bestOutput.maxResult}")
}
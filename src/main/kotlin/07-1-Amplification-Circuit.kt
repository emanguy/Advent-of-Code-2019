package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day7.amplify
import io.cloudtamer.adventofcode.lib.day7.optimizeProgram
import java.io.File

fun main() {
    val inputProgram = File("inputs/07-1-input.txt").readText().split(",").map { it.toLong() }
    val bestOutput = optimizeProgram(inputProgram, listOf(0, 1, 2, 3, 4), ::amplify)

    println("Highest output: ${bestOutput.maxResult}")
}
package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day2.processProgram
import java.io.File

fun main() {
    val input = File("inputs/05-1-input.txt").readText()
    val program = input.split(",").map { it.toLong() }

    val result = processProgram(program, intcodeInputs = listOf(5))
    println("Program outputs: ${result.outputs}")
}
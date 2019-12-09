package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day2.processProgram
import java.io.File

fun main() {
    val program = File("inputs/09-1-input.txt").readText().split(",").map { it.toLong() }

    val result = processProgram(program, intcodeInputs = listOf(1))

    println("Completed: ${result.complete}, output: ${result.outputs}")
}
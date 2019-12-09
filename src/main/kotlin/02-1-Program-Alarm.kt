package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day2.processProgram
import java.io.File

fun main() {
    val input = File("inputs/02-1-input.txt").readText()
    val program = input.split(",").map { it.toLong() }

    val result = processProgram(program)

    println(result.memory.joinToString(","))
}

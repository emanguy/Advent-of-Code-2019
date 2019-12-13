package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day13.outputToScreen
import io.cloudtamer.adventofcode.lib.day13.renderScreen
import io.cloudtamer.adventofcode.lib.day2.processProgram
import java.io.File

fun main() {
    val program = File("inputs/13-1-input.txt").readText().split(",").map { it.toLong() }

    val result = processProgram(program)
    val screen = outputToScreen(result.outputs.map { it.toInt() })
    renderScreen(screen)
}
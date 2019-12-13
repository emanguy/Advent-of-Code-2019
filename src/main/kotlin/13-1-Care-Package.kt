package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day13.countBlockTiles
import io.cloudtamer.adventofcode.lib.day2.processProgram
import java.io.File

fun main() {
    val program = File("inputs/13-1-input.txt").readText().split(",").map { it.toLong() }

    val result = processProgram(program)
    require(result.complete)
    val blockTiles = countBlockTiles(result.outputs.map { it.toInt() })

    println("# block tiles: $blockTiles")
}
package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day12.getEncompassingCycleCount
import io.cloudtamer.adventofcode.lib.day12.readPlanetInput
import java.io.File

fun main() {
    val rawInput = File("inputs/12-1-input.txt").readLines()
    val planets = readPlanetInput(rawInput)
    val fullCycleCount = getEncompassingCycleCount(planets)

    println("Cycles before repeat: $fullCycleCount")
}
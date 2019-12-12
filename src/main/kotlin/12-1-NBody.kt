package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day12.readPlanetInput
import io.cloudtamer.adventofcode.lib.day12.simulate
import io.cloudtamer.adventofcode.lib.day12.systemEnergy
import java.io.File

fun main() {
    val rawInput = File("inputs/12-1-input.txt").readLines()
    val planets = readPlanetInput(rawInput)
    val state = simulate(planets, 1000)
    val totalEnergy = systemEnergy(state)

    println("Total energy: $totalEnergy")
}
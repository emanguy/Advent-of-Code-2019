package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day6.PlanetInOrbit
import io.cloudtamer.adventofcode.lib.day6.getTotalOrbitCount
import java.io.File

fun main() {
    val input = File("inputs/06-1-input.txt").readLines().map {
        val orbitParts = it.split(")")
        PlanetInOrbit(orbitParts[0], orbitParts[1])
    }

    val totalOrbits = getTotalOrbitCount(input)
    println("Total orbits: $totalOrbits")
}
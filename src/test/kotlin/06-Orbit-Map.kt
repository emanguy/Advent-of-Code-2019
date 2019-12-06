package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day6.PlanetInOrbit
import io.cloudtamer.adventofcode.lib.day6.getOrbitPath
import io.cloudtamer.adventofcode.lib.day6.getTotalOrbitCount
import io.cloudtamer.adventofcode.lib.day6.orbitalTransfersBetweenYouAndSanta
import io.kotlintest.fail
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class Day6Tests : FunSpec() {
    init {
        val input = listOf(
            PlanetInOrbit("COM", "B"),
            PlanetInOrbit("B", "C"),
            PlanetInOrbit("C", "D"),
            PlanetInOrbit("D", "E"),
            PlanetInOrbit("E", "F"),
            PlanetInOrbit("B", "G"),
            PlanetInOrbit("G", "H"),
            PlanetInOrbit("D", "I"),
            PlanetInOrbit("E", "J"),
            PlanetInOrbit("J", "K"),
            PlanetInOrbit("K", "L")
        )
        val inputsWithYouAndSanta = input + PlanetInOrbit("K", "YOU") + PlanetInOrbit("I", "SAN")
        val inputMap = input.associateBy { it.planetName }

        test("Verify test inputs") {
            getTotalOrbitCount(input) shouldBe 42
        }

        test("Verify path function works") {
            val planetJ = inputMap["J"] ?: fail("Planet J wasn't in the map")
            getOrbitPath(inputMap, planetJ) shouldBe listOf("B", "C", "D", "E", "J")
        }

        test("Verify part 2 test input works") {
            orbitalTransfersBetweenYouAndSanta(inputsWithYouAndSanta) shouldBe 4
        }
    }
}
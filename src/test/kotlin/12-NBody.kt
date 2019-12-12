package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day12.*
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class Day12Tests : FunSpec() {
    init {
        val sampleInput = listOf(
            "<x=-1, y=0, z=2>",
            "<x=2, y=-10, z=-7>",
            "<x=4, y=-8, z=8>",
            "<x=3, y=5, z=-1>"
        )
        val secondExample = listOf(
            "<x=-8, y=-10, z=0>",
            "<x=5, y=5, z=10>",
            "<x=2, y=-7, z=3>",
            "<x=9, y=-8, z=-3>"
        )

        test("Verify input parser works") {
            readPlanetInput(sampleInput) shouldBe listOf(
                Planet(-1, 0, 2),
                Planet(2, -10, -7),
                Planet(4, -8, 8),
                Planet(3, 5, -1)
            )
        }

        test("Verify simulation works properly") {
            val planets = readPlanetInput(sampleInput)
            simulate(planets, 2) shouldBe listOf(
                Planet(
                    position = Vector3(5, -3, -1),
                    velocity = Vector3(3, -2, -2)
                ),
                Planet(
                    position = Vector3(1, -2, 2),
                    velocity = Vector3(-2, 5, 6)
                ),
                Planet(
                    position = Vector3(1, -4, -1),
                    velocity = Vector3(0, 3, -6)
                ),
                Planet(
                    position = Vector3(1, -4, 2),
                    velocity = Vector3(-1, -6, 2)
                )
            )
        }

        test("Verify sample inputs") {
            var planets = readPlanetInput(sampleInput)
            var state = simulate(planets, 10)
            systemEnergy(state) shouldBe 179

            planets = readPlanetInput(secondExample)
            state = simulate(planets, 100)
            systemEnergy(state) shouldBe 1940
        }

        test("Verify part 2") {
            var planets = readPlanetInput(sampleInput)
            var cyclesBeforeRepeat = getEncompassingCycleCount(planets)

            cyclesBeforeRepeat shouldBe 2772L

            planets = readPlanetInput(secondExample)
            cyclesBeforeRepeat = getEncompassingCycleCount(planets)

            cyclesBeforeRepeat shouldBe 4686774924L
        }
    }
}
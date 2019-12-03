package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day1.getFuelForMass
import io.cloudtamer.adventofcode.lib.day1.getTotalFuel
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class Day1Tests : FunSpec() {
    init {
        test("Verify outputs from getTotalFuel") {
            getTotalFuel(listOf(12)) shouldBe 2
            getTotalFuel(listOf(14)) shouldBe 2
            getTotalFuel(listOf(1969)) shouldBe 654
            getTotalFuel(listOf(100756)) shouldBe 33583
        }

        test("Verify correct amount of fuel for getFuelForMass") {
            getFuelForMass(14) shouldBe 2
            getFuelForMass(1969) shouldBe 966
            getFuelForMass(100756) shouldBe 50346
        }
    }
}
package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day2.processProgram
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class Day2Tests : FunSpec() {
    init {
        test("Verify sample programs") {
            processProgram(listOf(1, 0, 0, 0, 99)).memory shouldBe listOf<Long>(2, 0, 0, 0, 99)
            processProgram(listOf(2, 3, 0, 3, 99)).memory shouldBe listOf<Long>(2, 3, 0, 6, 99)
            processProgram(listOf(2, 4, 4, 5, 99, 0)).memory shouldBe listOf<Long>(2, 4, 4, 5, 99, 9801)
            processProgram(listOf(1, 1, 1, 4, 99, 5, 6, 0, 99)).memory shouldBe listOf<Long>(30, 1, 1, 4, 2, 5, 6, 0, 99)
        }
    }
}
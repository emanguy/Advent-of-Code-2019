package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day16.applyFFT
import io.cloudtamer.adventofcode.lib.day16.getPattern
import io.cloudtamer.adventofcode.lib.day16.getRealMessage
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class Day16Tests: FunSpec() {
    init {
        test("Pattern generation works") {
            getPattern(1).take(10).toList() shouldBe listOf(0, 1, 0, -1, 0, 1, 0, -1, 0, 1)
            getPattern(2).take(10).toList() shouldBe listOf(0, 0, 1, 1, 0, 0, -1, -1, 0, 0)
            getPattern(3).take(10).toList() shouldBe listOf(0, 0, 0, 1, 1, 1, 0, 0, 0, -1)
            getPattern(4).take(20).toList() shouldBe listOf(
                0, 0, 0, 0,
                1, 1, 1, 1,
                0, 0, 0, 0,
                -1, -1, -1, -1,
                0, 0, 0, 0
            )
        }

        test("Verify test inputs") {
            val firstInput = listOf(1, 2, 3, 4, 5, 6, 7, 8)
            applyFFT(firstInput, 4) shouldBe listOf(0, 1, 0, 2, 9, 4, 9, 8)

            val secondInput = "80871224585914546619083218645595".chunked(1).map { it.toInt() }
            applyFFT(secondInput, 100).take(8) shouldBe listOf(2, 4, 1, 7, 6, 1, 7, 6)

            val thirdInput = "19617804207202209144916044189917".chunked(1).map { it.toInt() }
            applyFFT(thirdInput, 100).take(8) shouldBe listOf(7, 3, 7, 4, 5, 4, 1, 8)

            val fourthInput = "69317163492948606335995924319873".chunked(1).map { it.toInt() }
            applyFFT(fourthInput, 100).take(8) shouldBe listOf(5, 2, 4, 3, 2, 1, 3, 3)
        }

        test("Verify part 2 inputs") {
            val firstInput = "03036732577212944063491565474664".chunked(1).map { it.toInt() }
            getRealMessage(firstInput) shouldBe listOf(8, 4, 4, 6, 2, 0, 2, 6)
        }
    }
}
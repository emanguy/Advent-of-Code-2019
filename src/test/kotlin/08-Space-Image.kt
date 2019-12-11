package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day8.Image
import io.cloudtamer.adventofcode.lib.day8.imageSplit
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class Day8Tests : FunSpec() {
    init {
        test("Flatten works on sample data") {
            val inputImage = "0222112222120000".chunked(1).map { it.toInt() }
            val layers = imageSplit(inputImage, 2, 2)
            val img = Image(layers, 2, 2)

            img.flatten() shouldBe listOf(
                listOf(0, 1),
                listOf(1, 0)
            )
        }
    }
}
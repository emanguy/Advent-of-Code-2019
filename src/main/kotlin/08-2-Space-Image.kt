package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day8.Image
import io.cloudtamer.adventofcode.lib.day8.imageSplit
import java.io.File

fun main() {
    val inputData = File("inputs/08-1-input.txt").readText().chunked(1).map { it.toInt() }
    val layers = imageSplit(inputData, 25, 6)
    val img = Image(layers, 25, 6)

    for (pixelRow in img.flatten()) {
        val renderedRow = pixelRow.map { if (it == 0) "■" else "□" }
        println(renderedRow.joinToString(""))
    }
}
package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day16.applyFFT
import java.io.File

fun main() {
    val inputSequence = File("inputs/16-1-input.txt").readText().chunked(1).map { it.toInt() }
    val resultSequence = applyFFT(inputSequence, 100)

    println("First 8 characters of resulting sequence: ${resultSequence.take(8)}")
}
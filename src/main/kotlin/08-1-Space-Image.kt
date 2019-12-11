package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day8.getOnesAndTwosFromLeastZeroesLayer
import java.io.File

fun main() {
    val inputData = File("inputs/08-1-input.txt").readText().chunked(1).map { it.toInt() }
    val answer = getOnesAndTwosFromLeastZeroesLayer(inputData, 25, 6)
    println("Ones multiplied by twos: $answer")
}
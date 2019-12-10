package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day10.maxVisibleAsteroids
import io.cloudtamer.adventofcode.lib.day10.parseVectorInput
import java.io.File

fun main() {
    val rawInput = File("inputs/10-1-input.txt").readLines()
    val input = parseVectorInput(rawInput)

    val result = maxVisibleAsteroids(input)

    println("Most visible asteroids: $result")
}
package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day10.getNthVaporizedAsteroid
import io.cloudtamer.adventofcode.lib.day10.parseVectorInput
import java.io.File

fun main() {
    val rawInput = File("inputs/10-1-input.txt").readLines()
    val input = parseVectorInput(rawInput)

    val result = getNthVaporizedAsteroid(input, 200)

    println("200th asteroid: $result")
}
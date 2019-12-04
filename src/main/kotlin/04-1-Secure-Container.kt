package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day4.validateNumber
import java.io.File

fun main() {
    val testInput = File("inputs/04-1-input.txt").readText().split("-").map { it.toInt() }

    val validInputs = (testInput[0]..testInput[1]).count { validateNumber(it) }

    println("Valid combinations: $validInputs")
}
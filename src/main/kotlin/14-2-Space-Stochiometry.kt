package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day14.getMaximumFuelOutput
import io.cloudtamer.adventofcode.lib.day14.parseTransforms
import java.io.File

fun main() {
    val input = parseTransforms(File("inputs/14-1-input.txt").readLines())
    val maximumProducibleFuel = getMaximumFuelOutput(input, 1_000_000_000_000)

    println("Max fuel: $maximumProducibleFuel")
}
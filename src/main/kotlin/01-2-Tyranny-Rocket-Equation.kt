package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day1.getTotalFuelIncludingFuel
import java.io.File

fun main() {
    val masses = mutableListOf<Int>()
    File("inputs/01-1-input.txt").forEachLine { masses += it.toInt() }

    val total = getTotalFuelIncludingFuel(masses)

    println("Total: $total")
}


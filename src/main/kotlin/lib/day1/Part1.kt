package io.cloudtamer.adventofcode.lib.day1

fun getTotalFuel(masses: List<Int>): Int {
    return masses.fold(0) { total, currentMass ->
        val fuelAmt = currentMass / 3 - 2
        total + fuelAmt
    }
}
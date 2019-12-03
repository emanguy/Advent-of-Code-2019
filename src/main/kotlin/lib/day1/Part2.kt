package io.cloudtamer.adventofcode.lib.day1

fun getTotalFuelIncludingFuel(masses: List<Int>): Int {
    return masses.fold(0) { totalFuel, currentMass -> totalFuel + getFuelForMass(currentMass) }
}

fun getFuelForMass(mass: Int): Int {
    var total = 0
    var workingMass = mass / 3 - 2
    while (workingMass > 0) {
        total += workingMass
        workingMass = workingMass / 3 - 2
    }

    return total
}
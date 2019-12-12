package io.cloudtamer.adventofcode.lib.day12

import kotlin.math.abs

data class PositionVelocityPair(val position: Int, val velocity: Int)

fun getCycleCount(planets: List<Planet>, getMeasurements: (Planet) -> PositionVelocityPair): Int {
    var currentState = planets
    var stepCount = 0
    val initialValues = planets.map(getMeasurements)

    while (true) {
        stepCount++
        currentState = simulate(currentState, 1)
        val dimensionValues = currentState.map(getMeasurements)
        if (dimensionValues == initialValues) break
    }

    return stepCount
}

fun getEncompassingCycleCount(planets: List<Planet>): Long {
    val cyclesX = getCycleCount(planets) { PositionVelocityPair(it.position.x, it.velocity.x) }
    val cyclesY = getCycleCount(planets) { PositionVelocityPair(it.position.y, it.velocity.y) }
    val cyclesZ = getCycleCount(planets) { PositionVelocityPair(it.position.z, it.velocity.z) }

    val xyLcm = lcmLong(cyclesX.toLong(), cyclesY.toLong())
    val xyzLcm = lcmLong(xyLcm, cyclesZ.toLong())

    return xyzLcm
}

tailrec fun gcdLong(a: Long, b: Long): Long = if (b == 0L) abs(a) else gcdLong(abs(b), abs(a % b))
fun lcmLong(a: Long, b: Long) = abs(a * b) / gcdLong(a, b)

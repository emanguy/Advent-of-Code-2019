package io.cloudtamer.adventofcode.lib.day10

import kotlin.math.PI

data class AsteroidClass(val identityVector: Vector2, val vectorsOfClass: List<Vector2>)

fun getNthVaporizedAsteroid(asteroids: List<Vector2>, asteroidN: Int): Vector2 {
    require(asteroids.size >= asteroidN)

    val asteroidVisibility = getAsteroidVisibility(asteroids)
    val (choiceAsteroidIndex, _) = asteroidVisibility.withIndex().maxBy { it.value } ?: error("No location for station!")
    val choiceAsteroid = asteroids[choiceAsteroidIndex]
    val otherAsteroids = asteroids - choiceAsteroid

    val classifiedAsteroids = otherAsteroids.groupBy({ (it - choiceAsteroid).identity() }, { it })
    var classes = classifiedAsteroids.entries
        .map { rawClass -> AsteroidClass(rawClass.key, rawClass.value.sortedBy { it.magnitude }) }
        .sortedBy { it.identityVector.angle }
        .toMutableList()

    // Ordering by angle goes counterclockwise from 0 to 360 degrees
    // We want clockwise from 90 degrees all the way around. This modifies the order to support that
    val (zeroToNinety, ninetyToThreeSixty) = classes.partition { it.identityVector.angle in 0.0..(PI / 2.0) }
    classes = (zeroToNinety.asReversed() + ninetyToThreeSixty.asReversed()).toMutableList()

    // N times, go to the next identity vector class and select the first one and drop it
    var nthBlownUp = Vector2(0, 0)
    var identityClassIdx = 0
    repeat(asteroidN) {
        // Skip this class if there are no vectors left in the class
        while (classes[identityClassIdx].vectorsOfClass.isEmpty()) {
            identityClassIdx = (identityClassIdx + 1) % classes.size
        }
        val currentVectorClass = classes[identityClassIdx]
        // Take the first (closest) vector
        nthBlownUp = currentVectorClass.vectorsOfClass.first()
        // Remove that vector from the class
        classes[identityClassIdx] = currentVectorClass.copy(vectorsOfClass = currentVectorClass.vectorsOfClass - nthBlownUp)


        identityClassIdx = (identityClassIdx + 1) % classes.size
    }

    return nthBlownUp
}
package io.cloudtamer.adventofcode.lib.day10

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sqrt

tailrec fun gcd(a: Int, b: Int): Int = if (b == 0) abs(a) else gcd(abs(b), abs(a % b))

data class Vector2(val x: Int, val y: Int) {
    val magnitude: Double by lazy { sqrt((x*x + y*y).toDouble()) }
    val angle: Double by lazy {
        val angle = atan2(y.toDouble(), x.toDouble())
        return@lazy if (angle < 0) angle + 2 * PI else angle
    }

    /**
     * Identity scales the vector down to its shortest length while the coordinates are still integers
     */
    fun identity(): Vector2 {
        val scalar = gcd(x, y)
        return Vector2(x / scalar, y / scalar)
    }

    operator fun plus(other: Vector2) = Vector2(x + other.x, y + other.y)
    operator fun minus(other: Vector2) = Vector2(x - other.x, y - other.y)
    operator fun times(scalar: Int) = Vector2(scalar * x, scalar * y)
}

/**
 * Returns a list of ints corresponding to the passed asteroid list, where each
 * int is the number of asteroids visible from that asteroid
 */
fun getAsteroidVisibility(asteroidPositions: List<Vector2>): List<Int> {
    val numVisibleAsteroids = mutableListOf<Int>()

    for (thisAsteroid in asteroidPositions) {
        val otherAsteroids = asteroidPositions - thisAsteroid
        val visibleAsteroids = mutableSetOf<Vector2>()

        for (otherAsteroid in otherAsteroids) {
            visibleAsteroids += (otherAsteroid - thisAsteroid).identity()
        }

        numVisibleAsteroids += visibleAsteroids.size
    }

    return numVisibleAsteroids
}

fun maxVisibleAsteroids(asteroidPositions: List<Vector2>) = getAsteroidVisibility(asteroidPositions).max()

fun parseVectorInput(inputLines: List<String>): List<Vector2> {
    val vectors = mutableListOf<Vector2>()
    for ((lineIdx, line) in inputLines.withIndex()) {
        for ((charIdx, character) in line.withIndex()) {
            if (character == '#') {
                vectors += Vector2(charIdx, -lineIdx)
            }
        }
    }

    return vectors
}
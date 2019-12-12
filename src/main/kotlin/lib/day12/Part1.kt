package io.cloudtamer.adventofcode.lib.day12

import kotlin.math.abs

data class Vector3(val x: Int, val y: Int, val z: Int) {
    constructor() : this(0, 0, 0)

    operator fun plus(other: Vector3) = Vector3(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vector3) = Vector3(x - other.x, y - other.y, z - other.z)
}

data class Planet(val position: Vector3, val velocity: Vector3 = Vector3()) {
    constructor(xPosition: Int, yPosition: Int, zPosition: Int) : this(Vector3(xPosition, yPosition, zPosition))

    val potentialEnergy: Int
        get() = abs(position.x) + abs(position.y) + abs(position.z)
    val kineticEnergy: Int
        get() = abs(velocity.x) + abs(velocity.y) + abs(velocity.z)
    val totalEnergy: Int
        get() = potentialEnergy * kineticEnergy

    infix fun withGravityFrom(other: Planet): Planet {
        val newVx = when {
            other.position.x > position.x -> velocity.x + 1
            other.position.x < position.x -> velocity.x - 1
            else -> velocity.x
        }
        val newVy = when {
            other.position.y > position.y -> velocity.y + 1
            other.position.y < position.y -> velocity.y - 1
            else -> velocity.y
        }
        val newVz = when {
            other.position.z > position.z -> velocity.z + 1
            other.position.z < position.z -> velocity.z - 1
            else -> velocity.z
        }

        return this.copy(velocity = Vector3(newVx, newVy, newVz))
    }

    fun moved() = this.copy(position = position + velocity)
}

fun readPlanetInput(rawInput: List<String>): List<Planet> {
    val planets = mutableListOf<Planet>()

    for (line in rawInput) {
        val (x, y, z) = line.removeSurrounding("<", ">").split(", ").map { it.substring(2).toInt() }
        planets += Planet(x, y, z)
    }

    return planets
}

fun simulate(planets: List<Planet>, iterations: Int): List<Planet> {
    var currentPlanetState = planets

    repeat(iterations) {
        val nextState = mutableListOf<Planet>()

        for (planet in currentPlanetState) {
            val otherPlanets = currentPlanetState - planet
            var updatedPlanet = planet

            for (otherPlanet in otherPlanets) {
                updatedPlanet = updatedPlanet withGravityFrom otherPlanet
            }

            nextState += updatedPlanet.moved()
        }

        currentPlanetState = nextState
    }

    return currentPlanetState
}

fun systemEnergy(planets: List<Planet>) = planets.sumBy { it.totalEnergy }

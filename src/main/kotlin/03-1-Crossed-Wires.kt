package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day3.determineClosestCollision
import io.cloudtamer.adventofcode.lib.day3.inputLineToPoints
import java.io.File

fun main() {
    val inputFile = File("inputs/03-1-input.txt")
    val (lineOne, lineTwo) = inputFile.readLines()

    val lineOneActualPoints = inputLineToPoints(lineOne)
    val lineTwoActualPoints = inputLineToPoints(lineTwo)

    val closestPoint = determineClosestCollision(lineOneActualPoints, lineTwoActualPoints)

    println("Closest collision to center: $closestPoint, distance ${closestPoint?.manhattanDist()}")
}

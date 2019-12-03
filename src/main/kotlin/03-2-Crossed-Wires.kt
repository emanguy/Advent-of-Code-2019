package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day3.getShortestPathPoint
import io.cloudtamer.adventofcode.lib.day3.inputLineToPoints
import java.io.File

fun main() {
    val lines = File("inputs/03-1-input.txt").readLines()

    val pointsA = inputLineToPoints(lines[0])
    val pointsB = inputLineToPoints(lines[1])

    val shortestPathPoint = getShortestPathPoint(pointsA, pointsB)

    println("The intersection with the shortest path is: $shortestPathPoint")
}

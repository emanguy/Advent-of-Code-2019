package io.cloudtamer.adventofcode.lib.day3

import kotlin.math.abs

data class Point(val x: Int, val y: Int) {
    fun manhattanDist(from: Point = Point( 0, 0 )): Int {
        val normalizedPoint = Point(
            this.x - from.x,
            this.y - from.y
        )
        return abs(normalizedPoint.x) + abs(normalizedPoint.y)
    }

    operator fun plus(instruction: Instruction): Point {
        val pointDiff = instruction.direction.change * instruction.distance
        return this + pointDiff
    }
    operator fun plus(difference: Point) = Point(x + difference.x, y + difference.y)
    operator fun times(scalar: Int) = Point(scalar * x, scalar * y)
}

enum class Direction(val change: Point) {
    UP(Point(0, 1)),
    DOWN(Point(0, -1)),
    LEFT(Point(-1, 0)),
    RIGHT(Point(1, 0))
}

data class Instruction(val distance: Int, val direction: Direction) {
    companion object {
        fun fromString(notatedString: String): Instruction {
            val directionStr = notatedString[0]
            val distance = notatedString.substring(1).toInt()
            val officialDirection = when (directionStr) {
                'U' -> Direction.UP
                'D' -> Direction.DOWN
                'L' -> Direction.LEFT
                'R' -> Direction.RIGHT
                else -> throw IllegalArgumentException("Bad direction $directionStr")
            }

            return Instruction(distance, officialDirection)
        }
    }
}

fun inputLineToPoints(commaSeparatedInstructions: String): List<Point> {
    val instructions = commaSeparatedInstructions.split(",").map { Instruction.fromString(it) }
    val pointList = mutableListOf(Point(0, 0))
    instructions.forEach { pointList += pointList.last() + it }

    return pointList
}

/**
 * determineClosestCollision assumes that vertical lines will only cross horizontal lines and vice versa,
 * then uses that to find the closest collision to the center via manhattan distance
 */
fun determineClosestCollision(pointsA: List<Point>, pointsB: List<Point>): Point? {
    val segmentsA = pointsA.windowed(2)
    val segmentsB = pointsB.windowed(2)

    val (verticalSegmentsA, horizontalSegmentsA) = segmentsA.partition { it[0].x == it[1].x }
    val (verticalSegmentsB, horizontalSegmentsB) = segmentsB.partition { it[0].x == it[1].x }

    fun getClosestCollisionToCenter(horizontalSegments: List<List<Point>>, verticalSegments: List<List<Point>>): Point? {
        var closestCollisionPoint: Point? = null
        var closestDistance = Int.MAX_VALUE
        for (horizontalSegment in horizontalSegments) {
            val orderedHoriz = horizontalSegment.sortedBy { it.x }
            for (verticalSegment in verticalSegments) {
                val orderedVert = verticalSegment.sortedBy { it.y }

                // The segments can only cross if:
                //  1. The horizontal segment's y position is between the y positions of the vertical segment
                //  2. The vertical segment's x position is between the x positions of the horizontal segment
                if (orderedHoriz[0].y > orderedVert[0].y && orderedHoriz[0].y < orderedVert[1].y &&
                        orderedVert[0].x > orderedHoriz[0].x && orderedVert[0].x < orderedHoriz[1].x) {
                    // The collision point will be at the horizontal line's y position and the vertical's x position
                    val collision = Point(orderedVert.first().x, orderedHoriz.first().y)
                    val manhattanDistance = collision.manhattanDist()

                    // Only save the new point if it's closer to the center
                    if (manhattanDistance <= closestDistance) {
                        closestCollisionPoint = collision
                        closestDistance = manhattanDistance
                    }
                }
            }
        }

        return closestCollisionPoint
    }

    val closestA = getClosestCollisionToCenter(horizontalSegmentsA, verticalSegmentsB)
    val closestB = getClosestCollisionToCenter(horizontalSegmentsB, verticalSegmentsA)

    if (closestA == null && closestB == null) return null

    val aDistance = closestA?.manhattanDist() ?: Int.MAX_VALUE
    val bDistance = closestB?.manhattanDist() ?: Int.MAX_VALUE
    return if (aDistance < bDistance) closestA else closestB
}
package io.cloudtamer.adventofcode.lib.day3

/**
 * DistanceSegment contains the points for a line segment plus the total distance traveled
 * in the path so far
 */
data class DistanceSegment(val previousDistance: Int, val lineSegment: List<Point>)

/**
 * DistancePoint contains a point and the distance traveled to get to that point
 */
data class DistancePoint(val totalDistanceToPoint: Int, val point: Point)

fun List<Point>.toSegments(): List<DistanceSegment> {
    // Start with the first segment. No distance has occurred yet.
    val segments = mutableListOf<DistanceSegment>()

    for (pointIdx in 1..this.lastIndex) {
        // Previous distance should be the distance covered in the last segment plus last segment's previous distance
        val previousDistance = if (segments.isEmpty()) {
            0
        } else {
            val lastSegment = segments.last()
            lastSegment.lineSegment[0].manhattanDist(from = lastSegment.lineSegment[1]) + lastSegment.previousDistance
        }

        // Make sure to place the points in the segment in the order they appear
        segments += DistanceSegment(
            previousDistance,
            listOf(this[pointIdx - 1], this[pointIdx])
        )
    }

    return segments
}

fun getShortestPathPoint(pointsA: List<Point>, pointsB: List<Point>): DistancePoint? {
    val segmentsA = pointsA.toSegments()
    val segmentsB = pointsB.toSegments()

    val (vertSegmentsA, horizSegmentsA) = segmentsA.partition { it.lineSegment[0].x == it.lineSegment[1].x }
    val (vertSegmentsB, horizSegmentsB) = segmentsB.partition { it.lineSegment[0].x == it.lineSegment[1].x }

    val allIntersectionsWithDistances = getIntersectionsWithDistance(horizSegmentsA, vertSegmentsB) +
            getIntersectionsWithDistance(horizSegmentsB, vertSegmentsA)
    return allIntersectionsWithDistances.minBy { it.totalDistanceToPoint }
}

fun getIntersectionsWithDistance(horizontalSegments: List<DistanceSegment>, verticalSegments: List<DistanceSegment>): List<DistancePoint> {
    val shortestDistancePoints = mutableListOf<DistancePoint>()
    for (horizontalSegment in horizontalSegments) {
        val orderedHoriz = horizontalSegment.lineSegment.sortedBy { it.x }
        for (verticalSegment in verticalSegments) {
            val orderedVert = verticalSegment.lineSegment.sortedBy { it.y }

            // The segments can only cross if:
            //  1. The horizontal segment's y position is between the y positions of the vertical segment
            //  2. The vertical segment's x position is between the x positions of the horizontal segment
            if (orderedHoriz[0].y > orderedVert[0].y && orderedHoriz[0].y < orderedVert[1].y &&
                orderedVert[0].x > orderedHoriz[0].x && orderedVert[0].x < orderedHoriz[1].x
            ) {
                // The collision point will be at the horizontal line's y position and the vertical's x position
                val collision = Point(orderedVert.first().x, orderedHoriz.first().y)
                // The collision's distance will be the distance traveled to get up to each segment
                // plus the distance between the segment's initial point and the collision point
                val horizToPoint = horizontalSegment.lineSegment[0].manhattanDist(from = collision)
                val vertToPoint = verticalSegment.lineSegment[0].manhattanDist(from = collision)
                val collisionDistance = horizToPoint + horizontalSegment.previousDistance + vertToPoint + verticalSegment.previousDistance
                shortestDistancePoints += DistancePoint(collisionDistance, collision)
            }
        }
    }

    return shortestDistancePoints
}
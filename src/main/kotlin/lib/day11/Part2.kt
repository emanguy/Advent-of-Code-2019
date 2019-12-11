package io.cloudtamer.adventofcode.lib.day11

import io.cloudtamer.adventofcode.lib.day3.Point

fun printRegistration(tiles: Map<Point, Int>) {
    val minX = tiles.keys.map { it.x }.min() ?: error("No min x")
    val maxX = tiles.keys.map { it.x }.max() ?: error("No max x")
    val minY = tiles.keys.map { it.y }.min() ?: error("No min y")
    val maxY = tiles.keys.map { it.y }.max() ?: error("No max y")

    for (y in maxY downTo minY) {
        for (x in minX..maxX) {
            val tileValue = tiles.getOrDefault(Point(x, y), 0)
            if (tileValue == 0) print("■") else print("□")
        }
        println()
    }
}
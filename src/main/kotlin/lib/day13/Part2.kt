package io.cloudtamer.adventofcode.lib.day13

import io.cloudtamer.adventofcode.lib.day3.Point

fun renderScreen(screen: Map<Point, Tile>) {
    val minX = screen.keys.map { it.x }.min() ?: error("No min x")
    val minY = screen.keys.map { it.y }.min() ?: error("No min y")
    val maxX = screen.keys.map { it.x }.max() ?: error("No max x")
    val maxY = screen.keys.map { it.y }.max() ?: error("No max y")

    for (yCoordinate in minY..maxY) {
        for (xCoordinate in minX..maxX) {
            when (screen.getOrDefault(Point(xCoordinate, yCoordinate), Tile.EMPTY)) {
                Tile.EMPTY -> print("_")
                Tile.PADDLE -> print("\uD83C\uDFD3")
                Tile.BALL -> print("⚽")
                Tile.BLOCK -> print("\uD83C\uDF6B")
                Tile.WALL -> print("\uD83E\uDDF1")
            }
        }
        println()
    }
}
package io.cloudtamer.adventofcode.lib.day13

import io.cloudtamer.adventofcode.lib.day3.Point

enum class Tile {
    EMPTY, WALL, BLOCK, PADDLE, BALL;

    companion object {
        fun fromCode(code: Int) = when (code) {
            0 -> EMPTY
            1 -> WALL
            2 -> BLOCK
            3 -> PADDLE
            4 -> BALL
            else -> error("Unrecognized tile code!")
        }
    }
}

fun countBlockTiles(programOutput: List<Int>): Int {
    val screen = outputToScreen(programOutput)

    return screen.values.count { it == Tile.BLOCK }
}

fun outputToScreen(programOutput: List<Int>): Map<Point, Tile> {
    val screen = mutableMapOf<Point, Tile>()
    for (pixelToDraw in programOutput.chunked(3)) {
        val (x, y, tileCode) = pixelToDraw
        screen[Point(x, y)] = Tile.fromCode(tileCode)
    }

    return screen
}
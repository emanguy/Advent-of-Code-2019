package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day11.EmergencyHullPaintRobot
import io.cloudtamer.adventofcode.lib.day3.Point
import java.io.File

fun main() {
    val intcodeProgram = File("inputs/11-1-input.txt").readText().split(",").map { it.toLong() }
    val robot = EmergencyHullPaintRobot(intcodeProgram)

    val paintState = mutableMapOf<Point, Int>()
    var robotDone = false

    while (!robotDone) {
        val currentColor = paintState.getOrDefault(robot.position, 0)
        val robotAction = robot.scanAndMove(currentColor)

        paintState[robotAction.locationToPaint] = robotAction.colorToPaint
        robotDone = robotAction.programComplete
    }

    println("# Panels Painted: ${paintState.size}")
}
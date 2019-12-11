package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day11.EmergencyHullPaintRobot
import io.cloudtamer.adventofcode.lib.day11.printRegistration
import io.cloudtamer.adventofcode.lib.day3.Point
import java.io.File

fun main() {
    val intcodeProgram = File("inputs/11-2-input.txt").readText().split(",").map { it.toLong() }
    val robot = EmergencyHullPaintRobot(intcodeProgram)

    val paintState = mutableMapOf<Point, Int>(Point(0, 0) to 1)
    var robotDone = false

    while (!robotDone) {
        val currentColor = paintState.getOrDefault(robot.position, 0)
        val robotAction = robot.scanAndMove(currentColor)

        paintState[robotAction.locationToPaint] = robotAction.colorToPaint
        robotDone = robotAction.programComplete
    }

    printRegistration(paintState)
}
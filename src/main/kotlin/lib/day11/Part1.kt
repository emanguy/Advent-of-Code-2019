package io.cloudtamer.adventofcode.lib.day11

import io.cloudtamer.adventofcode.lib.day2.processProgram
import io.cloudtamer.adventofcode.lib.day3.Direction
import io.cloudtamer.adventofcode.lib.day3.Point

data class RobotAction(val colorToPaint: Int, val locationToPaint: Point, val programComplete: Boolean)

class EmergencyHullPaintRobot(private var intcodeMemory: List<Long>) {
    var position = Point(0, 0)
        private set
    var direction = Direction.UP
        private set

    private var intcodeInstructionPtr = 0
    private var intcodeRelativeBase = 0

    /**
     * ScanAndMove reads the current color, runs the intcode program,
     * then paints, rotates, and moves based on the output.
     *
     * @return True if the intcode program is complete
     */
    fun scanAndMove(currentColor: Int): RobotAction {
        val result = processProgram(
            intcodeMemory,
            intcodeInputs = listOf(currentColor.toLong()),
            startAtInstruction = intcodeInstructionPtr,
            startingRelativeBase = intcodeRelativeBase
        )

        intcodeMemory = result.memory
        intcodeInstructionPtr = result.instructionPtr
        intcodeRelativeBase = result.relativeBase

        val lastPosition = position
        direction = if (result.outputs[1] == 0L) direction.turnLeft() else direction.turnRight()
        position += direction

        return RobotAction(result.outputs[0].toInt(), lastPosition, result.complete)
    }
}
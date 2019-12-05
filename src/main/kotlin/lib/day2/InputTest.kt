package io.cloudtamer.adventofcode.lib.day2

@Suppress("UNUSED_VARIABLE")
fun main() {
    val echoProgram = listOf(
        3, 0,
        4, 0,
        99)
    val equals8ProgramPositionMode = listOf(
        3, 9,
        8, 9, 10, 9,
        4, 9,
        99,
        // Data
        -1, 8)
    val lessThan8ProgramPositionMode = listOf(
        3, 9,
        7, 9, 10, 9,
        4, 9,
        99,
        // Data
        -1, 8)
    val equals8ProgramImmediateMode = listOf(
        3, 3,
        1108, -1, 8, 3,
        4, 3,
        99)
    val lessThan8ProgramImmediateMode = listOf(
        3, 3,
        1107, -1, 8, 3,
        4, 3,
        99
    )
    val jumpOnNonZeroPositionMode = listOf(
        3, 12,
        6, 12, 15,
        1, 13, 14, 13,
        4, 13,
        99,
        // Data
        -1,0,1,9
    )
    val jumpOnNonZeroImmediateMode = listOf(
        3, 3,
        1105, -1, 9,
        1101, 0, 0, 12,
        4, 12,
        99,
        // Data
        1
    )
    val allAroundEight = listOf(
        3, 21,
        1008, 21, 8, 20,
        1005, 20, 22,
        107, 8, 21, 20,
        1006, 20, 31,
        1106, 0, 36,
        // Data begin
        98, 0, 0,
        // Data end
        1002, 21, 125, 20,
        4, 20,
        1105, 1, 46,
        104, 999,
        1105, 1, 46,
        1101, 1000, 1, 20,
        4, 20,
        1105, 1, 46,
        // Data begin
        98,
        // Data end
        99
    )

    processProgram(allAroundEight, debug = false)
}
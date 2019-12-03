package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day2.processProgram
import java.io.File

fun main() {
    val input = File("inputs/02-1-input.txt").readText()
    val program = input.split(",").map { it.toInt() }.toMutableList()

    val target = 19690720
    var lastResult = 0
    var input1 = 0
    var input2 = 0
    testLoop@ while (true) {
        program[1] = input1
        program[2] = input2

        val currentResult = processProgram(program)[0]

        when {
            currentResult == target -> break@testLoop
            currentResult < target -> {
                input1++
                lastResult = currentResult
            }
            currentResult > target -> {
                input1--
                input2 = target - lastResult
                break@testLoop
            }
        }
    }

    program[1] = input1
    program[2] = input2
    val result = processProgram(program)[0]
    println("f($input1, $input2) = $result")
}
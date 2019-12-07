package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day2.*
import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import io.kotlintest.tables.row

class Day5Tests : FunSpec() {
    init {
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

        test("Verify opcodes are read correctly") {
            Opcode.fromMemory(listOf(1001), 0) shouldBe Opcode(
                1,
                ParameterMode.POSITION,
                ParameterMode.IMMEDIATE,
                ParameterMode.POSITION
                )
            Opcode.fromMemory(listOf(99), 0) shouldBe Opcode(
                99,
                ParameterMode.POSITION,
                ParameterMode.POSITION,
                ParameterMode.POSITION
            )
            Opcode.fromMemory(listOf(11111), 0) shouldBe Opcode(
                11,
                ParameterMode.IMMEDIATE,
                ParameterMode.IMMEDIATE,
                ParameterMode.IMMEDIATE
            )
            Opcode.fromMemory(listOf(5, 1111), 1) shouldBe Opcode(
                11,
                ParameterMode.IMMEDIATE,
                ParameterMode.IMMEDIATE,
                ParameterMode.POSITION
            )
        }

        test("Verify sample programs") {
            processProgram(echoProgram, intcodeInputs = listOf(5)).outputs shouldBe listOf(5)

            forall(
                row(equals8ProgramPositionMode),
                row(equals8ProgramImmediateMode)
            ) { program ->
                processProgram(program, listOf(8)).firstOutput shouldBe INTCODE_TRUE
                processProgram(program, listOf(4)).firstOutput shouldBe INTCODE_FALSE
            }
            forall(
                row(lessThan8ProgramPositionMode),
                row(lessThan8ProgramImmediateMode)
            ) { program ->
                processProgram(program, listOf(3)).firstOutput shouldBe INTCODE_TRUE
                processProgram(program, listOf(7)).firstOutput shouldBe INTCODE_TRUE
                processProgram(program, listOf(8)).firstOutput shouldBe INTCODE_FALSE
                processProgram(program, listOf(15)).firstOutput shouldBe INTCODE_FALSE
            }
            forall(
                row(jumpOnNonZeroPositionMode),
                row(jumpOnNonZeroImmediateMode)
            ) { program ->
                processProgram(program, listOf(0)).firstOutput shouldBe 0
                processProgram(program, listOf(1)).firstOutput shouldBe 1
                processProgram(program, listOf(5)).firstOutput shouldBe 1
            }
            forall(
                row(7, 999),
                row(8, 1000),
                row(9, 1001)
            ) { input, expectedOutput ->
                processProgram(allAroundEight, listOf(input)).firstOutput shouldBe expectedOutput
            }
        }
    }
}
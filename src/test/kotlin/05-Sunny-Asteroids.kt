package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day2.*
import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import io.kotlintest.tables.row

class Day5Tests : FunSpec() {
    init {
        val echoProgram = listOf(
            3L, 0L,
            4L, 0L,
            99L)
        val equals8ProgramPositionMode = listOf(
            3L, 9L,
            8L, 9L, 10L, 9L,
            4L, 9L,
            99L,
            // Data
            -1L, 8L)
        val lessThan8ProgramPositionMode = listOf(
            3L, 9L,
            7L, 9L, 10L, 9L,
            4L, 9L,
            99L,
            // Data
            -1L, 8L)
        val equals8ProgramImmediateMode = listOf(
            3L, 3L,
            1108L, -1L, 8L, 3L,
            4L, 3L,
            99L)
        val lessThan8ProgramImmediateMode = listOf(
            3L, 3L,
            1107L, -1L, 8L, 3L,
            4L, 3L,
            99L
        )
        val jumpOnNonZeroPositionMode = listOf(
            3L, 12L,
            6L, 12L, 15L,
            1L, 13L, 14L, 13L,
            4L, 13L,
            99L,
            // Data
            -1L,0L,1L,9L
        )
        val jumpOnNonZeroImmediateMode = listOf(
            3L, 3L,
            1105L, -1L, 9L,
            1101L, 0L, 0L, 12L,
            4L, 12L,
            99L,
            // Data
            1L
        )
        val allAroundEight = listOf(
            3L, 21L,
            1008L, 21L, 8L, 20L,
            1005L, 20L, 22L,
            107L, 8L, 21L, 20L,
            1006L, 20L, 31L,
            1106L, 0L, 36L,
            // Data begin
            98L, 0L, 0L,
            // Data end
            1002L, 21L, 125L, 20L,
            4L, 20L,
            1105L, 1L, 46L,
            104L, 999L,
            1105L, 1L, 46L,
            1101L, 1000L, 1L, 20L,
            4L, 20L,
            1105L, 1L, 46L,
            // Data begin
            98L,
            // Data end
            99L
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
            processProgram(echoProgram, intcodeInputs = listOf(5)).outputs shouldBe listOf<Long>(5)

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
                row(7L, 999),
                row(8L, 1000),
                row(9L, 1001)
            ) { input, expectedOutput ->
                processProgram(allAroundEight, listOf(input)).firstOutput shouldBe expectedOutput
            }
        }
    }
}
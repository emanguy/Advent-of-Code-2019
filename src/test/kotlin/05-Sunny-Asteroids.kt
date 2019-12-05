package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day2.Opcode
import io.cloudtamer.adventofcode.lib.day2.ParameterMode
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class Day5Tests : FunSpec() {
    init {
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
    }
}
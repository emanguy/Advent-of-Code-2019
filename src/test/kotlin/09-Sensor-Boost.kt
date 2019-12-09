package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day2.processProgram
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class Day9Tests : FunSpec() {
    init {
        test("Verify day 9 sample programs") {
            val selfCopyProgram = listOf<Long>(109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99)
            var result = processProgram(selfCopyProgram)
            result.outputs shouldBe selfCopyProgram
            result.complete shouldBe true

            val bigNumberOutputProgram = listOf<Long>(1102,34915192,34915192,7,4,7,99,0)
            result = processProgram(bigNumberOutputProgram)
            result.complete shouldBe true
            result.firstOutput.toString().length shouldBe 16

            val printLargeNumber = listOf<Long>(104,1125899906842624,99)
            result = processProgram(printLargeNumber)
            result.firstOutput shouldBe printLargeNumber[1]
        }
    }
}
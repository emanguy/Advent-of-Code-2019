package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day4.validateNumber
import io.cloudtamer.adventofcode.lib.day4.validateNumberWithRepeatRestrictions
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class Day4Tests : FunSpec() {
    init {
        test("Test validation function") {
            // Validate sample inputs
            validateNumber(111111) shouldBe true
            validateNumber(223450) shouldBe false
            validateNumber(123789) shouldBe false

            // My edge cases
            // Repeat at beginning
            validateNumber(112345) shouldBe true
            // Repeat at end
            validateNumber(123455) shouldBe true
            // Decrease at beginning
            validateNumber(545567) shouldBe false
            // Decrease at end
            validateNumber(455676) shouldBe false
        }

        test("Test restricted validation function") {
            // Validate sample inputs
            validateNumberWithRepeatRestrictions(112233) shouldBe true
            validateNumberWithRepeatRestrictions(123444) shouldBe false
            validateNumberWithRepeatRestrictions(111122) shouldBe true

            validateNumberWithRepeatRestrictions(111223) shouldBe true
            validateNumberWithRepeatRestrictions(111222) shouldBe false
            validateNumberWithRepeatRestrictions(223333) shouldBe true
        }
    }
}
package io.cloudtamer.adventofcode.lib.day4

/**
 * Criteria:
 *  - Six digit number
 *  - 2 adjacent digits are the same
 *  - Digits never decrease going left-to-right
 */
fun validateNumber(number: Int): Boolean {
    val numString = number.toString()
    if (numString.length != 6) return false

    var foundAdjacent = false
    var highestDigit = numString[0]
    for (index in 1..numString.lastIndex) {
        // Decrease check
        if (numString[index] < highestDigit) return false
        if (numString[index] > highestDigit) highestDigit = numString[index]
        if (numString[index - 1] == numString[index]) foundAdjacent = true
    }

    // At this point, all the other checks pass so it reduces to whether or not we found an adjacent pair
    return foundAdjacent
}
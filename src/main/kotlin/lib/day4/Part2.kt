package io.cloudtamer.adventofcode.lib.day4

/**
 * Criteria:
 *  - Six digit number
 *  - at least one set of exactly 2 adjacent digits are the same
 *  - Digits never decrease going left-to-right
 */
fun validateNumberWithRepeatRestrictions(number: Int): Boolean {
    val numString = number.toString()
    if (numString.length != 6) return false

    var foundAdjacent = false
    var adjacentCount = 0
    var highestDigit = numString[0]
    for (index in 1..numString.lastIndex) {
        // Decrease check
        if (numString[index] < highestDigit) return false
        if (numString[index] > highestDigit) highestDigit = numString[index]

        // Adjacency check
        if (numString[index - 1] == numString[index]) {
            adjacentCount++
        } else {
            if (adjacentCount == 1) {
                foundAdjacent = true
            }
            adjacentCount = 0
        }
    }

    // Handle the edge case where the pair is at the very end
    if (adjacentCount == 1) foundAdjacent = true

    // At this point, all the other checks pass so it reduces to whether or not we found an adjacent pair
    return foundAdjacent
}
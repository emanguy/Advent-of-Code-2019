package io.cloudtamer.adventofcode.lib.day2

// Input formatted as OP, P1, P2, D
// OP = 1 -> D = P1 + P2
// OP = 2 -> D = P1 * P2
// OP = 99 -> QUIT
fun processProgram(data: List<Int>): List<Int> {
    val scratchSpace = data.toMutableList()

    for (index in scratchSpace.indices step 4) {
        val opcode = scratchSpace[index]
        if (opcode == 99) break
        val param1 = scratchSpace[index + 1]
        val param2 = scratchSpace[index + 2]
        val destination = scratchSpace[index + 3]

        when (opcode) {
            1 -> scratchSpace[destination] = scratchSpace[param1] + scratchSpace[param2]
            2 -> scratchSpace[destination] = scratchSpace[param1] * scratchSpace[param2]
        }
    }

    return scratchSpace
}
package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day14.getOresRequiredToMakeFuelWithWaste
import io.cloudtamer.adventofcode.lib.day14.parseTransforms
import java.io.File

fun main() {
    val input = parseTransforms(File("inputs/14-1-input.txt").readLines())
    val oreRequired = getOresRequiredToMakeFuelWithWaste(input)

    println("Ore required: $oreRequired")
}
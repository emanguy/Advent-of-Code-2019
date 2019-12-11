package io.cloudtamer.adventofcode.lib.day8

// Split the image into layers
fun imageSplit(imageData: List<Int>, width: Int, height: Int) = imageData.chunked(width * height)

fun getFewestZeroDigitLayer(layers: List<List<Int>>): Int {
    var fewestZeroesSeen = Int.MAX_VALUE
    var layerIndexWithFewestZeroes = -1

    for ((index, layer) in layers.withIndex()) {
        val numberBuckets = countDigits(layer)

        val zeroesSeen = numberBuckets.getOrDefault(0, 0)
        if (zeroesSeen < fewestZeroesSeen) {
            fewestZeroesSeen = zeroesSeen
            layerIndexWithFewestZeroes = index
        }
    }

    return layerIndexWithFewestZeroes
}

fun countDigits(layer: List<Int>): Map<Int, Int> {
    return layer.fold(mutableMapOf<Int, Int>()) { numberBuckets, number ->
        val numAlreadySeen = numberBuckets.getOrDefault(number, 0)
        numberBuckets[number] = numAlreadySeen + 1
        numberBuckets
    }
}

// Could be named better but solves the problem
fun getOnesAndTwosFromLeastZeroesLayer(imageData: List<Int>, width: Int, height: Int): Int {
    val layers = imageSplit(imageData, width, height)
    val targetLayer = getFewestZeroDigitLayer(layers)
    val digitCounts = countDigits(layers[targetLayer])

    return checkNotNull(digitCounts[1]) * checkNotNull(digitCounts[2])
}
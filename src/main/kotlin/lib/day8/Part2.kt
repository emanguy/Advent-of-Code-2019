package io.cloudtamer.adventofcode.lib.day8

class Image(private val layers: List<List<Int>>, val width: Int, val height: Int) {
    // Flatten returns the image as a list of lists of pixel rows
    fun flatten(): List<List<Int>> {
        val imageData = mutableListOf<Int>()
        for (imgY in 0 until height) {
            for (imgX in 0 until width) {
                imageData += pixelAt(imgX, imgY)
            }
        }

        return imageData.chunked(width)
    }

    fun pixelAt(x: Int, y: Int): Int {
        val layerPosition = x + y * width
        var pixelValue = 2
        var layerIdx = 0

        while (pixelValue == 2) {
            pixelValue = layers[layerIdx][layerPosition]
            layerIdx++
        }

        return pixelValue
    }
}
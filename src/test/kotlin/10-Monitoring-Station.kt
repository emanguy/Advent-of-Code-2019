package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day10.*
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class Day10Tests : FunSpec() {
    init {
        val sampleInput = listOf(
            ".#..#",
            ".....",
            "#####",
            "....#",
            "...##"
        )

        test("Verify GCD works") {
            gcd(10, 5) shouldBe 5
            gcd(5, 10) shouldBe 5
            gcd(12, 18) shouldBe 6
            gcd(11, 13) shouldBe 1
        }

        test("Verify vector operations") {
            Vector2(1, 2) * 5 shouldBe Vector2(5, 10)
            Vector2(1, 2) + Vector2 (3, 4) shouldBe Vector2(4, 6)
            Vector2(3, 4) - Vector2(1, 2) shouldBe Vector2(2, 2)
            Vector2(6, 12).identity() shouldBe Vector2(1, 2)

            Vector2(2, 4).identity() shouldBe Vector2(4, 8).identity()
        }

        test("Verify input parser") {
            parseVectorInput(sampleInput) shouldBe listOf(
                Vector2(1, 0), Vector2(4, 0),
                Vector2(0, -2), Vector2(1, -2), Vector2(2, -2), Vector2(3, -2), Vector2(4, -2),
                Vector2(4, -3),
                Vector2(3, -4), Vector2(4, -4)
            )
        }

        test("Verify test inputs - part 1") {
            val parsedSampleInput = parseVectorInput(sampleInput)
            maxVisibleAsteroids(parsedSampleInput) shouldBe 8

            val larger1 = parseVectorInput(listOf(
                "......#.#.",
                "#..#.#....",
                "..#######.",
                ".#.#.###..",
                ".#..#.....",
                "..#....#.#",
                "#..#....#.",
                ".##.#..###",
                "##...#..#.",
                ".#....####"
            ))
            maxVisibleAsteroids(larger1) shouldBe 33

            val larger2Text = listOf(
                "#.#...#.#.",
                ".###....#.",
                ".#....#...",
                "##.#.#.#.#",
                "....#.#.#.",
                ".##..###.#",
                "..#...##..",
                "..##....##",
                "......#...",
                ".####.###."
            )
            val larger2Input = parseVectorInput(larger2Text)
            maxVisibleAsteroids(larger2Input) shouldBe 35

            val larger3Text = listOf(
                ".#..#..###",
                 "####.###.#",
                 "....###.#.",
                 "..###.##.#",
                 "##.##.#.#.",
                 "....###..#",
                 "..#.#..#.#",
                 "#..#.#.###",
                 ".##...##.#",
                 ".....#.#.."
            )
            val larger3Input = parseVectorInput(larger3Text)
            maxVisibleAsteroids(larger3Input) shouldBe 41
        }

        test("Verify test inputs - part 2") {
            val example = listOf(
                ".#....#####...#..",
                "##...##.#####..##",
                "##...#...#.#####.",
                "..#.....#...###..",
                "..#.#.....#....##"
            )
            val parsedSampleInput = parseVectorInput(example)
            getNthVaporizedAsteroid(parsedSampleInput, 18) shouldBe Vector2(4, -4)
            val largeInput = listOf(
                ".#..##.###...#######",
                "##.############..##.",
                ".#.######.########.#",
                ".###.#######.####.#.",
                "#####.##.#.##.###.##",
                "..#####..#.#########",
                "####################",
                "#.####....###.#.#.##",
                "##.#################",
                "#####.##.###..####..",
                "..######..##.#######",
                "####.##.####...##..#",
                ".#####..#.######.###",
                "##...#.##########...",
                "#.##########.#######",
                ".####.#.###.###.#.##",
                "....##.##.###..#####",
                ".#.#.###########.###",
                "#.#.#.#####.####.###",
                "###.##.####.##.#..##"
            )
            val inputs = parseVectorInput(largeInput)
            getNthVaporizedAsteroid(inputs, 200) shouldBe Vector2(8, -2)
        }
    }
}
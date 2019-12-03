package io.cloudtamer.adventofcode

import io.cloudtamer.adventofcode.lib.day3.*
import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import io.kotlintest.tables.row

class Day3Tests : FunSpec() {
    init {
        test("Verifying manhattan distances") {
            // Origin point = 0,0
            forall(
                row(1, 2),
                row(-1, 2),
                row(2, -1),
                row(-1, -2)
            ) { a, b ->
                Point(a, b).manhattanDist() shouldBe 3
            }

            // vs. 1,1
            val oneOne = Point(1, 1)
            forall(
                row(1, 2, 1),
                row(-1, 2, 3),
                row(1, -2, 3),
                row(-1, -2, 5)
            ) { a, b, result ->
                Point(a, b).manhattanDist(from = oneOne) shouldBe result
                oneOne.manhattanDist(from = Point(a, b)) shouldBe result
            }
        }

        test("Verifying instructions") {
            val origin = Point(1, 2)
            forall(
                row(
                    Instruction(5, Direction.UP),
                    Point(1, 7)
                ),
                row(
                    Instruction(5, Direction.DOWN),
                    Point(1, -3)
                ),
                row(
                    Instruction(5, Direction.LEFT),
                    Point(-4, 2)
                ),
                row(
                    Instruction(5, Direction.RIGHT),
                    Point(6, 2)
                )
            ) { instruction, destination ->
                origin + instruction shouldBe destination
            }
        }

        test("Verify input processing") {
            inputLineToPoints("U1,U1,D1,D1,L1,R1,L20,R19") shouldBe listOf(
                Point(0, 0),
                Point(0, 1),
                Point(0, 2),
                Point(0, 1),
                Point(0, 0),
                Point(-1, 0),
                Point(0, 0),
                Point(-20, 0),
                Point(-1, 0)
            )
        }

        test("Test against sample problems - Part 1") {
            // Problem 1
            val problem1A = inputLineToPoints("R75,D30,R83,U83,L12,D49,R71,U7,L72")
            val problem1B = inputLineToPoints("U62,R66,U55,R34,D71,R55,D58,R83")

            val closestPointProblem1 = determineClosestCollision(problem1A, problem1B)
            closestPointProblem1?.manhattanDist() shouldBe 159

            // Problem 2
            val problem2A = inputLineToPoints("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51")
            val problem2B = inputLineToPoints("U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")

            val closestPointProblem2 = determineClosestCollision(problem2A, problem2B)
            closestPointProblem2?.manhattanDist() shouldBe 135
        }

        test("Test segment conversion") {
            val points = inputLineToPoints("U1,U1,D1,D1,L1,R1,L20,R19")
            val segments = points.toSegments()

            segments shouldBe listOf(
                // Up one
                DistanceSegment( 0, listOf(
                        Point(0, 0),
                        Point(0, 1)
                    )
                ),
                // Up one
                DistanceSegment( 1, listOf(
                        Point(0, 1),
                        Point(0, 2)
                    )
                ),
                // Down one
                DistanceSegment( 2, listOf(
                        Point(0, 2),
                        Point(0, 1)
                    )
                ),
                // Down one
                DistanceSegment( 3, listOf(
                        Point(0, 1),
                        Point(0, 0)
                    )
                ),
                // Left one
                DistanceSegment( 4, listOf(
                        Point(0, 0),
                        Point(-1, 0)
                    )
                ),
                // Right one
                DistanceSegment( 5, listOf(
                        Point(-1, 0),
                        Point(0, 0)
                    )
                ),
                // Left 20
                DistanceSegment( 6, listOf(
                        Point(0, 0),
                        Point(-20, 0)
                    )
                ),
                // Right 19
                DistanceSegment( 26, listOf(
                        Point(-20, 0),
                        Point(-1, 0)
                    )
                )
            )
        }

        test("Test sample problems - part 2") {

            val problem1A = inputLineToPoints("R75,D30,R83,U83,L12,D49,R71,U7,L72")
            val problem1B = inputLineToPoints("U62,R66,U55,R34,D71,R55,D58,R83")

            val closestPointProblem1 = getShortestPathPoint(problem1A, problem1B)
            closestPointProblem1?.totalDistanceToPoint shouldBe 610

            // Problem 2
            val problem2A = inputLineToPoints("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51")
            val problem2B = inputLineToPoints("U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")

            val closestPointProblem2 = getShortestPathPoint(problem2A, problem2B)
            closestPointProblem2?.totalDistanceToPoint shouldBe 410
        }
    }
}
package io.github.nillkki.aoc

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup

@State(Scope.Benchmark)
@Warmup(iterations = 1)
@Measurement(iterations = 3)
class Day02 {
    companion object {
        fun part1(input: List<String>): Int {
            fun scoreChosenMove(chosenMove: Char) = when (chosenMove) {
                'X' -> 1
                'Y' -> 2
                'Z' -> 3
                else -> {
                    error("Unknown chosen move")
                }
            }

            fun scoreMovePair(movePair: Pair<Char, Char>) = when (movePair) {
                'A' to 'Y', 'B' to 'Z', 'C' to 'X' -> 6 // Win
                'A' to 'X', 'B' to 'Y', 'C' to 'Z' -> 3 // Draw
                'A' to 'Z', 'B' to 'X', 'C' to 'Y' -> 0

                else -> {
                    error("Unknown move pair")
                }
            }

            return input.sumOf { line ->
                val opponentsMove = line[0]
                val chosenMove = line[2]
                scoreChosenMove(chosenMove) + scoreMovePair(opponentsMove to chosenMove)
            }
        }

        fun part2(input: List<String>): Int {
            fun scoreChosenMove(
                endResult: Char,
                opponentsMove: Char,
            ): Int = when (opponentsMove to endResult) {
                'B' to 'X', 'A' to 'Y', 'C' to 'Z' -> 1
                'C' to 'X', 'B' to 'Y', 'A' to 'Z' -> 2
                'A' to 'X', 'C' to 'Y', 'B' to 'Z' -> 3

                else -> {
                    error("Unknown input")
                }
            }

            fun scoreResult(endResult: Char) = when (endResult) {
                'X' -> 0
                'Y' -> 3 // Draw
                'Z' -> 6 // Win

                else -> {
                    error("Unknown endResult")
                }
            }

            return input.sumOf { line ->
                val opponentsMove = line[0]
                val endResult = line[2]
                scoreChosenMove(endResult, opponentsMove) + scoreResult(endResult)
            }
        }
    }

    private var input = emptyList<String>()

    @Setup
    fun setUp() {
        input = readInput("Day02")
    }

    @Benchmark
    public fun part1Bench() {
        Day02.part1(input)
    }

    @Benchmark
    public fun part2Bench() {
        Day02.part2(input)
    }
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(Day02.part1(testInput) == 15)
    check(Day02.part2(testInput) == 12)

    val input = readInput("Day02")
    println(Day02.part1(input))
    println(Day02.part2(input))
}

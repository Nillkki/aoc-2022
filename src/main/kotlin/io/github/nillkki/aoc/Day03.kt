package io.github.nillkki.aoc

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup

private const val UPPER_CASE_DIFF = 38

private const val LOWER_CASE_DIFF = 96

@State(Scope.Benchmark)
@Warmup(iterations = 1)
@Measurement(iterations = 3)
class Day03 {
    companion object {
        fun getPriorityValue(c: Char): Int {
            if (c.isUpperCase()) {
                return c.code - UPPER_CASE_DIFF
            }
            return c.code - LOWER_CASE_DIFF
        }

        fun part1(input: List<String>): Int {
            return input.sumOf { s ->
                val halves = s.splitInHalf().toList()
                halves.reappearingChars().sumOf {
                    getPriorityValue(it)
                }
            }
        }

        fun part2(input: List<String>): Int {
            // Split elves into groups of three
            val groups = input.chunked(3)

            return groups.sumOf { group ->
                group.reappearingChars().sumOf { getPriorityValue(it) }
            }
        }
    }

    private var input = emptyList<String>()

    @Setup
    fun setUp() {
        input = readInput("Day03")
    }

    @Benchmark
    public fun part1Bench() {
        Day03.part1(input)
    }

    @Benchmark
    public fun part2Bench() {
        Day03.part2(input)
    }
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(Day03.part1(testInput) == 157)
    check(Day03.part2(testInput) == 70)

    val input = readInput("Day03")
    println(Day03.part1(input))
    println(Day03.part2(input))
}

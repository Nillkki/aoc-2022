package io.github.nillkki.aoc

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import java.util.concurrent.TimeUnit

private const val UPPER_CASE_DIFF = 38

private const val LOWER_CASE_DIFF = 96

@State(Scope.Benchmark)
@Warmup(time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(time = 500, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
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
    public fun part1Bench(): Int {
        return part1(input)
    }

    @Benchmark
    public fun part2Bench(): Int {
        return part2(input)
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

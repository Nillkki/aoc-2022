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
class Day04 {
    companion object {
        fun toRange(section: String): IntRange {
            return IntRange(section.substringBefore('-').toInt(), section.substringAfter('-').toInt())
        }

        fun asRangePair(section: String): Pair<IntRange, IntRange> {
            return toRange(section.substringBefore(',')) to toRange(section.substringAfter(','))
        }

        fun part1(input: List<String>): Int {
            var sum = 0

            for (i in input) {
                val (firstElf, secondElf) = asRangePair(i)
                if (firstElf.all { secondElf.contains(it) }
                    || secondElf.all { firstElf.contains(it) }) {
                    sum += 1
                }
            }

            return sum
        }

        fun part2(input: List<String>): Int {
            var sum = 0

            for (i in input) {
                val (firstElf, secondElf) = asRangePair(i)

                if (firstElf.any { secondElf.contains(it) } || secondElf.any { firstElf.contains(it) }) {
                    sum += 1
                }
            }

            return sum
        }
    }

    private var input = emptyList<String>()

    @Setup
    fun setUp() {
        input = readInput("Day04")
    }

    @Benchmark
    public fun part1Bench() {
        part1(input)
    }

    @Benchmark
    public fun part2Bench() {
        part2(input)
    }
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(Day04.part1(testInput) == 2)
    check(Day04.part2(testInput) == 4)

    val input = readInput("Day04")
    println(Day04.part1(input))
    println(Day04.part2(input))
}

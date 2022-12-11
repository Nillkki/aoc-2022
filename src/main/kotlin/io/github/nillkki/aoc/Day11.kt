package io.github.nillkki.aoc

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Warmup(time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(time = 500, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
class Day11 {
    companion object {
        fun part1(input: String): Int {
            val monkeys = input.split("\n\n")
            monkeys.forEach { monkey ->
                var parts = monkey.split("\n")
                parts.forEach { part ->
                    println(part)
                }
            }

            return 0
        }

        fun part2(input: String): Int {
            return 0
        }
    }

    private var input = ""

    @Setup
    fun setUp() {
        input = readInputAsText("Day11")
    }

    @Benchmark
    fun part1Bench() {
        part1(input)
    }

    @Benchmark
    fun part2Bench() {
        part2(input)
    }
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsText("Day11_test")
    check(Day11.part1(testInput) == 10605)
    check(Day11.part2(testInput) == 0)

    val input = readInputAsText("Day11")
    println(Day11.part1(input))
    println(Day11.part2(input))
}

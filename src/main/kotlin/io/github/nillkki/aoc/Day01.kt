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

@State(Scope.Benchmark)
@Warmup(time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(time = 500, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
class Day01 {

    companion object {
        fun parseList(input: List<String>): List<Int> {
            val list = mutableListOf<Int>()
            var sum = 0
            for (i in input) {
                if (i.isBlank()) {
                    list.add(sum)
                    sum = 0
                    continue
                }
                sum += i.toInt()
            }
            list.add(sum)
            return list
        }

        fun part1(input: List<String>): Int {
            val list = parseList(input)
            return list.max()
        }

        fun part2(input: List<String>): Int {
            val list = parseList(input)
            val topThree = list.sorted().takeLast(3)
            return topThree.sum()
        }
    }

    private var input = emptyList<String>()

    @Setup
    fun setUp() {
        input = readInput("Day01")
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
    val testInput = readInput("Day01_test")
    check(Day01.part1(testInput) == 24000)
    check(Day01.part2(testInput) == 45000)

    val input = readInput("Day01")
    println(Day01.part1(input))
    println(Day01.part2(input))
}



package io.github.nillkki.aoc

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Warmup(time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(time = 500, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
class Day10 {
    companion object {
        fun part1(input: List<String>): Int {
            var sum = 0
            var cycle = 0
            var x = 1
            var nextCycleToStop = 20

            input.forEachIndexed { index, s ->
                when (cycle) {
                    nextCycleToStop -> {
                        sum += cycle * x
                        nextCycleToStop += 40
                    }

                    else -> {}
                }

                when (s) {
                    "noop" -> {
                        // Do nothing, but takes one cycle
                        cycle++
                    }

                    else -> {
                        // Instruction is always addx
                        // Adds value to x and takes two cycles
                        var value = s.substringAfter(" ").toInt()
                        repeat(2) {
                            cycle++
                            when (cycle) {
                                nextCycleToStop -> {
                                    sum += cycle * x
                                    nextCycleToStop += 40
                                }

                                else -> {}
                            }
                        }
                        x += value
                    }
                }
            }

            return sum
        }

        fun part2(input: List<String>): Int {
            var currentRow = ""
            var cycle = 0
            var x = 1

            fun processCycle() {
                if (cycle % 40 == 0) {
                    println(currentRow)
                    currentRow = ""
                }

                if (currentRow.length == x || currentRow.length == x-1 || currentRow.length == x+1){
                    currentRow += "#"
                } else {
                    currentRow += "."
                }

                cycle++
            }


            input.forEachIndexed { index, s ->
                when (s) {
                    "noop" -> {
                        // Do nothing, but takes one cycle
                        processCycle()
                    }

                    else -> {
                        // Instruction is always addx
                        // Adds value to x and takes two cycles
                        val value = s.substringAfter(" ").toInt()
                        repeat(2) {
                            processCycle()
                        }
                        x += value
                    }
                }
            }
            // Print one last time
            if (cycle % 40 == 0) {
                println(currentRow)
                currentRow = ""
            }
            return 0
        }
    }

    private var input = emptyList<String>()

    @Setup
    fun setUp() {
        input = readInput("Day08")
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
    val testInput = readInput("Day10_test")
    check(Day10.part1(testInput) == 13140)
    check(Day10.part2(testInput) == 0)

    val input = readInput("Day10")
    println(Day10.part1(input))
    println(Day10.part2(input))
}

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
import kotlin.math.abs

@State(Scope.Benchmark)
@Warmup(time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(time = 500, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
class Day09 {
    companion object {
        fun Int.makeSmaller(): Int {
            return if (this > 0) this - 1 else this + 1
        }

        fun part1(input: List<String>): Int {
            var headX: Int = 0
            var headY: Int = 0
            var tailX: Int = 0
            var tailY: Int = 0
            val visitedPositions = mutableSetOf<Pair<Int,Int>>()
            visitedPositions.add(tailX to tailY)
            input.forEach { line ->
                val direction = line.substringBefore(" ")
                var steps = line.substringAfter(" ").toInt()
                while (steps > 0) {
                    // Update head
                    when(direction) {
                        "R" -> {
                            headX++
                        }
                        "L" -> {
                            headX--
                        }
                        "U" -> {
                            headY++
                        }
                        "D" -> {
                            headY--
                        }
                        else -> error("Unknown direction")
                    }
                    // Update tail
                    // If tail and head are on the same row or column tail just follows
                    if (headX == tailX || headY == tailY) {
                        if (abs(headX - tailX) > 1 ) {
                            tailX += (headX - tailX).makeSmaller()
                        }
                        if (abs(headY - tailY) > 1 ) {
                            tailY += (headY - tailY).makeSmaller()
                        }
                    } else {
                        // Else if they are not on the same row or column tail must more diagonally
                        if(abs(headX - tailX) > 1 || abs(headY - tailY) > 1) {
                            if (headX > tailX){
                                tailX++
                            } else {
                                tailX--
                            }
                            if (headY > tailY){
                                tailY++
                            } else {
                                tailY--
                            }
                        }
                    }

                    println("Head: $headX, $headY; Tail: $tailX, $tailY")
                    // Store visited tail
                    visitedPositions.add(tailX to tailY)

                    steps--
                }
            }

            val visitedSum = visitedPositions.size
            return visitedSum
        }

        public fun part2(input: List<String>): Int {
            return 0
        }
    }

    private var input = emptyList<String>()

    @Setup
    fun setUp() {
        input = readInput("Day08")
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
    val testInput = readInput("Day09_test")
    check(Day09.part1(testInput) == 13)
    //check(Day09.part2(testInput) == 8)

    val input = readInput("Day09")
    println(Day09.part1(input))
    println(Day09.part2(input))
}

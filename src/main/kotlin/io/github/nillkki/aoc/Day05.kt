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
class Day05 {
    companion object {
        fun parseParts(input: String): Pair<List<String>, List<String>> {
            val parts = input.split("\n\n")
            val cargo = parts.first().split("\n")
            val moves = parts.last().split("\n")
            return cargo to moves
        }

        fun parseStacks(cargo: List<String>): Array<ArrayDeque<Char>> {
            val lines = cargo.reversed().iterator()

            // Build stacks first
            val firstLine = lines.next()
            val numberOfStacks = firstLine.split("   ").size
            val stacks = Array(numberOfStacks) {
                ArrayDeque<Char>()
            }

            lines.forEachRemaining {
                val columns = it.chunked(4)

                columns.forEachIndexed { index, element ->
                    if (element.isNotBlank()) {
                        // Add container to current stack
                        stacks[index].add(element[1])
                    }
                }
            }

            return stacks
        }

        fun parseMove(move: String): Triple<Int, Int, Int> {
            val moveParts = move.split(" ")
            val count = moveParts[1].toInt()
            val from = moveParts[3].toInt()
            val to = moveParts[5].toInt()
            return Triple(count, from, to)
        }

        fun part1(input: String): String {
            val (cargo, moves) = parseParts(input)
            val stacks = parseStacks(cargo)

            moves.forEach {
                if(it.isNotBlank()) {
                    val (count, from, to) = parseMove(it)
                    repeat(count) {
                        val element = stacks[from-1].removeLast()
                        stacks[to-1].add(element)
                    }
                }
            }

            return stacks.map { it.last() }.joinToString(separator = "")
        }

        fun part2(input: String): String {
            val (cargo, moves) = parseParts(input)
            val stacks = parseStacks(cargo)

            moves.forEach {
                if(it.isNotBlank()) {
                    val (count, from, to) = parseMove(it)

                    // Crane should keep order of moved items
                    val crane = ArrayDeque<Char>()
                    repeat(count) {
                        val element = stacks[from-1].removeLast()
                        crane.addFirst(element)
                    }
                    stacks[to-1].addAll(crane)
                }
            }

            return stacks.map { it.last() }.joinToString(separator = "")
        }
    }

    private var input = ""

    @Setup
    fun setUp() {
        input = readInputAsText("Day05")
    }

    @Benchmark
    public fun part1Bench(): String {
        return part1(input)
    }

    @Benchmark
    public fun part2Bench(): String {
        return part2(input)
    }
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsText("Day05_test")
    check(Day05.part1(testInput) == "CMZ")
    check(Day05.part2(testInput) == "MCD")

    val input = readInputAsText("Day05")
    println(Day05.part1(input))
    println(Day05.part2(input))
}

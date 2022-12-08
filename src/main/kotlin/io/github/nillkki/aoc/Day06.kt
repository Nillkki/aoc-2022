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
class Day06 {
    companion object {
        fun String.findEndOfNDistinctCharacters(n: Int): Int {
            var index = n - 1
            while (index < this.length) {
                val min = index - (n - 1)
                val max = index
                val marker = this.slice(min..max)

                // Nice hack but not very efficient
                if (marker.toCharArray().distinct().size == n) {
                    return index + 1
                }
                index++
            }

            return -1
        }

        fun part1(input: String): Int {
            // first position where the four most recently received characters were all different
            return input.findEndOfNDistinctCharacters(4)
        }

        fun part2(input: String): Int {
            // first position where the fourteen most recently received characters were all different
            return input.findEndOfNDistinctCharacters(14)
        }
    }

    private var input = ""

    @Setup
    fun setUp() {
        input = readInputAsText("Day06")
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
    check(Day06.part1("bvwbjplbgvbhsrlpgdmjqwftvncz") == 5)
    check(Day06.part1("nppdvjthqldpwncqszvftbrmjlhg") == 6)
    check(Day06.part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 10)
    check(Day06.part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 11)

    check(Day06.part2("mjqjpqmgbljsphdztnvjfqwrcgsmlb") == 19)
    check(Day06.part2("bvwbjplbgvbhsrlpgdmjqwftvncz") == 23)
    check(Day06.part2("nppdvjthqldpwncqszvftbrmjlhg") == 23)
    check(Day06.part2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 29)
    check(Day06.part2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 26)

    val input = readInputAsText("Day06")
    println(Day06.part1(input))
    println(Day06.part2(input))
}

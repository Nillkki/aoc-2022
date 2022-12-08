package io.github.nillkki.aoc

private const val UPPER_CASE_DIFF = 38

private const val LOWER_CASE_DIFF = 96

fun main() {
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

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

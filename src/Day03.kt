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
        var sum = 0

        for (i in input) {
            var charsInBoth = ""
            val firstHalf = i.slice(IntRange(0, i.length / 2 - 1))
            val secondHalf = i.slice(IntRange(i.length / 2, i.length - 1))
            for (c in firstHalf) {
                if (secondHalf.contains(c) && !charsInBoth.contains(c)) {
                    charsInBoth += c
                }
            }
            sum += charsInBoth.map { getPriorityValue(it) }.sum()
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0

        val groups = input.chunked(3)
        for (g in groups) {
            for (c in g[0]) {
                if (g[1].contains(c) && g[2].contains(c)) {
                    sum += getPriorityValue(c)
                    break
                }
            }
        }

        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

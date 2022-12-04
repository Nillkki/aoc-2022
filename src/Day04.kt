fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0

        fun getRange(elf: String): IntRange {
            val digits = elf.split('-')
            return IntRange(digits.first().toInt(), digits.last().toInt())
        }

        for (i in input) {
            val pair = i.split(',')
            val firstElfRange = getRange(pair.first())
            val secondElfRange = getRange(pair.last())

            if (firstElfRange.all { secondElfRange.contains(it) } || secondElfRange.all { firstElfRange.contains(it) }) {
                sum += 1
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0

        fun getRange(elf: String): IntRange {
            val digits = elf.split('-')
            return IntRange(digits.first().toInt(), digits.last().toInt())
        }

        for (i in input) {
            val pair = i.split(',')
            val firstElfRange = getRange(pair.first())
            val secondElfRange = getRange(pair.last())

            if (firstElfRange.any { secondElfRange.contains(it) } || secondElfRange.any { firstElfRange.contains(it) }) {
                sum += 1
            }
        }

        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

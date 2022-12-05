fun main() {
    fun toRange(section: String): IntRange {
        val ids = section.split('-')
        return IntRange(ids.first().toInt(), ids.last().toInt())
    }

    fun part1(input: List<String>): Int {
        var sum = 0

        for (i in input) {
            val pair = i.split(',')
            val firstElf = toRange(pair.first())
            val secondElf = toRange(pair.last())

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
            val pair = i.split(',')
            val firstElf = toRange(pair.first())
            val secondElf = toRange(pair.last())

            if (firstElf.any { secondElf.contains(it) } || secondElf.any { firstElf.contains(it) }) {
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

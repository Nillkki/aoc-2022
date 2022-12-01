fun main() {
    fun part1(input: List<String>): Int {
        val list = parseList(input)
        return list.max()
    }

    fun part2(input: List<String>): Int {
        val list = parseList(input)
        val topThree = list.sorted().takeLast(3)
        return topThree.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

fun parseList(input: List<String>): List<Int> {
    val list = mutableListOf<Int>()
    var sum: Int = 0
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

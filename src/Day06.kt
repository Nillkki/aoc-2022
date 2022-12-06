fun main() {
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

    // test if implementation meets criteria from the description, like:
    check(part1("bvwbjplbgvbhsrlpgdmjqwftvncz") == 5)
    check(part1("nppdvjthqldpwncqszvftbrmjlhg") == 6)
    check(part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 10)
    check(part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 11)

    check(part2("mjqjpqmgbljsphdztnvjfqwrcgsmlb") == 19)
    check(part2("bvwbjplbgvbhsrlpgdmjqwftvncz") == 23)
    check(part2("nppdvjthqldpwncqszvftbrmjlhg") == 23)
    check(part2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 29)
    check(part2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 26)

    val input = readInputAsText("Day06")
    println(part1(input))
    println(part2(input))
}

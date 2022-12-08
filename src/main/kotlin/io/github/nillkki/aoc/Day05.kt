package io.github.nillkki.aoc

fun main() {

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

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsText("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInputAsText("Day05")
    println(part1(input))
    println(part2(input))
}

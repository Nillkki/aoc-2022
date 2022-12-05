fun main() {
    fun part1(input: List<String>): Int {
        fun scoreChosenMove(chosenMove: Char) = when (chosenMove) {
            'X' -> 1
            'Y' -> 2
            'Z' -> 3
            else -> {
                error("Unknown chosen move")
            }
        }

        fun scoreMovePair(movePair: Pair<Char, Char>) = when (movePair) {
            'A' to 'Y', 'B' to 'Z', 'C' to 'X' -> 6 // Win
            'A' to 'X', 'B' to 'Y', 'C' to 'Z' -> 3 // Draw
            'A' to 'Z', 'B' to 'X', 'C' to 'Y' -> 0

            else -> {
                error("Unknown move pair")
            }
        }

        return input.sumOf { line ->
            val opponentsMove = line[0]
            val chosenMove = line[2]
            scoreChosenMove(chosenMove) + scoreMovePair(opponentsMove to chosenMove)
        }
    }
    
    fun part2(input: List<String>): Int {
        fun scoreChosenMove(
            endResult: Char,
            opponentsMove: Char,
        ): Int = when (opponentsMove to endResult) {
            'B' to 'X', 'A' to 'Y', 'C' to 'Z' -> 1
            'C' to 'X', 'B' to 'Y', 'A' to 'Z' -> 2
            'A' to 'X', 'C' to 'Y', 'B' to 'Z' -> 3

            else -> {
                error("Unknown input")
            }
        }

        fun scoreResult(endResult: Char) = when (endResult) {
            'X' -> 0
            'Y' -> 3 // Draw
            'Z' -> 6 // Win

            else -> {
                error("Unknown endResult")
            }
        }

        return input.sumOf { line ->
            val opponentsMove = line[0]
            val endResult = line[2]
            scoreChosenMove(endResult, opponentsMove) + scoreResult(endResult)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

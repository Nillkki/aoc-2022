fun main() {
    fun part1(input: List<String>): Int {
        var score = 0
        for (i in input) {
            val opponentsMove = i[0]
            val chosenMove = i[2]
            // A (Rock) is beaten by Y (Paper)
            // B (Paper) is beaten by Z (Scissors)
            // C (Scissors) is beaten by X (Rock)
            when (opponentsMove) {
                'A' -> {
                    if (chosenMove == 'Y') {
                        // Win
                        score += 6
                    } else if (chosenMove == 'X') {
                        // Draw
                        score += 3
                    }
                }

                'B' -> {
                    if (chosenMove == 'Z') {
                        // Win
                        score += 6
                    } else if (chosenMove == 'Y') {
                        // Draw
                        score += 3
                    }
                }

                'C' -> {
                    if (chosenMove == 'X') {
                        // Win
                        score += 6
                    } else if (chosenMove == 'Z') {
                        // Draw
                        score += 3
                    }
                }

                else -> {
                    error("Unknown input for opponent")
                }
            }
            score += when (chosenMove) {
                'X' -> 1
                'Y' -> 2
                'Z' -> 3
                else -> {
                    error("Unknown input for opponent")
                }
            }
        }
        return score
    }

    fun part2(input: List<String>): Int {
        var score = 0
        fun getLosingMove(opponentsMove: Char) = when (opponentsMove) {
            'A' -> 'C' // A (Rock) beats C (Scissors)
            'B' -> 'A' // B (Paper) beats A (Rock)
            'C' -> 'B' // C (Scissors) beats B (Paper)
            else -> {
                error("Unknown move for opponent when getting losing move")
            }
        }

        fun getDrawingMove(opponentsMove: Char) = when (opponentsMove) {
            'A' -> 'A'
            'B' -> 'B'
            'C' -> 'C'
            else -> {
                error("Unknown move for opponent when getting drawing move")
            }
        }

        fun getWinningMove(opponentsMove: Char) = when (opponentsMove) {
            'A' -> 'B' // B (Paper) beats A (Rock)
            'B' -> 'C' // C (Scissors) beats B (Paper)
            'C' -> 'A' // A (Rock) beats C (Scissors)
            else -> {
                error("Unknown move for opponent when getting winning move")
            }
        }

        for (i in input) {
            val opponentsMove = i[0]
            val chosenMove = when (i[2]) {
                // Lose
                'X' -> {
                    getLosingMove(opponentsMove)
                }
                // Draw
                'Y' -> {
                    score += 3
                    getDrawingMove(opponentsMove)
                }
                // Win
                'Z' -> {
                    score += 6
                    getWinningMove(opponentsMove)
                }
                else -> {
                    error("Unknown input")
                }
            }
            score += when (chosenMove) {
                'A' -> 1
                'B' -> 2
                'C' -> 3
                else -> {
                    error("Unknown move chosen")
                }
            }
        }
        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

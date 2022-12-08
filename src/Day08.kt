fun main() {
    fun buildGrid(input: List<String>): List<List<Int>> {
        return input.map { line -> line.map { it.digitToInt() } }
    }

    /**
     * Tree is taller that trees on the same row or column until the edge
     */
    fun isTallerThanRowOrColumn(
        tree: Int,
        top: List<Int>,
        bottom: List<Int>,
        left: List<Int>,
        right: List<Int>
    ): Boolean {
        return top.all { it < tree } ||
                bottom.all { it < tree } ||
                left.all { it < tree } ||
                right.all { it < tree }
    }

    fun part1(input: List<String>): Int {
        val grid = buildGrid(input)

        var sum = 0
        // Count the visible trees
        grid.forEachIndexed { columnIndex, row ->
            fun isAtEdge(cIdx: Int, rIdx: Int): Boolean {
                return (cIdx == 0) || (rIdx == 0) || (cIdx == grid.size) || (rIdx == row.size)
            }

            row.forEachIndexed { rowIndex, tree ->
                if (isAtEdge(columnIndex, rowIndex)) {
                    // Tree is at the edge, is always visible
                    sum += 1
                } else {
                    // To be visible,
                    // tree must be taller that trees on the same row or column until the edge
                    val top = grid.slice(0 until columnIndex).map { it[rowIndex] }
                    val bottom = grid.slice(columnIndex + 1 until grid.size).map { it[rowIndex] }
                    val left = row.slice(0 until rowIndex)
                    val right = row.slice(rowIndex + 1 until row.size)
                    if (isTallerThanRowOrColumn(tree, top, bottom, left, right)) {
                        sum += 1
                    }
                }
            }
        }

        return sum
    }

    /**
     * Trees seen until taller tree blocks the view or there are no more trees
     */
    fun getViewingDistance(trees: List<Int>, tree: Int): Int {
        trees.forEachIndexed { index, t ->
            // Stop when tree is as tall or taller than the current tree
            if (t >= tree) {
                return index + 1
            }
        }
        // If we never stopped distance is the whole length of trees
        return trees.size
    }

    fun part2(input: List<String>): Int {
        val grid = buildGrid(input)

        // Calculate scenic score for all trees
        // A tree's scenic score is found by multiplying together its viewing distance in each of
        // the four directions
        // To measure the viewing distance from a given tree, look up, down, left, and right from
        // that tree; stop if you reach an edge or at the first tree that is the same height or
        // taller than the tree under consideration. (If a tree is right on the edge, at least one
        // of its viewing distances will be zero.)
        val scenicScoreGrid = Array(grid.size) { Array(grid[0].size) { 0 } }
        grid.forEachIndexed { columnIndex, row ->
            row.forEachIndexed { rowIndex, tree ->
                // Viewing distance top
                val treesToTop = grid.slice(0 until columnIndex).map { it[rowIndex] }.reversed()
                val scoreTop = getViewingDistance(treesToTop, tree)
                // Viewing distance bottom
                val treesToBottom = grid.slice(columnIndex + 1 until grid.size).map { it[rowIndex] }
                val scoreBottom = getViewingDistance(treesToBottom, tree)
                // Viewing distance left
                val treesToLeft = row.slice(0 until rowIndex).reversed()
                val scoreLeft = getViewingDistance(treesToLeft, tree)
                // Viewing distance right
                val treesToRight = row.slice(rowIndex + 1 until row.size)
                val scoreRight = getViewingDistance(treesToRight, tree)

                val scenicScore = scoreTop * scoreBottom * scoreLeft * scoreRight
                scenicScoreGrid[columnIndex][rowIndex] = scenicScore
            }
        }

        return scenicScoreGrid.maxOf { it.max() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

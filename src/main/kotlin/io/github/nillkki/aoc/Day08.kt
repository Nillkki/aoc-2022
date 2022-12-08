package io.github.nillkki.aoc

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup

@State(Scope.Benchmark)
@Warmup(iterations = 1)
@Measurement(iterations = 3)
class Day08 {

    companion object {
        private fun buildGrid(input: List<String>): Array<IntArray> {
            return input.map { line -> line.map { it.digitToInt() }.toIntArray() }.toTypedArray()
        }

        /**
         * Tree is taller that trees on the same row or column until the edge
         */
        private fun isTallerThanRowOrColumn(
            tree: Int, top: List<Int>, bottom: List<Int>, left: IntArray, right: IntArray
        ): Boolean {
            return top.all { it < tree } || bottom.all { it < tree } || left.all { it < tree } || right.all { it < tree }
        }

        fun part1(input: List<String>): Int {
            val grid = buildGrid(input)

            // Count the visible trees
            return grid.mapIndexed column@{ columnIndex, row ->
                fun isAtEdge(cIdx: Int, rIdx: Int): Boolean {
                    return (cIdx == 0) || (rIdx == 0) || (cIdx == grid.size) || (rIdx == row.size)
                }

                return@column row.mapIndexed row@{ rowIndex, tree ->
                    if (isAtEdge(columnIndex, rowIndex)) {
                        // Tree is at the edge, is always visible
                        return@row 1
                    }
                    // To be visible,
                    // tree must be taller that trees on the same row or column until the edge
                    val top = grid.sliceArray(0 until columnIndex).map { it[rowIndex] }
                    val bottom =
                        grid.sliceArray(columnIndex + 1 until grid.size).map { it[rowIndex] }
                    val left = row.sliceArray(0 until rowIndex)
                    val right = row.sliceArray(rowIndex + 1 until row.size)
                    if (isTallerThanRowOrColumn(tree, top, bottom, left, right)) {
                        return@row 1
                    }
                    return@row 0
                }
                    .sum()
            }
                .sum()
        }

        /**
         * Trees seen until taller tree blocks the view or there are no more trees
         */
        private fun getViewingDistance(trees: List<Int>, tree: Int): Int {
            trees.forEachIndexed { index, t ->
                // Stop when tree is as tall or taller than the current tree
                if (t >= tree) {
                    return index + 1
                }
            }
            // If we never stopped distance is the whole length of trees
            return trees.size
        }

        public fun part2(input: List<String>): Int {
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
                    val treesToBottom =
                        grid.slice(columnIndex + 1 until grid.size).map { it[rowIndex] }
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
    }

    private var input = emptyList<String>()

    @Setup
    fun setUp() {
        input = readInput("Day08")
    }

    @Benchmark
    public fun part1Bench() {
        part1(input)
    }

    @Benchmark
    public fun part2Bench() {
        part2(input)
    }
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(Day08.part1(testInput) == 21)
    check(Day08.part2(testInput) == 8)

    val input = readInput("Day08")
    println(Day08.part1(input))
    println(Day08.part2(input))
}

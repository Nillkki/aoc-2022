package io.github.nillkki.aoc

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import java.util.SortedSet
import java.util.concurrent.TimeUnit

private const val TOTAL_DISK_SPACE = 70000000

private const val REQUIRED_DISK_SPACE = 30000000

@State(Scope.Benchmark)
@Warmup(time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(time = 500, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
class Day07 {
    companion object {
        abstract class Node(
            var name: String, var children: MutableList<Node>, var parent: Node?
        ) : Comparable<Node> {

            abstract fun getSize(): Int

            override operator fun compareTo(other: Node): Int {
                return this.getSize().compareTo(other.getSize())
            }
        }

        class RootNode(
            name: String,
            children: MutableList<Node>,
        ) : Node(name, children, null) {
            override fun getSize(): Int {
                return children.sumOf { it.getSize() }
            }
        }

        class FileNode(
            name: String, parent: Node?, var internalSize: Int
        ) : Node(name, mutableListOf(), parent) {

            override fun getSize(): Int {
                return internalSize
            }
        }

        class DirectoryNode(
            name: String,
            children: MutableList<Node>,
            parent: Node?,
        ) : Node(name, children, parent) {
            override fun getSize(): Int {
                return children.sumOf { it.getSize() }
            }
        }

        fun <T : Node> getDirectoriesByMax(maxSize: Int, node: T): SortedSet<Node> {
            return if (node.getSize() < maxSize && node is DirectoryNode) {
                val result = sortedSetOf<Node>(node)
                result.addAll(node.children.flatMap { getDirectoriesByMax(maxSize, it) })
                result
            } else {
                node.children.flatMap { getDirectoriesByMax(maxSize, it) }.toSortedSet()
            }
        }

        fun <T : Node> getDirectoriesByMin(minSize: Int, node: T): SortedSet<Node> {
            return if (node.getSize() > minSize && node is DirectoryNode) {
                val result = sortedSetOf<Node>(node)
                result.addAll(node.children.flatMap { getDirectoriesByMin(minSize, it) })
                result
            } else {
                node.children.flatMap { getDirectoriesByMin(minSize, it) }.toSortedSet()
            }
        }

        fun <T : Node> findSmallestDirectoryWithMinSize(minSize: Int, node: T): Node {
            val result = getDirectoriesByMin(minSize, node)
            return result.first()
        }

        fun buildDirectoryTree(input: String): RootNode {
            val rootNode = RootNode("/", mutableListOf())
            var currentNode: Node = rootNode

            val commands = input.split("\n$ ")
            for (command in commands) {
                if (command.startsWith("cd")) {
                    val path = command.split(" ").last()
                    currentNode = if (path == "..") {
                        currentNode.parent ?: error("Can not walk back when parent node is null")
                    } else {
                        val newNode = DirectoryNode(path, mutableListOf(), currentNode)
                        currentNode.children.add(newNode)
                        newNode
                    }
                }

                if (command.startsWith("ls")) {
                    val output = command.split("\n")
                    for (line in output) {
                        if (line[0].isDigit()) {
                            val parts = line.split(" ")
                            val size = parts.first().toInt()
                            val fileName = parts.last()
                            val newFileNode = FileNode(fileName, currentNode, size)
                            currentNode.children.add(newFileNode)
                        }
                    }
                }
            }

            return rootNode
        }


        fun part1(input: String): Int {
            val rootNode = buildDirectoryTree(input)

            return getDirectoriesByMax(100000, rootNode).sumOf { it.getSize() }
        }

        fun part2(input: String): Int {
            val rootNode = buildDirectoryTree(input)

            val totalUsedDiskSpace = rootNode.getSize()
            val freeDiskSpace = TOTAL_DISK_SPACE - totalUsedDiskSpace
            val diskSpaceToFree = REQUIRED_DISK_SPACE - freeDiskSpace

            val smallest = findSmallestDirectoryWithMinSize(diskSpaceToFree, rootNode)

            return smallest.getSize()
        }
    }

    private var input = ""

    @Setup
    fun setUp() {
        input = readInputAsText("Day07")
    }

    @Benchmark
    public fun part1Bench(): Int {
        return part1(input)
    }

    @Benchmark
    public fun part2Bench(): Int {
        return part2(input)
    }
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsText("Day07_test")
    check(Day07.part1(testInput) == 95437)
    check(Day07.part2(testInput) == 24933642)

    val input = readInputAsText("Day07")
    println(Day07.part1(input))
    println(Day07.part2(input))
}

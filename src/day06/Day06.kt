package day06

import readText

fun main() {

    val id = "06"
    val testOutput = 7

    fun readDataStream(fileName: String): String {
        return readText(fileName)
    }

    fun findStartOfMarker(input: String, size: Int): Int {
        return input.windowedSequence(size).indexOfFirst { it.toSet().size == size } + size
    }

    // Time — O(N), Memory — O(N), algo can be overwrite to be O(1)
    fun part1(input: String): Int {
        return findStartOfMarker(input, 4)
    }

    // Time — O(N), Memory — O(N), algo can be overwrite to be O(1)
    fun part2(input: String): Int {
        return findStartOfMarker(input, 14)
    }

    val testInput = readDataStream("day$id/Day${id}_test")
    println(part1(testInput))
    check(part1(testInput) == testOutput)

    val input = readDataStream("day$id/Day$id")
    println(part1(input))
    println(part2(input))
}

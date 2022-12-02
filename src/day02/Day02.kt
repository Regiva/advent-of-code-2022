package day02

import readText

fun main() {

    fun readGuide(fileName: String): Sequence<List<String>> {
        return readText(fileName)
            .splitToSequence("\n")
            .map { it.split(" ") }
    }

    val scoresMatrix = arrayOf(
        intArrayOf(4, 8, 3),
        intArrayOf(1, 5, 9),
        intArrayOf(7, 2, 6),
    )

    fun String.simplify() = when (this) {
        "A", "X" -> 0
        "B", "Y" -> 1
        "C", "Z" -> 2
        else -> -1
    }

    // Time — O(n), Memory — O(n)
    fun part1(input: Sequence<List<String>>): Int {
        return input.sumOf { list -> scoresMatrix[list[0].simplify()][list[1].simplify()] }
    }

    val part2ScoresMatrix = arrayOf(
        intArrayOf(3, 4, 8),
        intArrayOf(1, 5, 9),
        intArrayOf(2, 6, 7),
    )

    // Time — O(n), Memory — O(n)
    fun part2(input: Sequence<List<String>>): Int {
        return input.sumOf { list -> part2ScoresMatrix[list[0].simplify()][list[1].simplify()] }
    }

    val testInput = readGuide("day02/Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readGuide("day02/Day02")
    println(part1(input))
    println(part2(input))
}
package day08

import readLines

typealias Forest = List<List<Int>>

fun main() {

    val id = "08"
    val testOutputPart1 = 21
    val testOutputPart2 = 8

    val testInput = readTreeHeightMap("day$id/Day${id}_test")

    println("--- Part 1 ---")
    println(part1(testInput))
    check(part1(testInput) == testOutputPart1)
    println("--- Part 2 ---")
    println(part2(testInput))
    check(part2(testInput) == testOutputPart2)

    val input = readTreeHeightMap("day$id/Day$id")
    println(part1(input))
    println(part2(input))
}

// Time — O(), Memory — O()
fun part1(forest: Forest): Int {
    val n = forest.size
    val m = forest.first().size
    var count = 2 * (n + m - 2)

    fun Forest.isTreeVisibleByDirection(index: Int, jndex: Int, direction: Directions): Boolean {
        val treeHeight = this[index][jndex]
        return generateTreesByDirection(index, jndex, direction).all { treeHeight > it }
    }

    for (i in 1 until n - 1) {
        for (j in 1 until m - 1) {
            if (Directions.values().any { forest.isTreeVisibleByDirection(i, j, it) }) count++
        }
    }

    return count
}

// Time — O(), Memory — O()
private fun part2(forest: Forest): Int {
    var maxScore = 0

    fun Forest.countVisibleTreesByDirection(index: Int, jndex: Int, direction: Directions): Int {
        val treeHeight = this[index][jndex]
        var count = 0

        for (currentTree in generateTreesByDirection(index, jndex, direction)) {
            count++
            if (currentTree >= treeHeight) return count
        }

        return count
    }

    for (i in 1 until forest.lastIndex) {
        for (j in 1 until forest.first().lastIndex) {
            val score = Directions.values()
                .map { forest.countVisibleTreesByDirection(i, j, it) }
                .reduce(Int::times)
            maxScore = maxOf(score, maxScore)
        }
    }

    return maxScore
}

private fun Forest.generateTreesByDirection(index: Int, jndex: Int, direction: Directions): Sequence<Int> {
    return generateSequence(direction.next(index to jndex), direction::next)
        .takeWhile { (i, j) -> i in indices && j in first().indices }
        .map { (i, j) -> this[i][j] }
}

fun readTreeHeightMap(fileName: String): Forest {
    return readLines(fileName).map { it.map(Char::digitToInt) }
}

private enum class Directions(val verticalChange: Int = 0, val horizontalChange: Int = 0) {
    Up(verticalChange = -1),
    Down(verticalChange = +1),
    Left(horizontalChange = -1),
    Right(horizontalChange = +1);

    fun next(coordinates: Pair<Int, Int>): Pair<Int, Int> {
        val (i, j) = coordinates
        return (i + verticalChange) to (j + horizontalChange)
    }
}

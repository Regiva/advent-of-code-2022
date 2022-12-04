package day03

import readLines

fun main() {

    val id = "03"

    fun readRucksacks(fileName: String): Sequence<Pair<String, String>> {
        return readLines(fileName)
            .asSequence()
            .map { it.substring(0, it.length / 2) to it.substring(it.length / 2, it.length) }
    }

    fun Char.priority() = if (isLowerCase()) code - 'a'.code + 1 else code - 'A'.code + 27

    // Time — O(N * M), Memory — O(N) where M is a rucksack's compartment length
    fun part1(input: Sequence<Pair<String, String>>): Int {
        var count = 0
        input.forEach { pair ->
            pair.first.toSet().intersect(pair.second.toSet()).forEach {
                count += it.priority()
            }
        }
        return count
    }

    // Time — O(N * M), Memory — O(N)
    fun part2(input: Sequence<Pair<String, String>>): Int {
        return input.map { it.first + it.second }
            .chunked(3)
            .map { it.map(String::toSet).reduce(Set<Char>::intersect).single() }
            .sumOf { it.priority() }
    }

    val testInput = readRucksacks("day$id/Day${id}_test")
    println(part1(testInput))
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readRucksacks("day$id/Day$id")
    println(part1(input))
    println(part2(input))
}

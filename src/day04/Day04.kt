package day04

import readLines

fun main() {

    val id = "04"

    fun readSections(fileName: String): Sequence<String> {
        return readLines(fileName).asSequence()
    }

    // Time — O(), Memory — O()
    fun part1(input: Sequence<String>): Int {
        return input.map {
            val sections = it.split(",")
            val firstStart = sections.first().split("-").first().toInt()
            val firstEnd = sections.first().split("-").last().toInt()
            val secondStart = sections.last().split("-").first().toInt()
            val secondEnd = sections.last().split("-").last().toInt()
            firstStart..firstEnd to secondStart..secondEnd
        }.count { pair ->
            pair.first.all { pair.second.contains(it) } or pair.second.all { pair.first.contains(it) }
        }
    }

    // Time — O(), Memory — O()
    fun part2(input: Sequence<String>): Int {
        return input.map {
            val sections = it.split(",")
            val firstStart = sections.first().split("-").first().toInt()
            val firstEnd = sections.first().split("-").last().toInt()
            val secondStart = sections.last().split("-").first().toInt()
            val secondEnd = sections.last().split("-").last().toInt()
            firstStart..firstEnd to secondStart..secondEnd
        }.count { pair ->
            pair.first.any { pair.second.contains(it) } or pair.second.any { pair.first.contains(it) }
        }
    }

    val testInput = readSections("day$id/Day${id}_test")
    println(part1(testInput))
    check(part1(testInput) == 2)

    val input = readSections("day$id/Day$id")
    println(part1(input))
    println(part2(input))
}

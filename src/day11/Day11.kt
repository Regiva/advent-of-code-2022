package day11

import readText
import kotlin.math.pow

fun main() {

    val id = "11"
    val testOutput = 10605

    val testInput = readInput("day$id/Day${id}_test_simple")
    println(part1(testInput))
    check(part1(testInput) == testOutput.toLong())

    val input = readInput("day$id/Day${id}_simple")
    println(part1(input))
    println(part2(input))
}

typealias Worry = Long

class Monkey(
    val items: MutableList<Worry>,
    private val operation: (Worry) -> Worry,
    val assertValue: Int,
    private val ifTrueMonkey: Int,
    private val ifFalseMonkey: Int,
) {
    var activity = 0L
        private set

    private val initialItems = items.toList()

    fun inspectItems(decreaseFactor: Int = 1): List<Transition> {
        val transitions = mutableListOf<Transition>()

        for (worry in items) {
            val changedWorry = operation(worry) / decreaseFactor
            transitions += throwItem(changedWorry, test(changedWorry))
            activity++
        }
        items.clear()

        return transitions
    }

    private fun throwItem(worry: Worry, monkeyId: Int): Transition {
        return Transition(worry, monkeyId)
    }

    private fun test(worry: Worry) = if (worry % assertValue == 0L) ifTrueMonkey else ifFalseMonkey

    fun reset() {
        activity = 0
        items.clear()
        items.addAll(initialItems)
    }
}

data class Transition(
    val worry: Worry,
    val monkeyId: Int,
)

fun readInput(fileName: String): List<Monkey> {
    return readText(fileName).split("\n\n")
        .map { text ->
            val (items, operation, assert, ifTrueMonkey, ifFalseMonkey) = text.split("\n")
            Monkey(
                items = ArrayDeque(items.split(" ").map { it.toLong() }),
                operation = { worry ->
                    val (operator, value) = operation.split(" ")
                    when (operator) {
                        "+" -> worry + value.toInt()
                        "*" -> worry * value.toInt()
                        "^" -> worry.toDouble().pow(value.toInt()).toLong()
                        else -> worry
                    }
                },
                assertValue = assert.toInt(),
                ifTrueMonkey = ifTrueMonkey.toInt(),
                ifFalseMonkey = ifFalseMonkey.toInt(),
            )
        }
}

fun part1(monkeys: List<Monkey>): Long {
    for (monkey in monkeys) monkey.reset()

    for (round in 1..20) {
        for (monkey in monkeys) {
            for (transition in monkey.inspectItems(decreaseFactor = 3)) {
                val (item, id) = transition
                monkeys[id].items.add(item)
            }
        }
    }

    return monkeys.map { it.activity }.sortedDescending().take(2).reduce(Long::times)
}

fun part2(monkeys: List<Monkey>): Long {
    val mod = monkeys.map { it.assertValue }.reduce(Int::times)
    for (monkey in monkeys) monkey.reset()

    for (round in 1..10000) {
        for (monkey in monkeys) {
            for (transition in monkey.inspectItems()) {
                val (item, id) = transition
                monkeys[id].items.add(item % mod)
            }
        }
    }

    return monkeys.map { it.activity }.sortedDescending().take(2).reduce(Long::times)
}

package day13

import readLines

fun main() {

    val id = "13"
    val testOutput = 13

    val testInput = readInput("day$id/Day${id}_test")

    println(part1(testInput))
    check(part1(testInput) == testOutput)

    val input = readInput("day$id/Day$id")
    println(part1(input))
    println(part2(input))
}

private fun part1(input: List<Packet>): Int {
    var index = 0
    var count = 0

    for ((left, right) in input.chunked(2)) {
        index++
        if (left <= right) count += index
    }

    return count
}

private fun part2(input: List<Packet>): Int {
    fun divider(value: Int) = Packet.Complex(Packet.Complex(Packet.Simple(value)))

    val dividerTwo = divider(2)
    val dividerSix = divider(6)
    val sortedListWithDividers = (input + listOf(dividerTwo, dividerSix)).sorted()

    val indexTwo = sortedListWithDividers.binarySearch(dividerTwo)
    val indexSix = sortedListWithDividers.binarySearch(dividerSix)

    return (indexTwo + 1) * (indexSix + 1)
}

private fun readInput(fileName: String): List<Packet> {
    return readLines(fileName)
        .filter(String::isNotEmpty)
        .map(::eval)
}

private fun eval(line: String): Packet {
    val number = StringBuilder()
    val stack = ArrayDeque<Packet?>()

    fun pushSimple() {
        if (number.isNotEmpty()) {
            stack.addFirst(Packet.Simple(number.toString().toInt()))
            number.clear()
        }
    }

    fun pushComplex() {
        val nested = ArrayDeque<Packet>()
        var current = stack.removeFirst()

        while (current != null) {
            nested.addFirst(current)
            current = stack.removeFirst()
        }

        stack.addFirst(Packet.Complex(nested))
    }

    for (char in line) {
        when (char) {
            '[' -> stack.addFirst(null)
            ',' -> pushSimple()
            ']' -> {
                pushSimple()
                pushComplex()
            }
            else -> number.append(char)
        }
    }

    return checkNotNull(stack.single())
}

private sealed interface Packet : Comparable<Packet> {

    data class Simple(val value: Int) : Packet {

        override fun compareTo(other: Packet): Int {
            return when (other) {
                is Simple -> value compareTo other.value
                is Complex -> Complex(this) compareTo other
            }
        }

        override fun toString() = value.toString()
    }

    data class Complex(val list: List<Packet>) : Packet {
        constructor(value: Packet) : this(listOf(value))

        override fun compareTo(other: Packet): Int {
            val left = this
            val right = if (other is Complex) other else Complex(other)

            for (i in 0..minOf(left.list.lastIndex, right.list.lastIndex)) {
                val compare = left.list[i] compareTo right.list[i]
                if (compare != 0) return compare
            }

            return left.list.lastIndex compareTo right.list.lastIndex
        }

        override fun toString() = list.toString()
    }
}

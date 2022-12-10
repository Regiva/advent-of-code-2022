package day09

import readLines

fun main() {

    val id = "09"
    val testOutput = 13
    val testOutputTwo = 36

    val testInput = readInput("day$id/Day${id}_test")
    val testInputTwo = readInput("day$id/Day${id}_test_two")
    println(part1(testInput))
    println(part2(testInputTwo))
    check(part1(testInput) == testOutput)
    check(part2(testInputTwo) == testOutputTwo)

    val input = readInput("day$id/Day$id")
    println(part1(input))
    println(part2(input))
}

private fun readInput(fileName: String): List<Pair<Command, Int>> {
    return readLines(fileName).map {
        val (command, steps) = it.split(" ")
        Command.valueOf(command) to steps.toInt()
    }
}

// Time — O(), Memory — O()
private fun part1(input: List<Pair<Command, Int>>): Int {
    var head = 0 to 0
    var tail = 0 to 0
    val visited = mutableSetOf<Pair<Int, Int>>()

    input.forEach { (command, steps) ->
        repeat(steps) {
            head = head.applyCommand(command)
            tail = tail.adjustTo(head)
            visited.add(tail)
        }
    }

    return visited.size
}

// Time — O(), Memory — O()
private fun part2(input: List<Pair<Command, Int>>): Int {
    val rope = Array(10) { 0 to 0 }
    val visited = mutableSetOf<Pair<Int, Int>>()

    input.forEach { (command, steps) ->
        repeat(steps) {
            rope[0] = rope.first().applyCommand(command)

            for (i in 1..rope.lastIndex) {
                val prev = rope[i - 1]
                rope[i] = rope[i].adjustTo(prev)
            }

            visited.add(rope.last())
        }
    }

    return visited.size
}

private enum class Command(val dx: Int = 0, val dy: Int = 0) {
    U(dy = +1),
    D(dy = -1),
    L(dx = -1),
    R(dx = +1);
}

private fun Pair<Int, Int>.applyCommand(command: Command): Pair<Int, Int> {
    val (x, y) = this
    return (x + command.dx) to (y + command.dy)
}

private fun Pair<Int, Int>.adjustTo(head: Pair<Int, Int>): Pair<Int, Int> {
    val (hx, hy) = head
    var (tx, ty) = this
    val dx = hx - tx
    val dy = hy - ty

    when {
        dx > 1 -> {
            tx++
            if (dy > 0) ty++ else if (dy < 0) ty--
        }
        dx < -1 -> {
            tx--
            if (dy > 0) ty++ else if (dy < 0) ty--
        }
        dy > 1 -> {
            ty++
            if (dx > 0) tx++ else if (dx < 0) tx--
        }
        dy < -1 -> {
            ty--
            if (dx > 0) tx++ else if (dx < 0) tx--
        }
    }

    return tx to ty
}

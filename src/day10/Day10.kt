package day10

import readLines
import kotlin.math.absoluteValue

fun main() {

    val id = "10"
    val testOutput = 13140

    val testInput = readInput("day$id/Day${id}_test")
    println(solve(testInput))
    check(solve(testInput) == testOutput)

    val input = readInput("day$id/Day$id")
    println(solve(input))
}

private fun readInput(fileName: String): List<Command> {
    return readLines(fileName).map {
        val splitted = it.split(" ")

        when (splitted.first()) {
            "noop" -> Command.Noop
            "addx" -> Command.Add(count = splitted[1].toInt())
            else -> Command.Noop
        }
    }
}

private fun solve(input: List<Command>): Int {
    var cycle = 0
    val keyCycles = generateSequence(seed = 20, nextFunction = { it + 40 }).take(6)

    var register = 1
    var strength = 0

    val width = 40

    fun tick(change: Int = 0) {
        cycle++

        // part 1
        if (cycle in keyCycles) strength += cycle * register

        // part 2
        print(if (((cycle - 1) % width - register).absoluteValue <= 1) "#" else " ")
        if (cycle % width == 0) println()

        register += change
    }

    for (command in input) {
        tick()

        when (command) {
            is Command.Add -> tick(command.count)
            is Command.Noop -> {}
        }
    }

    return strength
}

private sealed class Command {
    object Noop : Command()
    data class Add(val count: Int) : Command()
}

package day05

import readText

fun main() {

    val id = "05"
    val testOutput = "CMZ"

    fun readDrawingSimple(fileName: String): Drawing {
        val drawing = readText(fileName).split("\n\n")
        val stacks = drawing[0].split("\n").map { ArrayDeque(it.toList()) }.toMutableList()
        val commands = drawing[1].split("\n").map { line ->
            val splitted = line.split(" ")
            Command(splitted[1].toInt(), splitted[3].toInt() - 1, splitted[5].toInt() - 1)
        }
        return Drawing(
            stacks = stacks,
            commands = commands,
        )
    }

    fun readDrawing(fileName: String): Drawing {
        return Drawing(mutableListOf(), listOf())
    }

    // Time — O(), Memory — O()
    fun part1(input: Drawing): String {
        println(input.toString())
        var currentLast = ' '
        input.commands.forEach { command ->
            repeat(command.count) {
                currentLast = input.stacks[command.from].removeLast()
                input.stacks[command.to].addLast(currentLast)
            }
        }
        var result = ""
        input.stacks.map { result += it.last() }
        return result
    }

    // Time — O(), Memory — O()
    fun part2(input: Drawing): String {
        var currentLast = listOf<Char>()
        input.commands.forEach { command ->
            println(input.stacks[command.from])

            currentLast = input.stacks[command.from].takeLast(command.count)
            repeat(command.count) {
                input.stacks[command.from].removeLast()
            }

            println(input.stacks[command.from])
            println()

            input.stacks[command.to].addAll(currentLast)
        }
        var result = ""
        input.stacks.map { result += it.last() }
        return result
    }

//    val testInput = readDrawing("day$id/Day${id}_test")
    val testFormattedInput = readDrawingSimple("day$id/Day${id}_test_formatted")
//    println(part1(testFormattedInput))
    check(part1(testFormattedInput) == testOutput)

//    val input = readDrawing("day$id/Day$id")
    val formattedInput = readDrawingSimple("day$id/Day${id}_formatted")
//    println(part1(formattedInput))
    println(part2(formattedInput))
}

data class Drawing(
    val stacks: MutableList<ArrayDeque<Char>>,
    val commands: List<Command>,
)

data class Command(
    val count: Int,
    val from: Int,
    val to: Int,
)

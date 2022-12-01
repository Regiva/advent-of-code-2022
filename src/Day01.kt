fun main() {

    fun readElves(fileName: String): Sequence<Sequence<Int>> {
        return readText(fileName)
            .splitToSequence("\n\n")
            .map { it.lineSequence().map(String::toInt) }
    }

    // Time — O(n), Memory — O(1)?
    fun part1(input: Sequence<Sequence<Int>>) = input.maxOf { it.sum() }

    // Time — O(n*log(n)), Memory — O(n)?
    fun part2(input: Sequence<Sequence<Int>>): Int {
        return input.sortedByDescending { it.sum() }
            .take(3)
            .sumOf { it.sum() }
    }

    val testInput = readElves("Day01_test")
    check(part1(testInput) == 24000)

    val input = readElves("Day01")
    println(part1(input))
    println(part2(input))
}

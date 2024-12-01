import kotlin.math.abs

fun main() {
    fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> {
        return input.map { it.split("   ") }
            .map { Pair(it[0].toInt(), it[1].toInt()) }
            .unzip()
    }

    fun part1(input: List<String>): Int {
        val unzip = parseInput(input)

        val first = unzip.first.sorted()
        val second = unzip.second.sorted()

        return first.mapIndexed { i, elem -> abs(elem - second[i]) }.sum()
    }

    fun part2(input: List<String>): Int {
        val unzip = parseInput(input)
        val secondByCount = unzip.second.groupingBy { it }.eachCount()
        return unzip.first.sumOf { it * (secondByCount[it] ?: 0) }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

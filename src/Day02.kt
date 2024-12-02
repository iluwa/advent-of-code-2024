import kotlin.math.abs

fun main() {
    fun parseInput(input: List<String>): List<Report> = input.map { Report.from(it.split(" ")) }

    fun part1(input: List<String>): Int {
        return parseInput(input)
            .map { it.isValid() }
            .count { it }
    }

    fun part2(input: List<String>): Int {
        return parseInput(input)
            .map {
                if (it.isValid()) true
                else it.extraCheck()
            }
            .count { it }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

private data class Report(val levels: List<Int>) {
    companion object {
        fun from(row: List<String>): Report {
            return Report(row.map { it.toInt() })
        }
    }

    fun isValid(): Boolean {
        val firstDiffNegative = (levels[0] - levels[1]) < 0
        for (i in 1..<levels.size) {
            val diff = levels[i - 1] - levels[i]
            if (abs(diff) !in 1..3) {
                return false
            }

            if (firstDiffNegative != (diff < 0)) {
                return false
            }
        }
        return true
    }

    fun extraCheck(): Boolean {
        for (i in levels.indices) {
            if (Report(levels.filterIndexed { index, _ -> index != i }).isValid()) {
                return true
            }
        }
        return false
    }
}

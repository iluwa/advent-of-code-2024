import java.util.function.BiFunction

fun main() {
    runDay("07", 3749) { input ->
        val parsedInput = Input.from(input)

        parsedInput.filter {
            val res = allResults(it.l, listOf(
                BiFunction { a, b -> a + b },
                BiFunction { a, b -> a * b }
            ))
            res.contains(it.desiredNumber)
        }.sumOf { it.desiredNumber }
    }

    runDay("07", 11387) { input ->
        val parsedInput = Input.from(input)

        parsedInput.filter {
            val res = allResults(
                it.l, listOf(
                    BiFunction { a, b -> a + b },
                    BiFunction { a, b -> a * b },
                    BiFunction { a, b -> (a.toString() + b.toString()).toLong() },
                )
            )
            res.contains(it.desiredNumber)
        }.sumOf { it.desiredNumber }
    }
}

private data class Input(val desiredNumber: Long, val l: List<Long>) {
    companion object {
        fun from(input: List<String>): List<Input> {
            return input.map {
                val split = it.split(": ")
                Input(split[0].toLong(), split[1].toListLong())
            }
        }
    }
}

private fun String.toListLong(): List<Long> {
    return this.split(" ").map { it.toLong() }
}

private fun allResults(
    numbers: List<Long>,
    accumulators: List<BiFunction<Long, Long, Long>>
): Set<Long> {
    val res = mutableSetOf<Long>()
    allResultsReq(
        numbers,
        1,
        numbers[0],
        res,
        accumulators
    )
    return res
}

private fun allResultsReq(
    numbers: List<Long>,
    i: Int,
    acc: Long,
    res: MutableSet<Long>,
    accumulators: List<BiFunction<Long, Long, Long>>
) {
    if (i == numbers.size) {
        res.add(acc)
        return
    }

    accumulators.forEach {
        allResultsReq(numbers, i + 1, it.apply(acc, numbers[i]), res, accumulators)
    }
}

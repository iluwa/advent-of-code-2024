fun main() {
    runDay("11", 55312) { input ->
        var arr = input.first().split(" ").map { it.toLong() }
        for (i in 1..25) {
            arr = nextTick(arr)
        }
        arr.size
    }

    runDay("11", null) { input ->
        var arr = input.first().split(" ").map { it.toLong() }
            .groupingBy { it }
            .eachCount()
            .mapValues { it.value.toLong() }

        for (i in 1..75) {
            arr = nextTick(arr)
        }
        arr.values.sumOf { it }
    }
}

private fun nextTick(m: Map<Long, Long>): Map<Long, Long> {
    val res = mutableMapOf<Long, Long>()
    for ((k, v) in m) {
        val nextNum = decideNextNum(k)
        for (n in nextNum) {
            res[n] = res.getOrDefault(n, 0) + v
        }
    }
    return res
}

private fun decideNextNum(n: Long): List<Long> {
    return when {
        n == 0L -> listOf(1L)
        n.toString().length % 2 == 0 -> n.splitInTwoNumbers()
        else -> listOf(n * 2024L)
    }
}

private fun nextTick(arr: List<Long>): List<Long> {
    return arr.flatMap { decideNextNum(it) }
}

private fun Long.splitInTwoNumbers(): List<Long> {
    val length = this.toString().length
    return listOf(
        this.toString().substring(0..<length / 2).toLong(),
        this.toString().substring(length / 2..<length).toLong()
    )
}

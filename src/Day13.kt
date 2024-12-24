fun main() {
    runDay("13", 480) { input ->
        input.asSequence().chunked(4).map { Equation.of(it) }
            .mapNotNull { it.solve() }
            .map { it.first * 3 + it.second }
            .sum()
    }

    runDay("13", null) { input ->
        input.asSequence().chunked(4).map { Equation.of(it) }
            .map { it.increaseDesiredResult() }
            .mapNotNull { it.solve() }
            .map { it.first * 3 + it.second }
            .sum()
    }
}

private data class Equation(val ax: Long, val ay: Long, val bx: Long, val by: Long,
                            val sumX: Long, val sumY: Long) {
    companion object {
        fun of(threeLines: List<String>): Equation {
            val (ax, ay) = threeLines[0].replace("Button A: X+", "")
                .replace(" Y+", "")
                .split(",")
                .map { it.toLong() }
            val (bx, by) = threeLines[1].replace("Button B: X+", "")
                .replace(" Y+", "")
                .split(",")
                .map { it.toLong() }
            val (sumX, sumY) = threeLines[2].replace("Prize: X=", "")
                .replace(" Y=", "")
                .split(",")
                .map { it.toLong() }
            return Equation(ax, ay, bx, by, sumX, sumY)
        }
    }

    fun increaseDesiredResult(): Equation {
        return this.copy(
            sumX = sumX + 10000000000000,
            sumY = sumY + 10000000000000
        )
    }

    fun solve(): Pair<Long, Long>? {
        val del = (0 - by) * ax + bx * ay
        val sum = sumX * (0 - by) + sumY * bx
        if (sum % del != 0L) return null

        val x = sum / del

        if ((sumX - ax * x) % bx != 0L) return null
        val y = (sumX - ax * x) / bx
        return Pair(x, y)
    }
}

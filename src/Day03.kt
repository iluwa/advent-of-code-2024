fun main() {
    runDay("03", 161) { input ->
        input.sumOf { line ->
            val mules = parseLine(line)
            val res = mules.fold(0) { acc, mul ->
                acc + mul.l * mul.r
            }
            res
        }
    }

    runDay("03", 48) { input ->
        val hugeInput = input.joinToString()
        val s = Regex("don't\\(\\).*?do\\(\\)").replace(hugeInput, "")

        // remove a tail after "don't"
        val first = Regex("don't\\(\\)").find(s)?.groups
            ?.get(0)?.range?.first
        val preparedLine = if (first != null && first > 0) s.substring(0, first) else s

        val mules = parseLine(preparedLine)
        val res = mules.fold(0) { acc, mul ->
            acc + mul.l * mul.r
        }
        res
    }
}

private fun parseLine(line: String): List<Mul> {
    val matches = Regex("mul\\([0-9]{1,3},[0-9]{1,3}\\)").findAll(line)
    return matches.map { it.groupValues[0] }
        .map { Mul.of(it) }
        .toList()
}

private data class Mul(val l: Int, val r: Int) {
    companion object {
        fun of(s: String): Mul {
            val split = s.removePrefix("mul(")
                .removeSuffix(")")
                .split(",")
            return Mul(split[0].toInt(), split[1].toInt())
        }
    }
}

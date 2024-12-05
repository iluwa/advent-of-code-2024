fun main() {
    runDay("05", 143) { input ->
        val rulesHolder = RulesHolder.fromInput(input)
        val emptyLineIndex = input.indexOf("")

        val instructions = input.subList(emptyLineIndex + 1, input.size).map { line ->
            line.split(",").map { it.toInt() }
        }

        return@runDay instructions.filter { instruction ->
            instruction.generatePairs().all { rulesHolder.isValid(it) }
        }.sumOf { it[(it.size - 1) / 2] }
    }

    runDay("05", 123) { input ->
        val rulesHolder = RulesHolder.fromInput(input)
        val emptyLineIndex = input.indexOf("")

        val instructions = input.subList(emptyLineIndex + 1, input.size).map { line ->
            line.split(",").map { it.toInt() }
        }

        return@runDay instructions.filter { instruction ->
            instruction.generatePairs().any { !rulesHolder.isValid(it) }
        }.map { fixInstruction(it, rulesHolder) }
            .sumOf { it[(it.size - 1) / 2] }
    }
}

private fun MutableList<Int>.swap(e1: Int, e2: Int) {
    val i = this.indexOf(e1)
    val j = this.indexOf(e2)

    this[i] = e2
    this[j] = e1
}


private fun fixInstruction(instruction: List<Int>, rulesHolder: RulesHolder): List<Int> {
    val res = instruction.toMutableList()
    var invalid = res.generatePairs().filter { !rulesHolder.isValid(it) }
    while (invalid.isNotEmpty()) {
        res.swap(invalid[0].first, invalid[0].second)
        invalid = res.generatePairs().filter { !rulesHolder.isValid(it) }
    }
    return res
}

private fun List<Int>.generatePairs(): Set<Pair<Int, Int>> {
    val l = mutableSetOf<Pair<Int, Int>>()
    when (this.size) {
        2 -> l.add(Pair(this[0], this[1]))
        else -> {
            for (i in 1..<this.size) {
                l.add(Pair(this[0], this[i]))
            }
            l.addAll(this.subList(1, this.size).generatePairs())
        }
    }
    return l
}

private data class RulesHolder(val rules: Set<Pair<Int, Int>>) {
    companion object {
        fun fromInput(input: List<String>): RulesHolder {
            val emptyLineIndex = input.indexOf("")
            val rules = mutableSetOf<Pair<Int, Int>>()
            for (i in 0..<emptyLineIndex) {
                val split = input[i].split("|")
                rules.add(Pair(split[0].toInt(), split[1].toInt()))
            }
            return RulesHolder(rules)
        }
    }

    fun isValid(p: Pair<Int, Int>): Boolean {
        if (!rules.contains(p)) {
            if (rules.contains(Pair(p.second, p.first))) {
                return false
            }
        }
        return true
    }
}

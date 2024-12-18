fun main() {
    runDay("10", 36) { input ->
        val m = TopographicMap.of(input)
        val initialPoints = m.findInitialPoints()
        val points = mutableListOf<IJ>()
        points.addAll(initialPoints)
        var count = 0
        for (point in points) {
            var l: List<IJ> = mutableListOf(point)
            for (i in 1..9) {
                l = l.flatMap { m.findNextSteps(it) }
            }
            count += l.toSet().size
        }
        count
    }

    runDay("10", 81) { input ->
        val m = TopographicMap.of(input)
        val initialPoints = m.findInitialPoints()
        val points = mutableListOf<IJ>()
        points.addAll(initialPoints)
        var count = 0
        for (point in points) {
            var l: List<IJ> = mutableListOf(point)
            for (i in 1..9) {
                l = l.flatMap { m.findNextSteps(it) }
            }
            count += l.size
        }
        count
    }
}

private data class TopographicMap(val map: List<List<Int>>) {
    companion object {
        fun of(input: List<String>): TopographicMap {
            val map = input.map { it.toCharArray().map { c -> c.digitToInt() }.toList() }
            return TopographicMap(map)
        }
    }

    fun findInitialPoints(): List<IJ> {
        val res = mutableListOf<IJ>()
        for (i in map.indices) {
            for (j in map[0].indices) {
                if (map[i][j] == 0) {
                    res.add(IJ(i, j))
                }
            }
        }
        return res
    }

    fun findNextSteps(p: IJ): List<IJ> {
        return getNeighbours(p).filter { map[it.first][it.second] == map[p.first][p.second] + 1 }
    }

    private fun getNeighbours(p: IJ): List<IJ> {
        val res = mutableListOf<IJ>()
        if (p.first > 0) res.add(IJ(p.first - 1, p.second))
        if (p.second > 0) res.add(IJ(p.first, p.second - 1))
        if (p.first < map.size - 1) res.add(IJ(p.first + 1, p.second))
        if (p.second < map[0].size - 1) res.add(IJ(p.first, p.second + 1))
        return res
    }
}
import java.util.*

fun main() {
    runDay("12", 1930) { input ->
        val garden = Garden.of(input)
        val globalVisited = mutableSetOf<IJ>()
        var globalSum = 0
        for (i in garden.map.indices) {
            for (j in garden.map[0].indices) {
                if (globalVisited.contains(IJ(i, j))) {
                    continue
                } else {
                    val (visited, sum, _) = garden.findFullRegionSum(IJ(i, j))
                    globalVisited.addAll(visited)
                    globalSum += sum
                }
            }
        }
        globalSum
    }

    runDay("12", 1206) { input ->
        val garden = Garden.of(input)
        val globalVisited = mutableSetOf<IJ>()
        var globalSum = 0
        for (i in garden.map.indices) {
            for (j in garden.map[0].indices) {
                if (globalVisited.contains(IJ(i, j))) {
                    continue
                } else {
                    val (visited, _, sum) = garden.findFullRegionSum(IJ(i, j))
                    globalVisited.addAll(visited)
                    globalSum += sum
                }
            }
        }
        globalSum
    }
}

private data class Garden(val map: List<List<Char>>) {
    companion object {
        fun of(input: List<String>): Garden {
            val map = input.map { it.toCharArray().toList() }
            return Garden(map)
        }
    }

    fun findFullRegionSum(p: IJ): Triple<Set<IJ>, Int, Int> {
        val visited = mutableMapOf<IJ, Int>()
        val visitedToCorners = mutableMapOf<IJ, Int>()
        val toVisit = Stack<IJ>()
        toVisit.push(p)

        while (!toVisit.empty()) {
            val pop = toVisit.pop()
            if (visited.contains(pop)) {
                continue
            }

            val neighbours = getNeighbours(pop)
                .filter { map[it.first][it.second] == map[pop.first][pop.second] }
            visited[pop] = neighbours.count()
            visitedToCorners[pop] = corners(pop)
            toVisit.addAll(neighbours)
        }

        return Triple(
            visited.keys,
            visited.map { 4 - it.value }.sum() * visited.count(),
            visitedToCorners.values.sum() * visitedToCorners.count()
        )
    }

    private fun getNeighbours(p: IJ): List<IJ> {
        val res = mutableListOf<IJ>()
        if (p.first > 0) res.add(IJ(p.first - 1, p.second))
        if (p.second > 0) res.add(IJ(p.first, p.second - 1))
        if (p.first < map.size - 1) res.add(IJ(p.first + 1, p.second))
        if (p.second < map[0].size - 1) res.add(IJ(p.first, p.second + 1))
        return res
    }

    private fun corners(p: IJ): Int {
        var corners = 0
        val offsets = listOf(Pair(1, 1), Pair(1, -1), Pair(-1, 1), Pair(-1, -1))
        for (offset in offsets) {
            val verticalNeighbour = map.getOrNull(p.first + offset.first)?.getOrNull(p.second)
            val horizontalNeighbour = map.getOrNull(p.first)?.getOrNull(p.second + offset.second)
            val diagonalNeighbour = map.getOrNull(p.first + offset.first)?.getOrNull(p.second + offset.second)
            if ((verticalNeighbour == null || verticalNeighbour != map[p.first][p.second])
                && (horizontalNeighbour == null || horizontalNeighbour != map[p.first][p.second])
            ) {
                corners++
            }
            if (verticalNeighbour != null && verticalNeighbour == map[p.first][p.second]
                && horizontalNeighbour != null && horizontalNeighbour == map[p.first][p.second]
                && diagonalNeighbour != map[p.first][p.second]
            ) {
                corners++
            }
        }
        return corners
    }
}

fun main() {
    runDay("08", 14) { input ->
        val city = City.fromStringInput(input)
        val allAntennasPairs = city.allAntennasPairs()
        val antinodes = allAntennasPairs.map {
            it.antinodesNearest(city.layout.size - 1, city.layout[0].size - 1)
        }
        antinodes.flatten().toSet().size
    }

    runDay("08", 34) { input ->
        val city = City.fromStringInput(input)
        val allAntennasPairs = city.allAntennasPairs()
        val antinodes = allAntennasPairs.map {
            it.antinodesAll(city.layout.size - 1, city.layout[0].size - 1)
        }
        antinodes.flatten().toSet().size
    }
}

private data class City(
    val layout: List<List<Char>>,
    val antennas: Map<Char, List<IJ>>
) {
    companion object {
        fun fromStringInput(input: List<String>): City {
            val map = mutableMapOf<Char, MutableList<IJ>>()
            val l = input.mapIndexed { i, s ->
                s.mapIndexed { j, c ->
                    if (c != '.') {
                        if (map.containsKey(c)) map[c]?.add(IJ(i, j))
                        else map[c] = mutableListOf(IJ(i, j))
                    }
                    c
                }
            }
            return City(l, map)
        }
    }

    fun allAntennasPairs(): List<TwoAntennas> {
        val res = mutableListOf<TwoAntennas>()
        antennas.forEach {
            for (i in it.value.indices) {
                for (j in i + 1..<it.value.size) {
                    res.add(TwoAntennas.of(it.key, it.value[i], it.value[j]))
                }
            }
        }
        return res
    }
}

private data class TwoAntennas(val source: Char, val a1: IJ, val a2: IJ, val slope: Double) {
    companion object {
        fun of(source: Char, a1: IJ, a2: IJ): TwoAntennas {
            val diffI = a2.first - a1.first
            val diffJ = a2.second - a1.second
            return TwoAntennas(source, a1, a2, diffJ.toDouble() / diffI)
        }
    }

    fun antinodesNearest(maxI: Int, maxJ: Int): List<IJ> {
        val diffI = a2.first - a1.first
        val diffJ = a2.second - a1.second

        val res = mutableListOf<IJ>()

        val ant1 = IJ(a1.first - diffI, a1.second - diffJ)
        if (ant1.first in 0..maxI && ant1.second in 0..maxJ) {
            res.add(ant1)
        }

        val ant2 = IJ(a2.first + diffI, a2.second + diffJ)
        if (ant2.first in 0..maxI && ant2.second in 0..maxJ) {
            res.add(ant2)
        }

        return res
    }

    fun antinodesAll(maxI: Int, maxJ: Int): List<IJ> {
        val diffI = a2.first - a1.first
        val diffJ = a2.second - a1.second

        val res = mutableListOf<IJ>()
        res.add(a1)
        res.add(a2)

        var ant = a1
        while (true) {
            val ant1 = IJ(ant.first - diffI, ant.second - diffJ)
            if (ant1.first in 0..maxI && ant1.second in 0..maxJ) {
                res.add(ant1)
                ant = ant1
            } else break
        }

        ant = a2
        while (true) {
            val ant2 = IJ(ant.first + diffI, ant.second + diffJ)
            if (ant2.first in 0..maxI && ant2.second in 0..maxJ) {
                res.add(ant2)
                ant = ant2
            } else break
        }

        return res
    }
}

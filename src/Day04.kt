import kotlin.collections.mutableListOf as mutableListOf1

fun main() {
    runDay("04", 18) { input ->
        val arr = parseInput(input)
        var count = 0
        for (i in arr.indices) {
            for (j in arr[i].indices) {
                if (arr[i][j] == 'X') {
                    count += arr.allWordsAroundCoordinate(Pair(i, j)).count { it == "XMAS" }
                }
            }
        }
        count
    }

    runDay("04", 9) { input ->
        val arr = parseInput(input)
        var count = 0
        for (i in 1..<arr.size - 1) {
            for (j in 1..<arr[i].size - 1) {
                if (arr[i][j] == 'A' && arr.checkDiagonalLetters(Pair(i, j))) {
                    count ++
                }
            }
        }
        count
    }
}

private fun parseInput(input: List<String>): List<List<Char>> {
    return input.map { it.toCharArray().toList() }
}

private fun List<List<Char>>.allWordsAroundCoordinate(coordinate: Pair<Int, Int>): List<String> {
    val (i, j) = coordinate
    val res = mutableListOf1<String>()
    if (i > 2) res.add("" + this[i][j] + this[i - 1][j] + this[i - 2][j] + this[i - 3][j])
    if (j > 2) res.add("" + this[i][j] + this[i][j - 1] + this[i][j - 2] + this[i][j - 3])
    if (i < this.size - 3) res.add("" + this[i][j] + this[i + 1][j] + this[i + 2][j] + this[i + 3][j])
    if (j < this[0].size - 3) res.add("" + this[i][j] + this[i][j + 1] + this[i][j + 2] + this[i][j + 3])

    if (i > 2 && j > 2) res.add("" + this[i][j] + this[i - 1][j - 1] + this[i - 2][j - 2] + this[i - 3][j - 3])
    if (i < this.size - 3 && j < this[0].size - 3) {
        res.add("" + this[i][j] + this[i + 1][j + 1] + this[i + 2][j + 2] + this[i + 3][j + 3])
    }
    if (i < this.size - 3 && j > 2) {
        res.add("" + this[i][j] + this[i + 1][j - 1] + this[i + 2][j - 2] + this[i + 3][j - 3])
    }
    if (i > 2 && j < this[0].size - 3) {
        res.add("" + this[i][j] + this[i - 1][j + 1] + this[i - 2][j + 2] + this[i - 3][j + 3])
    }
    return res
}

private fun List<List<Char>>.checkDiagonalLetters(coordinate: Pair<Int, Int>): Boolean {
    val (i, j) = coordinate

    val firstDiagonal = "" + this[i - 1][j - 1] + this[i][j] + this[i+1][j+1]
    val secondDiagonal = "" + this[i + 1][j - 1] + this[i][j] + this[i-1][j+1]

    return (firstDiagonal == "MAS" || firstDiagonal == "SAM")
            && (secondDiagonal == "MAS" || secondDiagonal == "SAM")
}

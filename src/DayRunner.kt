fun runDay(
    day: String,
    testResult: Int,
    r: (l: List<String>) -> Int
) {
    val testInput = readInput("Day${day}_test")
    check(r(testInput) == testResult)
    println("Check is okay")

    val input = readInput("Day${day}")
    val res = r(input)
    println("Day${day} result: $res")
}
fun runDay(
    day: String,
    testResultExpected: Int,
    r: (l: List<String>) -> Int
) {
    val testInput = readInput("Day${day}_test")

    val testResult = r(testInput)
    check(testResult == testResultExpected) { "$testResult != $testResultExpected" }
    println("Check is okay")

    val input = readInput("Day${day}")
    val res = r(input)
    println("Day${day} result: $res")
}
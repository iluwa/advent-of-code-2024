fun main() {
    runDay("06", 41) { input ->
        val field = Field.fromInput(input)
        while (field.nextTick() != NextTickResult.EXIT) {
        }
        field.path.size
    }

    runDay("06", 6) { input ->
        val field = Field.fromInput(input)

        var loopCount = 0
        for (i in field.arr.indices) {
            for (j in field.arr[0].indices) {
                if (field.arr[i][j]) {
                    field.addObstacle(Pair(i, j))
                    while (true) {
                        when (field.nextTick()) {
                            NextTickResult.EXIT -> break
                            NextTickResult.LOOP -> {
                                loopCount++
                                break
                            }

                            NextTickResult.IN_PROGRESS -> continue
                        }
                    }
                }
            }
        }
        loopCount
    }
}

enum class Direction {
    UP, DOWN, RIGHT, LEFT
}

enum class NextTickResult {
    EXIT, LOOP, IN_PROGRESS
}

private data class Field(
    private val initialArr: List<List<Boolean>>,
    var arr: List<MutableList<Boolean>>,
    val initialGuard: Guard,
    var guard: Guard,
    val path: MutableSet<Pair<Int, Int>>,
    val turnState: MutableSet<Guard>
) {
    companion object {
        fun fromInput(input: List<String>): Field {
            var guard: Guard? = null
            val arr = input.mapIndexed { i, line ->
                line.mapIndexed { j, char ->
                    when (char) {
                        '.' -> true
                        '#' -> false
                        else -> {
                            guard = Guard.from(char, Pair(i, j))
                            true
                        }
                    }
                }.toMutableList()
            }
            return Field(arr, arr, guard!!, guard!!, mutableSetOf(), mutableSetOf())
        }
    }

    fun addObstacle(position: Pair<Int, Int>) {
        arr = initialArr.map { it.toMutableList() }
        guard = initialGuard.copy()
        path.clear()
        turnState.clear()
        arr[position.first][position.second] = false
    }

    fun nextTick(): NextTickResult {
        var nextPosition = guard.nextPosition()
        if (nextPosition.first < 0 || nextPosition.second < 0
            || nextPosition.first > arr.size - 1 || nextPosition.second > arr[0].size - 1
        ) {
            return NextTickResult.EXIT
        }

        while (!arr[nextPosition.first][nextPosition.second]) {
            if (turnState.contains(guard)) {
                return NextTickResult.LOOP
            }

            turnState.add(guard.copy())
            guard.turnRight()
            nextPosition = guard.nextPosition()
        }

        guard.move(nextPosition)
        path.add(nextPosition)
        return NextTickResult.IN_PROGRESS
    }
}

private data class Guard(var direction: Direction, var position: Pair<Int, Int>) {
    companion object {
        fun from(char: Char, position: Pair<Int, Int>): Guard {
            val direction = when (char) {
                '^' -> Direction.UP
                'v' -> Direction.DOWN
                '>' -> Direction.RIGHT
                '<' -> Direction.LEFT
                else -> throw Exception()
            }
            return Guard(direction, position)
        }
    }

    fun move(position: Pair<Int, Int>) {
        this.position = position
    }

    fun nextPosition(): Pair<Int, Int> {
        return when (direction) {
            Direction.UP -> position.copy(first = position.first - 1)
            Direction.DOWN -> position.copy(first = position.first + 1)
            Direction.RIGHT -> position.copy(second = position.second + 1)
            Direction.LEFT -> position.copy(second = position.second - 1)
        }
    }

    fun turnRight() {
        this.direction = when (this.direction) {
            Direction.UP -> Direction.RIGHT
            Direction.DOWN -> Direction.LEFT
            Direction.RIGHT -> Direction.DOWN
            Direction.LEFT -> Direction.UP
        }
    }
}
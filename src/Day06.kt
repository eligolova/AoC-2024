const val OBSTRUCTION = '#'
val GUARD = charArrayOf('^', '>', '<', 'v')

enum class Direction(private val symbol: Char) {
    LEFT('<'), RIGHT('>'), UP('^'), DOWN('v');

    companion object {
        fun fromSymbol(c: Char): Direction {
            return entries.find { it.symbol == c }!!
        }
    }
}

fun main() {
    fun rotate(direction: Direction): Direction = when (direction) {
        Direction.LEFT -> Direction.UP
        Direction.RIGHT -> Direction.DOWN
        Direction.UP -> Direction.RIGHT
        Direction.DOWN -> Direction.LEFT
    }

    fun nextStep(direction: Direction, row: Int, column: Int): Pair<Int, Int> = when (direction) {
        Direction.LEFT -> Pair(row, column - 1)
        Direction.RIGHT -> Pair(row, column + 1)
        Direction.UP -> Pair(row - 1, column)
        Direction.DOWN -> Pair(row + 1, column)
    }

    fun findGuard(input: List<String>): Pair<Int, Int> {
        for (rowIndex in input.indices) {
            val row = input[rowIndex]
            val index = row.indexOfAny(GUARD)
            if (index != -1) {
                return Pair(rowIndex, index)
            }
        }
        throw IllegalArgumentException("No guard found")
    }

    fun part1(input: List<String>): Int {
        val visitedPairs = mutableSetOf<Pair<Int, Int>>()
        var (guardRow, guardColumn) = findGuard(input)
        //move guard

        var direction = Direction.fromSymbol(input[guardRow][guardColumn])
        while (true) {
            val (nextRow, nextColumn) = nextStep(direction, guardRow, guardColumn)
            //check if guard has left the boundaries
            if (nextRow < 0 || nextRow >= input.size || nextColumn < 0 || nextColumn >= input[nextRow].length) {
                if (input[guardRow][guardColumn] != 'X') visitedPairs.add(Pair(guardRow, guardColumn))
                break
            }
            if (input[nextRow][nextColumn] == OBSTRUCTION) {
                direction = rotate(direction)
            } else {
                //make the step
                if (input[guardRow][guardColumn] != 'X') visitedPairs.add(Pair(guardRow, guardColumn))
                guardRow = nextRow
                guardColumn = nextColumn
            }
        }
        return visitedPairs.count()
    }

    fun containsGuardLoop(
        direction: Direction,
        guardRow: Int,
        guardColumn: Int,
        input: List<String>
    ): Boolean {
        var direction1 = direction
        var guardRow1 = guardRow
        var guardColumn1 = guardColumn
        val guardPositions = mutableSetOf<Triple<Direction, Int, Int>>()
        while (true) {
            //check loop
            if (guardPositions.contains(Triple(direction1, guardRow1, guardColumn1))) {
                return true
            }
            val (nextRow, nextColumn) = nextStep(direction1, guardRow1, guardColumn1)
            guardPositions.add(Triple(direction1, guardRow1, guardColumn1))
            //check if guard has left the boundaries
            if (nextRow < 0 || nextRow >= input.size || nextColumn < 0 || nextColumn >= input[nextRow].length) {
                if (input[guardRow1][guardColumn1] != 'X') guardPositions.add(
                    Triple(
                        direction1,
                        guardRow1,
                        guardColumn1
                    )
                )
                break
            }
            if (input[nextRow][nextColumn] == OBSTRUCTION) {
                direction1 = rotate(direction1)
            } else {
                //make the step
                if (input[guardRow1][guardColumn1] != 'X') guardPositions.add(
                    Triple(
                        direction1,
                        guardRow1,
                        guardColumn1
                    )
                )
                guardRow1 = nextRow
                guardColumn1 = nextColumn
            }
        }
        return false
    }

    fun part2(input: List<String>): Int {
        val (guardRow, guardColumn) = findGuard(input)
        //move guard
        val direction = Direction.fromSymbol(input[guardRow][guardColumn])
        var loopCount = 0
        for (rowIndex in input.indices) {
            val row = input[rowIndex]
            for (columnIndex in row.indices) {
                if (rowIndex == guardRow && columnIndex == guardColumn) {
                    continue
                }
                val inputWithNewObstacle = input.toMutableList()
                val updatedRow = inputWithNewObstacle[rowIndex].toCharArray().apply {
                    this[columnIndex] = OBSTRUCTION
                }.concatToString()
                inputWithNewObstacle[rowIndex] = updatedRow
                if (containsGuardLoop(direction, guardRow, guardColumn, inputWithNewObstacle)) loopCount++
            }
        }
        return loopCount
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)
}

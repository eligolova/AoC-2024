fun main() {

    data class GridPoint(val row: Int, val col: Int, val value: Int)


    //Improvements to add: memoization of visited points and their solution counts
    fun traverse(
        gridPoint: GridPoint,
        input: List<String>,
        distinctEnd: MutableSet<GridPoint>?,
    ): Int {
        if (input[gridPoint.row][gridPoint.col].digitToInt2() == 9) {
            return if (distinctEnd == null || distinctEnd.add(gridPoint)) {
                1
            } else 0
        }

        val rowIndex = gridPoint.row
        val colIndex = gridPoint.col
        val nextValue = input[gridPoint.row][gridPoint.col].digitToInt2() + 1
        var sumOfSolutions = 0
        //up
        if (rowIndex > 0 && input[rowIndex - 1][colIndex].digitToInt2() == nextValue) {
            val gp = GridPoint(rowIndex - 1, colIndex, nextValue)
            sumOfSolutions += traverse(gp, input, distinctEnd)
        }
        //right
        if (colIndex < input[rowIndex].lastIndex && input[rowIndex][colIndex + 1].digitToInt2() == nextValue) {
            val gp = GridPoint(rowIndex, colIndex + 1, nextValue)
            sumOfSolutions += traverse(gp, input, distinctEnd)
        }

        //left
        if (colIndex > 0 && input[rowIndex][colIndex - 1].digitToInt2() == nextValue) {
            val gp = GridPoint(rowIndex, colIndex - 1, nextValue)
            sumOfSolutions += traverse(gp, input, distinctEnd)
        }
        //down
        if (rowIndex < input.lastIndex && input[rowIndex + 1][colIndex].digitToInt2() == nextValue) {
            val gp = GridPoint(rowIndex + 1, colIndex, nextValue)
            sumOfSolutions += traverse(gp, input, distinctEnd)
        }

        return sumOfSolutions
    }

    fun getStartPoints(input: List<String>): MutableList<GridPoint> {
        val startPoints = mutableListOf<GridPoint>()
        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, value ->
                if (value == '0') {
                    startPoints.add(GridPoint(rowIndex, colIndex, 0))
                }
            }
        }
        return startPoints
    }

    fun part1(input: List<String>): Int {
        val startPoints = getStartPoints(input)
        return startPoints.sumOf {
            traverse(it, input, mutableSetOf())
        }
    }

    fun part2(input: List<String>): Any {
        val startPoints = getStartPoints(input)
        return startPoints.sumOf {
            traverse(it, input, null)
        }
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36)
    check(part2(testInput) == 81)
//    val input = readInput("Day10")
//    part1(input).println()
//    part2(input).println()
}

private fun Char.digitToInt2(): Int {
    if (this.isDigit()) return this.digitToInt()
    return -1
}

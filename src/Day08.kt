import kotlin.math.absoluteValue

fun main() {
    data class GridPoint(val row: Int, val col: Int)

    fun MutableSet<GridPoint>.addIfInBounds(rowIndex: Int, colIndex: Int, rowMaxIndex: Int, colMaxIndex: Int): Boolean {
        if (rowIndex in 0..rowMaxIndex && colIndex >= 0 && colIndex <= colMaxIndex) {
            this.add(GridPoint(rowIndex, colIndex))
            return true
        }
        return false
    }

    fun MutableSet<GridPoint>.addIfInBounds(gridPoint: GridPoint, rowMaxIndex: Int, colMaxIndex: Int): Boolean {
        return addIfInBounds(gridPoint.row, gridPoint.col, rowMaxIndex, colMaxIndex)
    }

    fun getMapOfAntennas(
        input: List<String>
    ): HashMap<Char, MutableList<GridPoint>> {
        val mapOfAntennas = HashMap<Char, MutableList<GridPoint>>()
        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, it ->
                if (it != '.') {
                    mapOfAntennas.getOrPut(it) { mutableListOf() }.add(GridPoint(rowIndex, colIndex))
                }

            }
        }
        return mapOfAntennas
    }

    fun part1(input: List<String>): Int {
        val mapOfAntennas = getMapOfAntennas(input)
        val antinodePoints = mutableSetOf<GridPoint>()
        val rowMax = input.lastIndex
        val colMax = input[0].lastIndex

        //we don't actually care about what antenna it is as they're already grouped together
        for (antennas in mapOfAntennas.values) {
            while (antennas.isNotEmpty()) {
                val first = antennas.removeFirst()
                for (pairAntenna in antennas) {
                    val rowDifference = first.row - pairAntenna.row
                    val colDifference = first.col - pairAntenna.col
                    antinodePoints.addIfInBounds(
                        rowIndex = first.row + rowDifference,
                        colIndex = first.col + colDifference,
                        rowMaxIndex = rowMax,
                        colMaxIndex = colMax
                    )
                    antinodePoints.addIfInBounds(
                        rowIndex = pairAntenna.row + rowDifference.absoluteValue,
                        colIndex = pairAntenna.col - colDifference,
                        rowMaxIndex = rowMax,
                        colMaxIndex = colMax
                    )
                }
            }
        }
        return antinodePoints.count()
    }

    fun part2(input: List<String>): Int {
        val mapOfAntennas = getMapOfAntennas(input)
        val antinodePoints = mutableSetOf<GridPoint>()
        val rowMax = input.lastIndex
        val colMax = input[0].lastIndex

        //we don't actually care about what antenna it is as they're already grouped together
        for (antennas in mapOfAntennas.values) {
            while (antennas.isNotEmpty()) {
                val first = antennas.removeFirst()
                antinodePoints.add(first)

                for (pairAntenna in antennas) {
                    val rowDifference = first.row - pairAntenna.row
                    val colDifference = first.col - pairAntenna.col

                    val getNextPoint: (GridPoint) -> GridPoint = { gp ->
                        GridPoint(gp.row + rowDifference, gp.col + colDifference)
                    }
                    var currentPoint = first
                    while (true) {
                        currentPoint = getNextPoint(currentPoint)
                        if (!antinodePoints.addIfInBounds(
                                gridPoint = currentPoint,
                                rowMaxIndex = rowMax,
                                colMaxIndex = colMax
                            )
                        ) break
                    }
                    val getNextPoint2: (GridPoint) -> GridPoint = { gp ->
                        GridPoint(gp.row + rowDifference.absoluteValue, gp.col - colDifference)
                    }
                    currentPoint = pairAntenna
                    while (true) {
                        currentPoint = getNextPoint2(currentPoint)
                        if (!antinodePoints.addIfInBounds(
                                gridPoint = currentPoint,
                                rowMaxIndex = rowMax,
                                colMaxIndex = colMax
                            )
                        ) break
                    }
                }
            }
        }
        return antinodePoints.count()
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)
//    val input = readInput("Day08")
//    part1(input).println()
//    part2(input).println()
}

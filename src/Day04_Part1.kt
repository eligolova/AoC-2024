private const val FIRST_LETTER = 'X'
private const val LAST_LETTER = 'S'

fun getNextLetter(currentLetter: Char): Char {
    return when (currentLetter) {
        FIRST_LETTER -> 'M'
        'M' -> 'A'
        'A' -> LAST_LETTER
        else -> throw IllegalArgumentException("Unexpected letter $currentLetter")
    }
}

fun foundXmasRight(input: List<String>, row: Int, column: Int, expectedLetter: Char): Boolean {
    if (column == input[row].length) return false
    return if (input[row][column] == expectedLetter)
        expectedLetter == LAST_LETTER || foundXmasRight(
            input,
            row,
            column + 1,
            getNextLetter(expectedLetter)
        )
    else false
}

fun foundXmasLeft(input: List<String>, row: Int, column: Int, expectedLetter: Char): Boolean {
    if (column < 0) return false
    return if (input[row][column] == expectedLetter)
        expectedLetter == LAST_LETTER || foundXmasLeft(input, row, column - 1, getNextLetter(expectedLetter))
    else false
}

fun foundXmasDown(input: List<String>, row: Int, column: Int, expectedLetter: Char): Boolean {
    if (row == input.size) return false
    return if (input[row][column] == expectedLetter)
        expectedLetter == LAST_LETTER || foundXmasDown(input, row + 1, column, getNextLetter(expectedLetter))
    else false
}

fun foundXmasUp(input: List<String>, row: Int, column: Int, expectedLetter: Char): Boolean {
    if (row < 0) return false
    return if (input[row][column] == expectedLetter)
        expectedLetter == LAST_LETTER || foundXmasUp(input, row - 1, column, getNextLetter(expectedLetter))
    else false
}

fun foundXmasDownRight(input: List<String>, row: Int, column: Int, expectedLetter: Char): Boolean {
    if (row == input.size || column == input[row].length) return false
    return if (input[row][column] == expectedLetter)
        expectedLetter == LAST_LETTER || foundXmasDownRight(
            input,
            row + 1,
            column + 1,
            getNextLetter(expectedLetter)
        )
    else false
}

fun foundXmasUpRight(input: List<String>, row: Int, column: Int, expectedLetter: Char): Boolean {
    if (row < 0 || column == input[row].length) return false
    return if (input[row][column] == expectedLetter)
        expectedLetter == LAST_LETTER || foundXmasUpRight(
            input,
            row - 1,
            column + 1,
            getNextLetter(expectedLetter)
        )
    else false
}

fun foundXmasDownLeft(input: List<String>, row: Int, column: Int, expectedLetter: Char): Boolean {
    if (row == input.size || column < 0) return false
    return if (input[row][column] == expectedLetter)
        expectedLetter == LAST_LETTER || foundXmasDownLeft(
            input,
            row + 1,
            column - 1,
            getNextLetter(expectedLetter)
        )
    else false
}

fun foundXmasUpLeft(input: List<String>, row: Int, column: Int, expectedLetter: Char): Boolean {
    if (row < 0 || column < 0) return false
    return if (input[row][column] == expectedLetter)
        expectedLetter == LAST_LETTER || foundXmasUpLeft(
            input,
            row - 1,
            column - 1,
            getNextLetter(expectedLetter)
        )
    else false
}

fun foundXmas(input: List<String>, rowIndex: Int, columnIndex: Int): Int {
    val firstLetter = FIRST_LETTER
    val directions = listOf(
        ::foundXmasRight,
        ::foundXmasLeft,
        ::foundXmasDown,
        ::foundXmasUp,
        ::foundXmasDownRight,
        ::foundXmasDownLeft,
        ::foundXmasUpRight,
        ::foundXmasUpLeft
    )
    return directions
        .filter { it(input, rowIndex, columnIndex, firstLetter) }
        .size
}

fun part1(input: List<String>): Int {
    var count = 0
    for (rowIndex in 0..input.lastIndex) {
        val line = input[rowIndex]
        for (columnIndex in 0..line.lastIndex) {
            if (input[rowIndex][columnIndex] == FIRST_LETTER)
                count += foundXmas(input, rowIndex, columnIndex)
        }
    }
    return count
}

fun main() {
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
}

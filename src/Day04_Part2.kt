private const val X_MAS_CENTRE_LETTER = 'A'

fun part2(input: List<String>): Int {
    var count = 0
    //we're starting with the middle letter A so can skip out the first and last lines and columns
    for (rowIndex in 1..<input.lastIndex) {
        //assuming all input lines are the same length
        for (columnIndex in 1..<input[0].lastIndex) {
            if (foundCrossMas(input, rowIndex, columnIndex)) count++
        }
    }
    return count
}

//we don't need to check boundaries in any of these as we've specifically excluded X-MAS middles on the boundaries
fun isUpperLeftToRightDiagonal(input: List<String>, rowIndex: Int, columnIndex: Int): Boolean {
    return (input[rowIndex - 1][columnIndex - 1] == 'M' && input[rowIndex + 1][columnIndex + 1] == 'S') ||
            (input[rowIndex - 1][columnIndex - 1] == 'S' && input[rowIndex + 1][columnIndex + 1] == 'M')
}

fun isLowerLeftToRightDiagonal(input: List<String>, rowIndex: Int, columnIndex: Int): Boolean {
    return (input[rowIndex + 1][columnIndex - 1] == 'M' && input[rowIndex - 1][columnIndex + 1] == 'S') ||
            (input[rowIndex + 1][columnIndex - 1] == 'S' && input[rowIndex - 1][columnIndex + 1] == 'M')
}

fun foundCrossMas(input: List<String>, rowIndex: Int, columnIndex: Int): Boolean {
    return input[rowIndex][columnIndex] == X_MAS_CENTRE_LETTER &&
            isUpperLeftToRightDiagonal(input, rowIndex, columnIndex) && isLowerLeftToRightDiagonal(
        input,
        rowIndex,
        columnIndex
    )
}

fun main() {
    val testInput = readInput("Day04_test")
    check(part2(testInput) == 9)

//    val input = readInput("Day04")
//    part2(input).println()
}

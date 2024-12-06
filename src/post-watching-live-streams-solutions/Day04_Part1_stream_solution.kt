package `post-watching-live-streams-solutions`

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val leftToRight = input
        val rightToLeft = input.map { it.reversed() }
        val topToBottom = input.pivot()
        val bottomToTop = topToBottom.map { it.reversed() }

        //diagonals
        val topLeftToBottomRight = leftToRight.tipRight()
        val bottomRightToTopLeft = topLeftToBottomRight.map { it.reversed() }
        val topRightToBottomLeft = rightToLeft.tipRight()
        val bottomLeftToTopRight = topRightToBottomLeft.map { it.reversed() }

        val listOfInputs = leftToRight +
                rightToLeft +
                topToBottom +
                bottomToTop +
                topLeftToBottomRight +
                topRightToBottomLeft +
                bottomLeftToTopRight +
                bottomRightToTopLeft

        return listOfInputs.sumOf {
            "XMAS".toRegex().findAll(it).count()
        }
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
}

private fun List<String>.tipRight(): List<String> {
    val chars = this
    return buildList {
        for (col in chars[0].indices) {
            add(getDiagonal(chars, 0, col))
        }
        //the first index will have been covered above
        for (col in 1 until chars[0].length) {
            add(getDiagonal(chars, col, 0))
        }
    }
}

private fun getDiagonal(input: List<String>, rowIndex: Int, colIndex: Int): String {
    var row = rowIndex
    var col = colIndex
    return buildString {
        //assuming all rows are the same size
        while (row < input.size && col < input[0].length) {
            append(input[row++][col++])
        }
    }
}

private fun List<String>.pivot(): List<String> {
    val chars = this
    return buildList {
        for (i in chars[0].indices) {
            add(buildString {
                for (j in chars[i].indices) {
                    append(chars[j][i])
                }
            })
        }
    }
}

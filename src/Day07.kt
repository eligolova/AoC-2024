fun main() {
    //This assumes no negative inputs and no 0 inputs
    fun isSumPossible(current: Long, remainingValues: List<Long>): Boolean {
        if (remainingValues.isEmpty()) return current == 0L
        if (current <= 0) return false
        val shouldDivide = current % remainingValues.last() == 0L
        return (shouldDivide && isSumPossible(
            current / remainingValues.last(),
            remainingValues.dropLast(1)
        )) || isSumPossible(current - remainingValues.last(), remainingValues.dropLast(1))
    }

    fun part1(input: List<String>): Long {
        val equations = input.map { equation ->
            val matches = """(\d+)""".toRegex().findAll(equation)
            Pair(matches.first().value.toLong(), matches.drop(1).map { match -> match.value.toLong() }.toList())
        }
        return equations.sumOf {
            if (isSumPossible(it.first, it.second)) it.first else 0
        }
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
//    val input = readInput("Day07")
//    part1(input).println()
}

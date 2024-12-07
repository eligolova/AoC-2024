fun isSumPossible(current: Long, remainingValues: List<Long>, target: Long): Boolean {
    if (remainingValues.isEmpty()) return current == target
    if (current > target) return false
    val firstValue = remainingValues[0]
    val newArray = remainingValues.dropFirst()
    return isSumPossible(current * firstValue, newArray, target) || isSumPossible(
        current + firstValue,
        newArray,
        target
    ) || isSumPossible("$current${remainingValues.first()}".toLong(), newArray, target)
}

private fun <E> List<E>.dropFirst(): List<E> {
    val new = this.toMutableList()
    new.removeFirst()
    return new
}

//This assumes no negative inputs and no 0 inputs
//works backwards to reach 0
fun isSumPossible(current: Long, remainingValues: List<Long>): Boolean {
    if (remainingValues.isEmpty()) return current == 0L
    if (current <= 0) return false
    return isSumPossibleByMultiplication(current, remainingValues)
            || isSumPossible(current - remainingValues.last(), remainingValues.dropLast(1))
}

fun isSumPossibleByMultiplication(current: Long, remainingValues: List<Long>): Boolean {
    val canDivide = current % remainingValues.last() == 0L
    return canDivide && isSumPossible(
        current =
            current / remainingValues.last(),
        remainingValues.dropLast(1)
    )
}

fun main() {

    fun getEquations(input: List<String>): List<Pair<Long, List<Long>>> {
        val equations = input.map { equation ->
            val matches = """(\d+)""".toRegex().findAll(equation)
            Pair(matches.first().value.toLong(), matches.drop(1).map { match -> match.value.toLong() }.toList())
        }
        return equations
    }

    fun part1(input: List<String>): Long {
        val equations = getEquations(input)
        return equations.sumOf {
            if (isSumPossible(it.first, it.second)) it.first else 0
        }
    }

    fun part2(input: List<String>): Long {
        val equations = getEquations(input)
        return equations.sumOf {
            if (isSumPossible(
                    current = it.second.first(),
                    remainingValues = it.second.dropFirst(),
                    target = it.first
                )
            ) it.first else 0
        }
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)
//    val input = readInput("Day07")
//    part1(input).println()
//    part2(input).println()
}

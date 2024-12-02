fun main() {
    fun areLevelsSafe(levels: List<Int>): Boolean =
        levels.zipWithNext().all { (a, b) -> b - a in 1..3 } ||
                levels.zipWithNext().all { (a, b) -> a - b in 1..3 }

    //a Dampner is removing one level from the list
    fun areLevelsSafeWithDampener(levels: List<Int>): Boolean {
        for (i in levels.indices) {
            if (
                areLevelsSafe(
                    levels.filterIndexed { index, _ -> index != i }
                )
            ) return true
        }
        return false
    }

    fun getLevels(input: List<String>) = input.map {
        it.trim().split("\\s+".toRegex())
            .map { num -> num.toInt() }
    }

    fun part1(input: List<String>): Int =
        getLevels(input)
            .count { areLevelsSafe(it) }

    fun part2(input: List<String>): Int =
        getLevels(input)
            .count { areLevelsSafeWithDampener(it) }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

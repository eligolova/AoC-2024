import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        //fetch the sorted lists
        val (left, right) = input
            .map { it.trim().split("\\s+".toRegex()) }
            .map { it[0].toInt() to it[1].toInt() }
            .unzip()
            .let { (l, r) -> l.sorted() to r.sorted() }

        //calculate the sum
        return left.zip(right) { l, r -> abs(l - r) }.sum()
    }

    fun part2(input: List<String>): Int {
        val (left, right) = input
            .map { it.trim().split("\\s+".toRegex()) }
            .map { it[0].toInt() to it[1].toInt() }
            .unzip()
        val counts = right.groupingBy { it }.eachCount()
        return left.fold(0) { acc, num -> acc + num * counts.getOrDefault(num, 0) }
    }

    // Read a large test input from the `src/test_input/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

//    // Read the input from the `src/test_input/Day01.txt` file.
//    val input = readInput("Day01")
//    part1(input).println()
//    part2(input).println()
}

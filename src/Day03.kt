fun main() {
    fun part1(input: List<String>): Int {
        val mulregex = """mul\((\d+),(\d+)\)""".toRegex()
        return input
            .map { line ->
                mulregex.findAll(line)
                    .map { match ->
                        // Extract the numbers inside the regex parentheses
                        val num1 = match.groupValues[1].toInt()
                        val num2 = match.groupValues[2].toInt()
                        num1 * num2
                    }.toList()
            }.flatten().sum()
    }

    val testInput = listOf("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")
    part1(testInput)
    check(part1(testInput) == 161)

    val input = readInput("Day03")
    part1(input).println()
}

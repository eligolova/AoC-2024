fun main() {
    fun calculateInstructions(input: List<String>, mulregex: Regex): Int {
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

    fun part1(input: List<String>): Int {
        val mulregex = """mul\((\d+),(\d+)\)""".toRegex()
        return calculateInstructions(input, mulregex)
    }

    fun part2(input: List<String>): Int {
        val instructions = input.fold(StringBuilder()) { sb, str -> sb.append(str) }.split("don't")
        val doInstructions = mutableListOf<String>()
        if(instructions.isNotEmpty()) doInstructions.add(instructions.first())
        for(i in 1 until instructions.size) {
            val doInstruction = instructions[i].substringAfter("""do()""", "")
            doInstructions.add(doInstruction)
        }
        val mulregex = """mul\((\d+),(\d+)\)""".toRegex()
        return calculateInstructions(doInstructions, mulregex)
    }

    val testInput = listOf("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")
    check(part1(testInput) == 161)
    val testInput2 = listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")
    check(part2(testInput2) == 48)

//    val input = readInput("Day03")
//    part1(input).println()
//    part2(input).println()
}

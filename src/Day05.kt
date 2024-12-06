import kotlin.math.min

fun main() {

    fun part1(input: List<String>): Int {
        //get page ordering rules
        val orderRules = HashMap<String, MutableSet<String>>()
        for (rule in input) {
            val split = rule.split('|')
            if (split.size != 2) break
            val orDefault = orderRules.getOrDefault(split[0], mutableSetOf())
            orDefault.add(split[1])
            orderRules[split[0]] = orDefault
        }

        var sum = 0
        iSet@ for (instructionSet in input) {
            val instructions = instructionSet.split(',')
            if (instructions.size <= 1) continue
            for (i in 1 until instructions.size) {
                val currentInstruction = instructions[i]
                val cannotAppearBeforeI = orderRules.getOrDefault(currentInstruction, emptyList())
                if (cannotAppearBeforeI.isNotEmpty()) {
                    for (j in 0 until i) { //75,97,47,61,53 bad
                        if (cannotAppearBeforeI.contains(instructions[j])) continue@iSet
                    }
                }

            }
            //validInstruction
            sum += instructions[instructions.size / 2].toInt()
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        //get page ordering rules
        val orderRules = HashMap<String, MutableSet<String>>()
        for (rule in input) {
            val split = rule.split('|')
            if (split.size != 2) break
            val orDefault = orderRules.getOrDefault(split[0], mutableSetOf())
            orDefault.add(split[1])
            orderRules[split[0]] = orDefault
        }

        val invalidInstructions = mutableListOf<List<String>>()
        iSet@ for (instructionSet in input) {
            val instructions = instructionSet.split(',')
            if (instructions.size <= 1) continue
            for (i in 1 until instructions.size) {
                val currentInstruction = instructions[i]
                val cannotAppearBeforeI = orderRules.getOrDefault(currentInstruction, emptyList())
                if (cannotAppearBeforeI.isNotEmpty()) {
                    for (j in 0 until i) { //75,97,47,61,53 bad
                        if (cannotAppearBeforeI.contains(instructions[j])) {
                            invalidInstructions.add(instructions)
                            continue@iSet
                        }
                    }
                }

            }
        }

        //handle invalid instructions
        val validInstructions = mutableListOf<List<String>>()
        for (instructions in invalidInstructions) {
            val newInstructionSet = mutableListOf<String>()
            for (instruction in instructions) {

                val cannotAppearBeforeI = orderRules.getOrDefault(instruction, emptyList())
                if (cannotAppearBeforeI.isEmpty()) {
                    newInstructionSet.add(instruction)
                } else {
                    //do some ordering
                    //find max index
                    val minIndex = newInstructionSet.size

                    val firstIndexOfCannotAppearBefore = cannotAppearBeforeI.fold(minIndex) { acc, inst ->
                        val index = if (newInstructionSet.contains(inst)) newInstructionSet.indexOf(inst) else minIndex
                        min(acc, index)
                    }
                    newInstructionSet.add(firstIndexOfCannotAppearBefore, instruction)
                }
            }
            validInstructions.add(newInstructionSet)
        }
        return validInstructions.sumOf { it[it.size / 2].toInt() }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

//    val input = readInput("Day05")
//    part2(input).println()
}

import kotlin.math.min

fun main() {

    //initial solution
    @Suppress("unused")
    fun getOrderRulesNotUsingFold(
        input: List<String>,
    ): HashMap<String, MutableSet<String>> {
        val orderRules = HashMap<String, MutableSet<String>>()
        input.takeWhile { line -> line.isNotEmpty() }.forEach { rule ->
            val split = rule.split('|')
            orderRules.getOrPut(split[0]) { mutableSetOf() }.add(split[1])
        }
        return orderRules
    }

    fun getOrderRules(
        input: List<String>,
    ): HashMap<String, MutableSet<String>> =
        input.takeWhile { line -> line.isNotEmpty() }.fold(HashMap()) { orderRules,
                                                                        rule ->
            val split = rule.split('|')
            orderRules.getOrPut(split[0]) { mutableSetOf() }.add(split[1])
            orderRules
        }

    fun getInstructionSets(
        input: List<String>,
    ) = input.takeLastWhile { line -> line.isNotEmpty() }.toList()


    fun handleInstructions(
        instructionSets: List<String>,
        orderRules: HashMap<String, MutableSet<String>>,
        onInvalid: ((List<String>) -> Unit)? = null,
        onValid: ((List<String>) -> Unit)? = null
    ) {
        iSet@ for (instructionSet in instructionSets) {
            val instructions = instructionSet.split(',')
            for (i in 1 until instructions.size) {
                val currentInstruction = instructions[i]
                val adjacenceyList = orderRules.getOrDefault(currentInstruction, emptyList())
                if (adjacenceyList.isNotEmpty()) {
                    //check that none of these letter appear before the current letter
                    for (j in 0 until i) {
                        if (adjacenceyList.contains(instructions[j])) {
                            onInvalid?.invoke(instructions)
                            continue@iSet
                        }
                    }
                }
            }
            //validInstruction
            onValid?.invoke(instructions)
        }
    }

    fun part1(input: List<String>): Int {
        val orderRules = getOrderRules(input)
        val instructionSets = getInstructionSets(input)

        var sum = 0
        handleInstructions(
            instructionSets,
            orderRules,
            onValid = { instructions -> sum += instructions[instructions.size / 2].toInt() })
        return sum
    }

    fun part2(input: List<String>): Int {
        val orderRules = getOrderRules(input)
        val instructionSets = getInstructionSets(input)

        val invalidInstructions = mutableListOf<List<String>>()
        handleInstructions(
            instructionSets,
            orderRules,
            onInvalid = { instrcutions -> invalidInstructions.add(instrcutions) })

        //handle invalid instructions
        val newlyValidInstructions = mutableListOf<List<String>>()
        for (instructions in invalidInstructions) {
            val newInstructionSet = mutableListOf<String>()
            for (instruction in instructions) {

                val adjacencyList = orderRules.getOrDefault(instruction, emptyList())
                if (adjacencyList.isEmpty()) {
                    newInstructionSet.add(instruction)
                } else {
                    //do some ordering
                    //find max index
                    val minIndex = newInstructionSet.size
                    val firstIndexOfCannotAppearBefore = adjacencyList.fold(minIndex) { acc, inst ->
                        val index = if (newInstructionSet.contains(inst)) newInstructionSet.indexOf(inst) else minIndex
                        min(acc, index)
                    }
                    newInstructionSet.add(firstIndexOfCannotAppearBefore, instruction)
                }
            }
            newlyValidInstructions.add(newInstructionSet)
        }
        return newlyValidInstructions.sumOf { it[it.size / 2].toInt() }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

//    val input = readInput("Day05")
//    part2(input).println()
}

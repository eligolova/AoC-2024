data class TestFile(val value: Int, var count: Int, var space: Int)

fun main() {
    fun getOrderedFileBlocks(input: String): MutableList<TestFile> {
        val orderedFileBlock = buildList {
            input.windowed(size = 2, step = 2, partialWindows = true).forEachIndexed { index, pair ->
                addLast(
                    TestFile(
                        value = index,
                        count = pair[0].digitToInt(),
                        space = pair.getOrElse(1) { '0' }.digitToInt()
                    )
                )
            }
        }
        return orderedFileBlock.toMutableList()
    }

    fun part1(input: String): Long {

        val orderedFileBlock = getOrderedFileBlocks(input)

        var frontPointer = 0
        var spaces = 0
        var backPointer = orderedFileBlock.lastIndex
        var lastFile = orderedFileBlock.last()
        var index = 0
        var sum = 0L
        while (frontPointer <= backPointer) {
            if (spaces > 0) {
                sum += lastFile.value * index++
                if (--lastFile.count == 0) {
                    lastFile = orderedFileBlock[--backPointer]
                }
                spaces--
            } else {
                val currentFile = orderedFileBlock[frontPointer]
                repeat(currentFile.count) {
                    sum += currentFile.value * index++
                }
                spaces = currentFile.space
                frontPointer++
            }
        }
        return sum
    }

    fun part2(input: String): Long {
        val orderedFileBlock = getOrderedFileBlocks(input)
        orderedFileBlock.reversed().forEach { file ->
            val indexOfCurrent = orderedFileBlock.indexOf(file)
            orderedFileBlock.subList(0, indexOfCurrent).find { it.space >= file.count }?.apply {
                //handle space
                orderedFileBlock[indexOfCurrent - 1].space += file.count + file.space
                file.space = this.space - file.count
                this.space = 0

                //move the file
                orderedFileBlock.remove(file)
                orderedFileBlock.add(orderedFileBlock.indexOf(this) + 1, file)
            }
        }

        var sum = 0L
        var index = 0
        orderedFileBlock.forEach { file ->
            repeat(file.count) {
                sum += file.value * index++
            }
            repeat(file.space) { index++ }
        }
        return sum
    }

    val testInput = "2333133121414131402"
    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)
//    val input = readInputAsLine("Day09")
//    part1(input).println()
//    part2(input).println()
}



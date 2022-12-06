import org.w3c.dom.ranges.Range

fun main() {
    fun part1(input: List<String>): Int {
        input.first()
                .windowed(4)
                .withIndex()
                .forEach {
                    if (it.value.toSet().count() == 4) {
                        return it.index + 4
                    }
                }
        return -1
    }

    fun part2(input: List<String>): Int {
        input.first()
                .windowed(14)
                .withIndex()
                .forEach {
                    if (it.value.toSet().count() == 14) {
                        return it.index + 14
                    }
                }
        return -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    val input = readInput("Day06")

    println(part1(listOf("mjqjpqmgbljsphdztnvjfqwrcgsmlb")))
    println(part1(input))

    println(part2(listOf("mjqjpqmgbljsphdztnvjfqwrcgsmlb")))
    println(part2(input))
}

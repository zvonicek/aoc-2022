fun main() {
    fun part1(input: List<String>): Int {
        return input.count { pair ->
            val lhs = pair.split(",")[0].split("-").map { it.toInt() }
            val rhs = pair.split(",")[1].split("-").map { it.toInt() }

            (lhs[0] >= rhs[0] && lhs[1] <= rhs[1]) || (lhs[0] <= rhs[0] && lhs[1] >= rhs[1])
        }
    }

    fun part2(input: List<String>): Int {
        return input.count { pair ->
            val lhs = pair.split(",")[0].split("-").map { it.toInt() }
            val rhs = pair.split(",")[1].split("-").map { it.toInt() }

            IntRange(lhs[0], lhs[1]).intersect(IntRange(rhs[0], rhs[1])).isNotEmpty()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val input = readInput("Day04")

    check2(part1(testInput), 2)
    println(part1(input))

    check2(part2(testInput), 4)
    println(part2(input))
}

fun main() {
    fun priority(char: Char): Int {
        return if (char.isUpperCase()) char.code - 38 else char.code - 96
    }

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val firstSet = HashSet(it.substring(0, it.length / 2).toCharArray().asList())
            val secondSet = HashSet(it.substring(it.length / 2).toCharArray().asList())
            val shared = firstSet.intersect(secondSet)

            priority(shared.first())
        }
    }

    fun part2(input: List<String>): Int {
        val sets = input.map { HashSet(it.toCharArray().asList()) }
        val badges = sets.windowed(3, step = 3).map { groupSets ->
            groupSets.reduce { acc, chars -> acc.intersect(chars).toHashSet() }.first()
        }
        return badges.map { priority(it) }.sum()
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val input = readInput("Day03")

    check2(part1(testInput), 157)
    println(part1(input))

    check2(part2(testInput), 70)
    println(part2(input))
}

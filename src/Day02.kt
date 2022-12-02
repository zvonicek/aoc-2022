fun main() {
    fun part1(input: List<String>): Int {
        return input.map {
            val first = it.split(" ")[0].toCharArray().first().code - 65
            val second = it.split(" ")[1].toCharArray().first().code - 88

            var sum = second + 1
            if (first == second) {
                sum += 3
            } else if (second - first == 1 || (first == 2 && second == 0)) {
                sum += 6
            }

            sum
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.map {
            val first = it.split(" ")[0].toCharArray().first().code - 65
            val second = it.split(" ")[1]

            when (second) {
                "X" -> {
                    val move = (first - 1).mod(3)
                    move + 1
                }
                "Y" -> first + 1 + 3
                "Z" -> {
                    val move = (first + 1).mod(3)
                    move + 1 + 6
                }
                else -> 0
            }
        }.sum()
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check2(part1(testInput), 15)
    check2(part2(testInput), 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

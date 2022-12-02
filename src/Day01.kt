fun main() {
    fun part1(input: List<String>): Int {
        var sums = arrayListOf<Int>(0)
        for (num in input) {
            if (num == "") {
                sums.add(0)
            } else {
                sums[sums.lastIndex] = sums[sums.lastIndex] + num.toInt()
            }
        }

        return sums.maxOf { it }
    }

    fun part2(input: List<String>): Int {
        var sums = arrayListOf<Int>(0)
        for (num in input) {
            if (num == "") {
                sums.add(0)
            } else {
                sums[sums.lastIndex] = sums[sums.lastIndex] + num.toInt()
            }
        }

        val sorted = sums.sortedDescending()
        return sorted[0] + sorted[1] + sorted[2]
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check2(part1(testInput), 24000)
    //check2(part2(testInput), 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

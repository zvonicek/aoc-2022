
data class Monkey(val items: MutableList<Long>, val operation: (Long) -> Long, val testValue: Int, val trueMonkey: Int, val falseMonkey: Int)

fun main() {
    fun load(input: List<String>): List<Monkey> {
        var items: List<Long>? = null
        var operation: ((Long) -> Long)? = null
        var testValue: Int? = null
        var trueMonkey: Int? = null
        var falseMonkey: Int? = null

        var monkeys = mutableListOf<Monkey>()

        input.forEach {
            val spaceParts = it.split(" ")

            if (it.startsWith("  Starting items")) {
                items = it.split(": ").last().split(", ").map { it.toLong() }
            } else if (it.startsWith("  Operation")) {
                if (it.contains("* old")) {
                    operation = { num -> num * num }
                } else if (it.contains(" *")) {
                    operation = { num -> num * spaceParts.last().toInt() }
                } else if (it.contains("+")) {
                    operation = { num -> num + spaceParts.last().toInt() }
                }
            } else if (it.startsWith("  Test")) {
                testValue = spaceParts.last().toInt()
            } else if (it.startsWith("    If true")) {
                trueMonkey = spaceParts.last().toInt()
            } else if (it.startsWith("    If false")) {
                falseMonkey = spaceParts.last().toInt()
                monkeys.add(Monkey(items!!.toMutableList(), operation!!, testValue!!, trueMonkey!!, falseMonkey!!))
            }
        }

        return  monkeys
    }

    fun part1(input: List<String>): Int {
        val monkeys = load(input)
        val inspectedCounts = hashMapOf<Int, Int>()

        repeat(20) {
            monkeys.withIndex().forEach { (index, monkey) ->
                monkey.items.forEach { item ->
                    val newItem = monkey.operation(item).floorDiv(3)

                    if (newItem.mod(monkey.testValue) == 0) {
                        monkeys[monkey.trueMonkey].items.add(newItem)
                    } else {
                        monkeys[monkey.falseMonkey].items.add(newItem)
                    }

                    inspectedCounts[index] = inspectedCounts.getOrDefault(index, 0) + 1
                }
                monkey.items.clear()
            }
        }

        val sortedCounts = inspectedCounts.values.sortedByDescending { it }
        return sortedCounts[0] * sortedCounts[1]
    }

    fun part2(input: List<String>): Long {
        val monkeys = load(input)

        val modBase = monkeys.map { it.testValue }.fold(1) { acc, i -> acc * i }
        val inspectedCounts = hashMapOf<Int, Long>()

        repeat(10000) {
            monkeys.withIndex().forEach { (index, monkey) ->
                monkey.items.forEach { item ->
                    val newItem = monkey.operation(item)
                            .mod(modBase).toLong()

                    if (newItem.mod(monkey.testValue) == 0) {
                        monkeys[monkey.trueMonkey].items.add(newItem)
                    } else {
                        monkeys[monkey.falseMonkey].items.add(newItem)
                    }

                    inspectedCounts[index] = inspectedCounts.getOrDefault(index, 0) + 1
                }
                monkey.items.clear()
            }
        }

        val sortedCounts = inspectedCounts.values.sortedByDescending { it }
        return sortedCounts[0] * sortedCounts[1]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    val input = readInput("Day11")

    println(part1(testInput))
    println(part1(input))

    println(part2(testInput))
    println(part2(input))
}

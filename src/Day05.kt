import org.w3c.dom.ranges.Range

fun main() {
    fun part1(input: List<String>): String {
        val stacks = hashMapOf<Int, ArrayList<String>>()

        input.forEach { line ->
            var stack = 0
            while ((line.startsWith("[") || line.startsWith("  ")) && line.count() >= stack*4+1) {
                if (stacks[stack] == null) {
                    stacks[stack] = arrayListOf<String>()
                }
                val crate = line.substring(stack*4+1, stack*4+2)
                if (crate != " ") {
                    stacks[stack]!!.add(crate)
                }
                stack += 1
            }

            val rule = "move (-?[0-9]+) from (-?[0-9]+) to (-?[0-9]+)".toRegex().find(line)
            if (rule != null) {
                val from = rule.groupValues[2].toInt() - 1
                val to = rule.groupValues[3].toInt() - 1

                for (i in 0 until rule.groupValues[1].toInt()) {
                    val item = stacks[from]!!.removeAt(0)
                    stacks[to]!!.add(0, item)
                }
            }
        }
        return stacks.map { it.value.first() }.joinToString("") { it }
    }

    fun part2(input: List<String>): String {
        val stacks = hashMapOf<Int, ArrayList<String>>()

        input.forEach { line ->
            var stack = 0
            while ((line.startsWith("[") || line.startsWith("  ")) && line.count() >= stack*4+1) {
                if (stacks[stack] == null) {
                    stacks[stack] = arrayListOf<String>()
                }
                val crate = line.substring(stack*4+1, stack*4+2)
                if (crate != " ") {
                    stacks[stack]!!.add(crate)
                }
                stack += 1
            }

            val rule = "move (-?[0-9]+) from (-?[0-9]+) to (-?[0-9]+)".toRegex().find(line)
            if (rule != null) {
                val from = rule.groupValues[2].toInt() - 1
                val to = rule.groupValues[3].toInt() - 1
                val n = rule.groupValues[1].toInt()

                val items = stacks[from]!!.take(n)
                stacks[to]!!.addAll(0, items)

                for (i in 0 until n) {
                    val item = stacks[from]!!.removeAt(0)
                }
            }
        }
        return stacks.map { it.value.first() }.joinToString("") { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val input = readInput("Day05")

    println(part1(testInput))
    println(part1(input))

    println(part2(testInput))
    println(part2(input))
}

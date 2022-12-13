import kotlinx.serialization.json.*

fun main() {
    fun String.parseList(): List<JsonElement> = Json.parseToJsonElement(this).jsonArray.toList()

    fun isRightOrder(lhs: List<JsonElement>, rhs: List<JsonElement>): Boolean? {
        val lhsIter = lhs.iterator()
        val rhsIter = rhs.iterator()

        while (lhsIter.hasNext() && rhsIter.hasNext()) {
            val lhsVal = lhsIter.next()
            val rhsVal = rhsIter.next()

            if (lhsVal.toString().toIntOrNull() != null && rhsVal.toString().toIntOrNull() != null) {
                if (lhsVal.toString().toInt() == rhsVal.toString().toInt()) {
                    continue
                } else {
                    return lhsVal.toString().toInt() < rhsVal.toString().toInt()
                }
            } else if (lhsVal.toString().startsWith("[") && rhsVal.toString().startsWith("[")) {
                val res = isRightOrder(lhsVal.jsonArray.toList(), rhsVal.jsonArray.toList())
                if (res != null) { return res } else { continue }
            } else if (lhsVal.toString().toIntOrNull() == null && rhsVal.toString().toIntOrNull() != null) {
                val res = isRightOrder(lhsVal.jsonArray.toList(), listOf(rhsVal))
                if (res != null) { return res } else { continue }
            } else if (lhsVal.toString().toIntOrNull() != null && rhsVal.toString().toIntOrNull() == null) {
                val res = isRightOrder(listOf(lhsVal), rhsVal.jsonArray.toList())
                if (res != null) { return res } else { continue }
            }
        }

        if (lhsIter.hasNext()) {
            return false
        } else if (rhsIter.hasNext()) {
            return true
        }
        return null
    }

    fun part1(input: List<String>): Int {
        val iterator = input.iterator()

        var indexSum = 0
        var index = 1
        while(iterator.hasNext()) {
            val lhs = iterator.next().parseList()
            val rhs = iterator.next().parseList()
            if (iterator.hasNext()) { iterator.next() }

            if (isRightOrder(lhs, rhs)!!) {
                indexSum += index
            }
            index += 1
        }
        return indexSum
    }

    fun part2(input: List<String>): Int {
        val modifiedInput = (input + listOf("[[2]]", "[[6]]"))
                .filter { it.isNotEmpty() }.map { it.parseList() }

        val sorted = modifiedInput.sortedWith(Comparator { o1, o2 -> if (isRightOrder(o1, o2)!!) -1 else 1 })

        val idx1 = sorted.indexOfFirst { it.toString() == "[[2]]" } + 1
        val idx2 = sorted.indexOfFirst { it.toString() == "[[6]]" } + 1

        return idx1 * idx2
    }

    val testInput = readInput("Day13_test")
    val input = readInput("Day13")

    println(part1(testInput))
    println(part1(input))

    println(part2(testInput))
    println(part2(input))
}

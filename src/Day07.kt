import kotlin.math.abs

sealed class Item {
    fun name(): String {
        when (this) {
            is Dir -> return this.name
            is File -> return this.name
        }
    }

    class Dir(val name: String, val items: ArrayList<Item>) : Item()
    class File(val name: String, val size: Int) : Item()
}

fun getSize(item: Item): Pair<Int, ArrayList<Int>> {
    return when (item) {
        is Item.File -> Pair(item.size, arrayListOf())
        is Item.Dir -> {
            val sizes = item.items.map { getSize(it) }
            val size = sizes.sumOf { it.first }

            val sizesList = arrayListOf<Int>(size)
            sizes.forEach { sizesList.addAll(it.second) }

            return Pair(size, sizesList)
        }
    }
}

fun getFsTree(input: List<String>): Item {
    val root = Item.Dir("/", arrayListOf())

    var currentPath = arrayListOf<Item.Dir>(root)
    input.forEach { line ->
        val commandParts = line.split(" ")

        if (line.startsWith("$ cd /")) {
        } else if (line.startsWith("$ ls")) {
        } else if (line.startsWith("$ cd ..")) {
            currentPath.removeLast()
        } else if (line.startsWith("$ cd")) {
            val dir = currentPath.last().items.first { it.name() == commandParts.last() }
            currentPath.add(dir as Item.Dir)
        } else if (line.startsWith("dir")) {
            currentPath.last().items.add(Item.Dir(commandParts.last(), arrayListOf()))
        } else if (commandParts[0].toIntOrNull() != null) {
            val size = commandParts[0].toInt()
            currentPath.last().items.add(Item.File(commandParts[1], size))
        } else {
            throw RuntimeException("illegal state");
        }
    }

    return root
}

fun main() {
    fun part1(input: List<String>): Int {
        val root = getFsTree(input)
        val sizes = getSize(root)
        return sizes.second.filter { it <= 100000 }.sum()
    }

    fun part2(input: List<String>): Int {
        val root = getFsTree(input)
        val sizes = getSize(root)

        val unusedSpace = 30000000 - (70000000 - sizes.first)

        var minDiff: Int? = null
        var size: Int? = null
        sizes.second.filter { it > unusedSpace }.forEach {
            val diff = abs(unusedSpace - it)
            if ( minDiff == null || size == null || diff < minDiff!!) {
                minDiff = diff
                size = it
            }
        }
        return size!!
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    val input = readInput("Day07")

    println(part1(testInput))
    println(part1(input))

    println(part2(testInput))
    println(part2(input))
}

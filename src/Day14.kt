import java.lang.Integer.max
import java.lang.Integer.min

fun main() {
    fun getGrid(input: List<String>): HashMap<Point2, Char> {
        val grid = hashMapOf<Point2, Char>()
        input.forEach { it ->
            it.split("->")
                    .map { point -> point.trim() }
                    .map { point -> point.split(",")[0].toInt() to point.split(",")[1].toInt() }
                    .windowed(2)
                    .forEach { (lhs, rhs) ->
                        for (i in min(lhs.first, rhs.first) .. max(lhs.first, rhs.first)) {
                            for (j in min(lhs.second, rhs.second) .. max(lhs.second, rhs.second)) {
                                grid[j to i] = '#'
                            }
                        }
                    }
        }
        return grid
    }

    fun HashMap<Point2, Char>.contains2(point: Point2, lowerBound: Int?): Boolean {
        return this.contains(point) || (lowerBound != null && point.first > lowerBound)
    }

    fun printGrid(grid: HashMap<Point2, Char>) {
        for (i in grid.keys.minOf { it.first }..kotlin.math.max(grid.keys.maxOf { it.first }, 0)) {
            for (j in grid.keys.minOf { it.second }..kotlin.math.max(grid.keys.maxOf { it.second }, 0)) {
                if (grid.contains(i to j)) {
                    print(grid[i to j])
                } else {
                    print(".")
                }
            }
            println()
        }
    }

    fun simulateSand(grid: HashMap<Point2, Char>, lowerBound: Int, lowerBoundFloor: Int?): Boolean {
        var sand = 0 to 500
        while(true) {
            if (sand.first > lowerBound) {
                return false
            }

            if (!grid.contains2(sand.first + 1 to sand.second, lowerBoundFloor)) {
                sand = sand.first + 1 to sand.second
            } else if (!grid.contains2(sand.first + 1 to sand.second - 1, lowerBoundFloor)) {
                sand = sand.first + 1 to sand.second - 1
            } else if (!grid.contains2(sand.first + 1 to sand.second + 1, lowerBoundFloor)) {
                sand = sand.first + 1 to sand.second + 1
            } else {
                if (sand == 0 to 500) {
                    return false
                }

                grid[sand] = 'o'
                break
            }
        }

        return true
    }

    fun part1(input: List<String>): Int {
        val grid = getGrid(input)
        val lowerBound = grid.keys.maxOf { it.first } - 1
        var i = 0

        while(true) {
            val res = simulateSand(grid, lowerBound, null)
            if (!res) {
                return i
            }

            i += 1
        }
    }

    fun part2(input: List<String>): Int {
        val grid = getGrid(input)
        val lowerBound = grid.keys.maxOf { it.first } + 1
        var i = 0

        while(true) {
            val res = simulateSand(grid, lowerBound, lowerBound)
            if (!res) {
                return i + 1
            }

            i += 1
        }
    }

    val testInput = readInput("Day14_test")
    val input = readInput("Day14")

    println(part1(testInput))
    println(part1(input))

    println(part2(testInput))
    println(part2(input))
}

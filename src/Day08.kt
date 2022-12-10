import kotlin.math.max

typealias Point2 = Pair<Int, Int>

fun main() {
    fun visibleIndexes(grid: List<List<Int>>, start: Point2, stepFunction: (Point2) -> Point2): Set<Point2> {
        var point: Point2 = start
        var res = mutableSetOf<Point2>()
        var maxValue = -1

        while (point.first >= 0 && point.second >= 0 && grid.count() > point.first && grid[point.first].count() > point.second) {
            if (grid[point.first][point.second] > maxValue) {
                maxValue = grid[point.first][point.second]
                res.add(point)
            }
            point = stepFunction(point)
        }

        return res
    }

    fun visibleIndexes2(grid: List<List<Int>>, start: Point2, stepFunction: (Point2) -> Point2): Set<Point2> {
        var point: Point2 = stepFunction(start)
        var res = mutableSetOf<Point2>()

        while (point.first >= 0 && point.second >= 0 && grid.count() > point.first && grid[point.first].count() > point.second) {
            res.add(point)

            if (grid[point.first][point.second] >= grid[start.first][start.second]) {
                break
            }

            point = stepFunction(point)
        }

        return res
    }

    fun part1(input: List<String>): Int {
        val grid = input.map { row -> row.toCharArray().map { it.toString().toInt() } }

        val visibleIndexes = arrayOf(
                (0..grid.count()).flatMap { visibleIndexes(grid, Point2(it, 0), stepFunction = { point -> Point2(point.first, point.second + 1) }) },
                (0..grid[0].count()).flatMap { visibleIndexes(grid, Point2(0, it), stepFunction = { point -> Point2(point.first + 1, point.second) }) },
                (grid.count() - 1 downTo 0).flatMap { visibleIndexes(grid, Point2(it, grid[0].count() - 1), stepFunction = { point -> Point2(point.first, point.second - 1) }) },
                (grid[0].count() downTo 0).flatMap { visibleIndexes(grid, Point2(grid.count() - 1, it), stepFunction = { point -> Point2(point.first - 1, point.second) }) },
        ).fold(setOf<Point2>()) { acc, sets -> acc.union(sets) }

        for (i in 0 until grid.count()) {
            val row = grid[i]
            for (j in 0 until row.count()) {
                if (visibleIndexes.contains(Pair(i, j))) {
                    print("X")
                } else {
                    print("_")
                }
            }
            println()
        }

        return visibleIndexes.count()
    }

    fun part2(input: List<String>): Int {
        val grid = input.map { row -> row.toCharArray().map { it.toString().toInt() } }

        var highestScenicScore = 0

        for (i in 0 until grid.count()) {
            val row = grid[i]
            for (j in 0 until row.count()) {
                val visibleIndexes = listOf(
                        visibleIndexes2(grid, Point2(i, j), stepFunction = { point -> Point2(point.first, point.second + 1) }),
                        visibleIndexes2(grid, Point2(i, j), stepFunction = { point -> Point2(point.first + 1, point.second) }),
                        visibleIndexes2(grid, Point2(i, j), stepFunction = { point -> Point2(point.first, point.second - 1) }),
                        visibleIndexes2(grid, Point2(i, j), stepFunction = { point -> Point2(point.first - 1, point.second) }),
                )

                val scenicScore = visibleIndexes.map { it.count() }.fold(1) { acc, count -> acc * count }
                highestScenicScore = max(highestScenicScore, scenicScore)
            }
        }

        return highestScenicScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    val input = readInput("Day08")

    println(part1(testInput))
    println(part1(input))

    println(part2(testInput))
    println(part2(input))
}

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Point(val x: Int, val y: Int) {
    fun isDiagonal(point: Point): Boolean {
        return abs(point.x - x) == 1 && abs(point.y - y) == 1
    }
    fun nextTo(point: Point): Boolean {
        return (abs(point.x - x) == 0 && abs(point.y - y) == 1) ||
                abs(point.x - x) == 1 && abs(point.y - y) == 0
    }
    fun isOffStraight(point: Point): Boolean {
        return (abs(point.x - x) == 2 && abs(point.y - y) == 0) ||
                (abs(point.x - x) == 0 && abs(point.y - y) == 2)
    }
    fun isOffDiagonal(point: Point): Boolean {
        return (abs(point.x - x) == 2 && abs(point.y - y) > 0) ||
                abs(point.x - x) > 0 && abs(point.y - y) == 2
    }
    fun straightTowards(point: Point): Point {
        if (point.x == x && point.y > y) {
            return Point(x, y+1)
        } else if (point.x > x && point.y == y) {
            return Point(x+1, y)
        } else if (point.x < x && point.y == y) {
            return Point(x-1, y)
        } else {
            return Point(x, y-1)
        }
    }
    fun diagonalTowards(point: Point): Point {
        if (point.x > x && point.y > y) {
            return Point(x+1, y+1)
        } else if (point.x > x && point.y < y) {
            return Point(x+1, y-1)
        } else if (point.x < x && point.y > y) {
            return Point(x-1, y+1)
        } else {
            return Point(x-1, y-1)
        }
    }
}

data class Rope(val H: Point, val T: Point) {
    fun step(direction: Direction): Rope {
        val newH = direction.moveStraight(H)
        return if (newH.nextTo(T) || newH.isDiagonal(T) || newH == T) {
            Rope(newH, T)
        } else {
            Rope(newH, H)
        }
    }
}

data class MultiKnotRope(val knots: List<Point>) {
    fun step(direction: Direction): MultiKnotRope {
        var previousKnotAfter: Point? = null

        return MultiKnotRope(knots.map {
            val newPoint = if (previousKnotAfter == null) {
                direction.moveStraight(it)
            } else if (previousKnotAfter!!.isOffStraight(it)) {
                it.straightTowards(previousKnotAfter!!)
            } else if (previousKnotAfter!!.isOffDiagonal(it)){
                it.diagonalTowards(previousKnotAfter!!)
            } else {
                // no-op
                it
            }

            previousKnotAfter = newPoint
            return@map newPoint
        })
    }

    fun print() {
        for (i in min(knots.minOf { it.y }, 0) .. max(knots.maxOf { it.y }, 0)) {
            for (j in min(knots.minOf { it.x }, 0) .. max(knots.maxOf { it.x }, 0)) {
                if (knots.contains(Point(j, i))) {
                    print(knots.indexOf(Point(j, i)))
                } else if (i == 0 && j == 0) {
                    print("s")
                } else {
                    print(".")
                }
            }
            println()
        }
    }
}

enum class Direction {
    L, R, U, D;

    fun moveStraight(point: Point): Point {
        return when (this) {
            L -> Point(point.x - 1, point.y)
            R -> Point(point.x + 1, point.y)
            U -> Point(point.x, point.y - 1)
            D -> Point(point.x, point.y + 1)
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        var rope = Rope(Point(0, 0), Point(0, 0))
        var tPositions = mutableSetOf<Point>(rope.T)

        input.forEach {
            val parts = it.split(" ")
            val direction = Direction.valueOf(parts[0])
            val distance = parts[1].toInt()

            repeat(distance) {
                rope = rope.step(direction)
                tPositions.add(rope.T)
            }
        }

        return tPositions.count()
    }

    fun part2(input: List<String>): Int {
        var rope = MultiKnotRope(Array(10) { Point(0, 0) }.toList())
        var tPositions = mutableSetOf<Point>(rope.knots.last())

        input.forEach {
            val parts = it.split(" ")
            val direction = Direction.valueOf(parts[0])
            val distance = parts[1].toInt()

            repeat(distance) {
                rope = rope.step(direction)
                tPositions.add(rope.knots.last())
            }
        }

        return tPositions.count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    val input = readInput("Day09")

    println(part1(testInput))
    println(part1(input))

    println(part2(testInput))
    println(part2(input))
}

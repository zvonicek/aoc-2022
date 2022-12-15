import java.lang.Integer.max
import java.lang.Integer.min

fun main() {
    data class Sensor(val pos: Point2, var beacon: Point2)

    fun positions(row: Int, sensor: Sensor): IntRange? {
        val xDist = kotlin.math.abs(sensor.pos.first - sensor.beacon.first)
        val yDist = kotlin.math.abs(sensor.pos.second - sensor.beacon.second)

        val rowDistance = (xDist + yDist - kotlin.math.abs(row - sensor.pos.second))
        var rowRange = sensor.pos.first - rowDistance .. sensor.pos.first + rowDistance

        if (rowDistance <= 0) {
            return null
        }

        if (rowRange.first == sensor.beacon.first) {
            rowRange = rowRange.first + 1 .. rowRange.last
        } else if (rowRange.last == sensor.beacon.first) {
            rowRange = rowRange.first .. rowRange.last - 1
        }

        return rowRange
    }

    fun part1(input: List<String>, row: Int): Int {
        val sensors = input.map { it ->
            val ints = it.split("=")
                    .map { it2 -> it2.filter { it.isDigit() || it == '-' } }
                    .filter { it2 -> it2.isNotEmpty() }
                    .map { it2 -> it2.toInt() }
            Sensor(ints[0] to ints[1], ints[2] to ints[3])
        }

        return sensors
                .mapNotNull { positions(row, it) }
                .fold(setOf<Int>()) { acc, intRange -> acc.union(intRange.toSet()) }
                .count()
    }

    fun part2(input: List<String>, limit: Int): Long {
        // rows occupied by either sensor or beacon
        val occupiedRows = hashMapOf<Int, Set<Int>>()

        val sensors = input.map {
            val ints = it.split("=")
                    .map { it2 -> it2.filter { it.isDigit() || it == '-' } }
                    .filter { it2 -> it2.isNotEmpty() }
                    .map { it2 -> it2.toInt() }

            occupiedRows[ints[1]] = occupiedRows.getOrDefault(ints[1], mutableSetOf()).union(setOf(ints[0]))
            occupiedRows[ints[3]] = occupiedRows.getOrDefault(ints[1], mutableSetOf()).union(setOf(ints[2]))
            Sensor(ints[0] to ints[1], ints[2] to ints[3])
        }

        for (i in 0..limit) {
            sensors.mapNotNull { positions(i, it) }
                    .sortedBy { it.first }
                    .fold(IntRange.EMPTY) { acc, intRange ->
                        if (acc.isEmpty() || acc.last < 0) {
                            return@fold intRange
                        } else if (intRange.first > limit) {
                            return@fold acc
                        } else if (intRange.first <= acc.last) {
                            return@fold IntRange(min(acc.first, intRange.first), max(acc.last, intRange.last))
                        } else {
                            // check that the gaps in the ranges can be filled with sensors or beacons
                            for (j in acc.last + 1 until intRange.first) {
                                if (occupiedRows[i]?.contains(j) != true) {
                                    return j.toLong() * 4000000 + i
                                }
                            }
                            return@fold IntRange(min(acc.first, intRange.first), max(acc.last, intRange.last))
                        }
                    }
        }
        return -1
    }

    val testInput = readInput("Day15_test")
    val input = readInput("Day15")

    println(part1(testInput, 10))
    println(part1(input, 2000000))

    println(part2(testInput, 20))
    println(part2(input, 4000000))
}

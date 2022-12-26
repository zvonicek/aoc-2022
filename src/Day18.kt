import kotlin.math.abs
import kotlin.math.min
import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        val cubes = input.map { it.split(",").map { it.toInt() } }
        return cubes.sumOf { first ->
            var angles = 6
            cubes.forEach { second ->
                if (abs(first[0] - second[0]) + abs (first[1] - second[1]) + abs(first[2] - second[2]) == 1) {
                    angles -= 1
                }
            }
            angles
        }
    }

    fun neighbours(triple: Triple<Int, Int, Int>): Set<Triple<Int, Int, Int>> {
        return setOf(Triple(triple.first - 1, triple.second, triple.third), Triple(triple.first + 1, triple.second, triple.third),
                Triple(triple.first, triple.second - 1, triple.third), Triple(triple.first, triple.second + 1, triple.third),
                Triple(triple.first, triple.second, triple.third - 1), Triple(triple.first, triple.second, triple.third + 1))
    }

    fun part2(input: List<String>): Int {
        val cubes = input.map {
            val nums = it.split(",").map { it.toInt() }
            Triple(nums[0], nums[1], nums[2])
        }.toSet()

        val innerCandidatesCounts = mutableMapOf<Triple<Int, Int, Int>, Int>()
        val projectionsMin = mutableMapOf<Triple<Int, Int, Int>, Int>()
        val projectionsMax = mutableMapOf<Triple<Int, Int, Int>, Int>()

        cubes.forEach {
            for (nb in neighbours(it).subtract(cubes)) {
                innerCandidatesCounts[nb] = innerCandidatesCounts.getOrDefault(nb, 0) + 1
            }

            projectionsMin[Triple(it.first, it.second, 0)] = min(it.third, projectionsMin.getOrDefault(Triple(it.first, it.second, 0), Int.MAX_VALUE))
            projectionsMin[Triple(it.first, 0, it.third)] = min(it.second, projectionsMin.getOrDefault(Triple(it.first, 0, it.third), Int.MAX_VALUE))
            projectionsMin[Triple(0, it.second, it.third)] = min(it.first, projectionsMin.getOrDefault(Triple(0, it.second, it.third), Int.MAX_VALUE))

            projectionsMax[Triple(it.first, it.second, 0)] = max(it.third, projectionsMax.getOrDefault(Triple(it.first, it.second, 0), 0))
            projectionsMax[Triple(it.first, 0, it.third)] = max(it.second, projectionsMax.getOrDefault(Triple(it.first, 0, it.third), 0))
            projectionsMax[Triple(0, it.second, it.third)] = max(it.first, projectionsMax.getOrDefault(Triple(0, it.second, it.third), 0))
        }

        var flooded = mutableSetOf<Triple<Int, Int, Int>>()
        innerCandidatesCounts.forEach { (key, value) ->
            val hasNoBound = projectionsMin[Triple(key.first, key.second, 0)] == null ||
                    projectionsMin[Triple(key.first, 0, key.third)] == null ||
                    projectionsMin[Triple(0, key.second, key.third)] == null ||
                    projectionsMax[Triple(key.first, key.second, 0)] == null ||
                    projectionsMax[Triple(key.first, 0, key.third)] == null ||
                    projectionsMax[Triple(0, key.second, key.third)] == null
            if (hasNoBound) {
                flooded.addAll(neighbours(key).subtract(cubes))
            }
        }

        repeat(20) {
            val newFlooded = flooded.toMutableSet()
            flooded.forEach {
                newFlooded.addAll(neighbours(it).subtract(cubes))
            }
            flooded = newFlooded
        }

        return innerCandidatesCounts.map { (key, value) ->
            if (!flooded.contains(key)) {
                0
            } else {
                value
            }
        }.sum()
    }

    val testInput = readInput("Day18_test")
    val input = readInput("Day18")

    println(part1(testInput))
    println(part1(input))

    println(part2(testInput))
    println(part2(input))
}

import java.util.*

fun main() {
    fun dijkstra(input: List<List<Int>>, start: Pair<Int, Int>, target: Pair<Int, Int>): Int {
        val dist = mutableMapOf<Pair<Int, Int>, Int>()
        val prev = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
        val pQ = PriorityQueue<Pair<Int, Int>>() { a, b -> dist[a]!! - dist[b]!! }

        for (i in input.indices) {
            for (j in input[i].indices) {
                dist[Pair(i, j)] = if (i == start.first && j == start.second) 0 else Int.MAX_VALUE
                pQ.offer(Pair(i, j))
            }
        }

        while (pQ.isNotEmpty()) {
            val u = pQ.poll()
            if (u == target) {  break }

            val nextPaths = listOf(Pair(u.first+1, u.second), Pair(u.first, u.second+1), Pair(u.first-1, u.second), Pair(u.first, u.second-1))
                    .filter { it.first >= 0 && it.second >= 0 && it.first <= input.indices.last && it.second <= input[0].indices.last }
                    .filter { input[u.first][u.second] >= input[it.first][it.second] - 1 }

            for (v in nextPaths) {
                val alt = dist[u]!! + 1
                if (alt < dist[v]!!) {
                    dist[v] = alt
                    prev[v] = u
                    // recompute value `v` in prio queue
                    pQ.remove(v)
                    pQ.offer(v)
                }
            }
        }

        return dist[target]!!
    }

    fun part1(input: List<String>): Int {
        val start = input.withIndex()
                .flatMap { row -> row.value.withIndex().map { if (it.value == 'S') Pair(row.index, it.index) else null } }
                .firstNotNullOf { it }
        val stop = input.withIndex()
                .flatMap { row -> row.value.withIndex().map { if (it.value == 'E') Pair(row.index, it.index) else null } }
                .firstNotNullOf { it }
        val input2 = input.map { row -> row.map {
            val digit = if (it == 'S') 'a' else if (it == 'E') 'z' else it
            digit.code
        } }

        return dijkstra(input2, start, stop)
    }

    fun part2(input: List<String>): Int {
        val input2 = input.map { row -> row.map {
            val digit = if (it == 'S') 'a' else if (it == 'E') 'z' else it
            digit.code
        } }
        val start = input2.withIndex()
                .flatMap { row -> row.value.withIndex().map { if (it.value == 'a'.code) Pair(row.index, it.index) else null } }
                .filterNotNull()
        val stop = input.withIndex()
                .flatMap { row -> row.value.withIndex().map { if (it.value == 'E') Pair(row.index, it.index) else null } }
                .firstNotNullOf { it }

        return start.minOf {
            dijkstra(input2, it, stop)
        }
    }

    val testInput = readInput("Day12_test")
    val input = readInput("Day12")

    println(part1(testInput))
    println(part1(input))

    println(part2(testInput))
    println(part2(input))
}

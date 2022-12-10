import kotlin.math.abs

class CPU(var X: Int = 1, var cycle: Int = 0) {
    var strengthCounts: Int = 0

    private fun operation(newX: Int? = null) {
        cycle += 1

        if (cycle == 20 || (cycle - 20).mod(40) == 0 ) {
            strengthCounts += cycle * X
        }
        draw()

        if (newX != null) {
            X = newX
        }
    }

    fun draw() {
        val pixel = (cycle - 1).mod(40)
        if (abs(pixel - X) < 2) {
            print("#")
        } else {
            print(".")
        }
        if (pixel == 39) {
            println()
        }
    }

    fun run(instr: String) {
        val parts = instr.split(" ")
        if (parts[0] == "noop") {
            operation()
        } else if (parts[0] == "addx") {
            operation()
            operation(X + parts[1].toInt())
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val cpu = CPU()

        input.forEach {
            cpu.run(it)
        }
        return cpu.strengthCounts
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    val input = readInput("Day10")

    println(part1(testInput))
    println(part1(input))

    //println(part2(testInput))
    //println(part2(input))
}

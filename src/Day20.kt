import kotlin.math.absoluteValue

fun main() {
    class Element(val num: Long, var prev: Element?, var next: Element?)
    
    fun nth(n: Int, element: Element): Long {
        var iter = element
        repeat(n) {
            iter = iter.next!!
        }
        return iter.num
    }

    fun mix(elements: List<Element>) {
        for (element in elements) {
            if (element.num.toInt() == 0) {
                continue
            }

            // remove the elem
            element.prev!!.next = element.next
            element.next!!.prev = element.prev

            var iterElem = element

            repeat(element.num.absoluteValue.mod(elements.count() - 1)) {
                iterElem = if (element.num > 0) {
                    iterElem.next!!
                } else {
                    iterElem.prev!!
                }
            }
            if (element.num > 0) {
                iterElem = iterElem.next!!
            }

            // insert the elem
            iterElem.prev!!.next = element
            element.prev = iterElem.prev
            iterElem.prev = element
            element.next = iterElem
        }
    }

    fun part1(input: List<String>): Long {
        val elements = input.map { Element(it.toLong(), null, null) }
        elements.windowed(2).forEach { (first, second) ->
            first.next = second
            second.prev = first
        }
        elements.first().prev = elements.last()
        elements.last().next = elements.first()

        val root = elements.first()

        mix(elements)

        var zero = root
        while(zero.num.toInt() != 0) {
            zero = zero.next!!
        }

        return nth(1000, zero) + nth(2000, zero) + nth(3000, zero)
    }

    fun part2(input: List<String>): Long {
        val elements = input.map { Element(it.toLong() * 811589153, null, null) }
        elements.windowed(2).forEach { (first, second) ->
            first.next = second
            second.prev = first
        }
        elements.first().prev = elements.last()
        elements.last().next = elements.first()

        val root = elements.first()

        repeat(10) {
            mix(elements)
        }

        var zero = root
        while(zero.num.toInt() != 0) {
            zero = zero.next!!
        }

        return nth(1000, zero) + nth(2000, zero) + nth(3000, zero)
    }

    val testInput = readInput("Day20_test")
    val input = readInput("Day20")

    println(part1(testInput))
    println(part1(input))

    println(part2(testInput))
    println(part2(input))
}
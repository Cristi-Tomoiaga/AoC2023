package day01

import println
import readInput

fun part1(input: List<String>): Int {
    return input.sumOf { line ->
        val firstDigit = line.first { it.isDigit() }.digitToInt()
        val lastDigit = line.last { it.isDigit() }.digitToInt()

        firstDigit * 10 + lastDigit
    }
}

fun transformToDigit(string: String): String {
    val digits = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

    var i = 0
    var newString = ""
    while (i < string.length) {
        var increment = 1
        var found = false

        for ((prefix, value) in digits) {
            if (string.substring(i).startsWith(prefix)) {
                newString += value.toString()
                increment = prefix.length - 1  // consider cases where the words share a letter
                found = true

                break
            }
        }

        if (!found) {
            newString += string[i]
        }

        i += increment
    }

    return newString
}

fun part2(input: List<String>): Int {
    return part1(
        input.map(::transformToDigit)
    )
}

fun main() {
    val day = "01"

    part1(readInput(day, "input.txt")).println()
    part2(readInput(day, "input.txt")).println()
}
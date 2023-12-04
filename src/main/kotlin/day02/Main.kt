package day02

import println
import readInput

data class Cubes(val red: Int, val green: Int, val blue: Int)
data class Game(val id: Int, val subsets: List<Cubes>)

fun String.toGame(): Game {
    val (gamePart, subsetsPart) = split(": ")
    val (_, id) = gamePart.split(" ")

    val subsets = mutableListOf<Cubes>()
    for (subset in subsetsPart.split("; ")) {
        var red = 0
        var green = 0
        var blue = 0

        for ((count, color) in subset.split(", ").map { it.split(" ") }) {
            when (color) {
                "red" -> red = count.toInt()
                "green" -> green = count.toInt()
                "blue" -> blue = count.toInt()
            }
        }

        subsets.add(Cubes(red, green, blue))
    }

    return Game(id.toInt(), subsets)
}

fun part1(input: List<String>): Int {
    val maxRed = 12
    val maxGreen = 13
    val maxBlue = 14

    val games = input.map(String::toGame)

    return games
        .filter {
            it.subsets.all { cubes ->
                cubes.red <= maxRed && cubes.green <= maxGreen && cubes.blue <= maxBlue
            }
        }
        .sumOf {
            it.id
        }
}

fun part2(input: List<String>): Int {
    val games = input.map(String::toGame)

    return games
        .map {
            Cubes(
                red = it.subsets.maxOf { cubes -> cubes.red },
                green = it.subsets.maxOf { cubes -> cubes.green },
                blue = it.subsets.maxOf { cubes -> cubes.blue }
            )
        }
        .sumOf {
            it.red * it.green * it.blue
        }
}

fun main() {
    val day = "02"

    part1(readInput(day, "input.txt")).println()
    part2(readInput(day, "input.txt")).println()
}
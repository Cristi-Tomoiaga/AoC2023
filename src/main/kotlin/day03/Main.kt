package day03

import println
import readInput

data class SchemaNumber(
    val num: Long,
    val positions: Pair<Int, IntRange>,
)

val directions = arrayOf(
    -1 to  0,
    -1 to  1,
    0 to  1,
    1 to  1,
    1 to  0,
    1 to -1,
    0 to -1,
    -1 to -1,
)

fun getNeighbours(matrix: List<String>, pos: Pair<Int, Int>): Set<Char> {
    return directions
        .map { (dx, dy) -> (pos.first + dx) to (pos.second + dy) }
        .filter { (x, y) -> x >= 0 && y >= 0 && x < matrix.size && y < matrix[0].length }
        .map { (x, y) -> matrix[x][y] }
        .filter { !it.isDigit() }
        .toSet()
}

fun isPartNumber(schemaNumber: SchemaNumber, matrix: List<String>): Boolean {
    return schemaNumber.positions.second
        .map { schemaNumber.positions.first to it }
        .flatMap { getNeighbours(matrix, it) }
        .toSet()
        .any { it != '.' }
}

fun getPartNumbers(matrix: List<String>): List<SchemaNumber> {
    return matrix
        .flatMapIndexed { x, line ->
            Regex("\\d+").findAll(line).map {
                SchemaNumber(
                    it.value.toLong(),
                    x to it.range
                )
            }
        }
        .filter { isPartNumber(it, matrix) }
}

fun getGearRatio(matrix: List<String>, partNumbers: List<SchemaNumber>, pos: Pair<Int, Int>): Long {
    val neighbours = directions
        .map { (dx, dy) -> (pos.first + dx) to (pos.second + dy) }
        .filter { (x, y) -> x >= 0 && y >= 0 && x < matrix.size && y < matrix[0].length }

    val nums = partNumbers
        .filter {
            neighbours.any {(x, y) ->
                it.positions.first == x && (y in it.positions.second)
            }
        }
        .map { it.num }

    return if (nums.size == 2) nums[0] * nums[1] else 0
}

fun part1(input: List<String>): Long {
    return getPartNumbers(input)
        .sumOf { it.num }
}

fun part2(input: List<String>): Long {
    val partNumbers = getPartNumbers(input)

    return input
        .flatMapIndexed { x, line ->
            Regex("\\*").findAll(line).map {
                x to it.range.first
            }
        }
        .sumOf { getGearRatio(input, partNumbers, it) }
}

fun main() {
    val day = "03"

    part1(readInput(day, "input.txt")).println()
    part2(readInput(day, "input.txt")).println()
}
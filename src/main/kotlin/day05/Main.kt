package day05

import println
import readInput

data class Map(
    val sourceName: String,
    val destName: String,
    val mappings: List<Triple<Long, Long, Long>>
) {
    fun map(source: Long): Long {
        for (mapping in mappings) {
            if (mapping.second <= source && source < mapping.second + mapping.third) {
                return source - mapping.second + mapping.first
            }
        }

        return source
    }
}

fun parseSeeds(input: List<String>): List<Long> =
    input[0].substringAfter("seeds: ")
        .split(" ")
        .map(String::toLong)

fun parseSeedRanges(input: List<String>): List<Pair<Long, Long>> =
    parseSeeds(input)
        .chunked(2)
        .map { Pair(it[0], it[1]) }

fun parseMappings(input: List<String>): List<Map> =
    input.subList(2, input.size)
        .joinToString("\n")
        .split("\n\n")
        .map {
            val lines = it.split("\n")

            val (sourceName, destName) = lines[0].substringBefore(" map:").split("-to-")
            val mappings = lines.subList(1, lines.size)
                .map { mapping ->
                    val (first, second, third) = mapping.split(" ").map(String::toLong)

                    Triple(first, second, third)
                }

            Map(sourceName, destName, mappings)
        }

fun part1(input: List<String>): Long {
    val seeds = parseSeeds(input)
    val mappings = parseMappings(input)

    return seeds.minOf { seed ->
        mappings.fold(seed) { value, mapping -> mapping.map(value) }
    }
}

fun part2(input: List<String>): Long {
    val seedRanges = parseSeedRanges(input)
    val mappings = parseMappings(input)

    return seedRanges.minOf { seedRange ->
        (seedRange.first ..< seedRange.first + seedRange.second)
            .minOf { seed ->
                mappings.fold(seed) { value, mapping -> mapping.map(value) }
            }
    }
}

fun main() {
    val day = "05"

    part1(readInput(day, "input.txt")).println()
    part2(readInput(day, "input.txt")).println()
}

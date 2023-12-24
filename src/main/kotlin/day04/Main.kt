package day04

import println
import readInput
import kotlin.math.pow

data class Card(
    val id: Int,
    val winningNums: List<Int>,
    val guessedNums: List<Int>,
    val wins: Int = guessedNums
        .filter { winningNums.contains(it) }
        .size
)

fun String.toCard(): Card {
    val cardId = this.substringBefore(":").substringAfter("Card ").trim().toInt()
    val (winning, guessed) = this.substringAfter(": ").split(" | ")

    return Card(
        id = cardId,
        winningNums = winning.split(" ")
            .filter { it.trim().isNotEmpty() }
            .map { it.trim().toInt() },
        guessedNums = guessed.split(" ")
            .filter { it.trim().isNotEmpty() }
            .map { it.trim().toInt() },
    )
}

fun part1(input: List<String>): Int {
    val cards = input.map(String::toCard)

    return cards
        .sumOf { card ->
            2f.pow(card.wins - 1).toInt()
        }
}

fun part2(input: List<String>): Int {
    val cards = input
        .map { it.toCard() to 1 }
        .associateBy { it.first.id }
        .toMutableMap()

    cards.forEach {
        val cardId = it.key
        val card = it.value.first
        val num = it.value.second

        for (nextId in 1..card.wins) {
            val newId = cardId + nextId
            val oldNum = cards[newId]?.second!!

            cards[newId] = cards[newId]?.copy(second = oldNum + num)!!
        }
    }

    return cards.values.sumOf { it.second }
}

fun main() {
    val day = "04"

    part1(readInput(day, "input.txt")).println()
    part2(readInput(day, "input.txt")).println()
}
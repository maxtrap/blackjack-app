package com.example.blackjack

import com.example.blackjack.model.Hand
import com.example.blackjack.model.PlayingCard
import com.example.blackjack.model.Rank
import com.example.blackjack.model.Rank.*
import com.example.blackjack.model.Suit
import com.example.blackjack.model.Suit.*
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun cardsOrderedCorrectly() {
        assertTrue(RANK_3 > RANK_2)
    }

    @Test
    fun handSumsCorrectly() {
        assertEquals(21, Hand(mutableListOf(PlayingCard(SPADES, RANK_10), PlayingCard(SPADES, RANK_A))).sum)
        assertEquals(21, Hand(mutableListOf(PlayingCard(SPADES, RANK_K), PlayingCard(SPADES, RANK_A))).sum)
        assertEquals(19, Hand(mutableListOf(PlayingCard(SPADES, RANK_K), PlayingCard(SPADES, RANK_A), PlayingCard(SPADES, RANK_8))).sum)
        assertEquals(13, Hand(mutableListOf(PlayingCard(SPADES, RANK_K), PlayingCard(SPADES, RANK_A), PlayingCard(SPADES, RANK_A), PlayingCard(SPADES, RANK_A))).sum)
        assertEquals(12, Hand(mutableListOf(PlayingCard(SPADES, RANK_K), PlayingCard(SPADES, RANK_A), PlayingCard(SPADES, RANK_A))).sum)
        assertEquals(22, Hand(mutableListOf(PlayingCard(SPADES, RANK_K), PlayingCard(SPADES, RANK_10), PlayingCard(SPADES, RANK_A), PlayingCard(SPADES, RANK_A))).sum)
    }
}
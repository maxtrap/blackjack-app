package com.example.blackjack

import com.example.blackjack.model.BlackjackGame
import com.example.blackjack.model.HandExpiredException
import com.example.blackjack.model.PlayableHand
import com.example.blackjack.model.PlayingCard
import com.example.blackjack.model.Rank
import com.example.blackjack.model.Rank.*
import com.example.blackjack.model.ShuffledDeck
import com.example.blackjack.model.Suit
import com.example.blackjack.model.Suit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class PlayingHandTests {

    @Test
    fun testSum() {
        var hand = PlayableHand(ShuffledDeck(), PlayingCard(SPADES, RANK_K), PlayingCard(SPADES, RANK_8), PlayingCard(SPADES, RANK_A), PlayingCard(SPADES, RANK_A))
        assertEquals(20, hand.hand.sum)

        hand = PlayableHand(ShuffledDeck(), PlayingCard(SPADES, RANK_K), PlayingCard(SPADES, RANK_A), PlayingCard(SPADES, RANK_A))
        assertEquals(12, hand.hand.sum)

        hand = PlayableHand(ShuffledDeck(), PlayingCard(SPADES, RANK_7), PlayingCard(SPADES, RANK_A), PlayingCard(SPADES, RANK_A))
        assertEquals(19, hand.hand.sum)

    }

    private fun PlayingCard(suit: Suit, rank: Rank): PlayingCard {
        return PlayingCard(suit, rank, R.drawable.hearts_ace)
    }


    @Test
    fun testHit() {
        val game = BlackjackGame()
        val playerHand = game.getPlayerHand()!!


        assertEquals(2, playerHand.hand.cards.size)


        val anotherHand = PlayableHand(ShuffledDeck(), PlayingCard(SPADES, RANK_J), PlayingCard(SPADES, RANK_A))
        assertEquals(21, anotherHand.hand.sum)

        assertThrows(HandExpiredException::class.java) {
            anotherHand.hit()
        }

        val handNumba3 = PlayableHand(ShuffledDeck(),  PlayingCard(SPADES, RANK_9), PlayingCard(SPADES, RANK_A))
        val handVal = handNumba3.hand.sum
        handNumba3.hit()
        assertTrue(handVal >= handNumba3.hand.sum)
    }






}
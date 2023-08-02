package com.example.blackjack.model

import com.example.blackjack.model.Rank.*
import com.example.blackjack.model.Suit.*

class ShuffledDeck() {
    private var cards = freshDeck()

    fun draw(): PlayingCard {
        if (cards.isEmpty())
            cards = freshDeck()
        return cards.removeLast()
    }


    private fun freshDeck() = ArrayDeque(listOf(
        PlayingCard(ClUBS, RANK_A),
        PlayingCard(ClUBS, RANK_2),
        PlayingCard(ClUBS, RANK_3),
        PlayingCard(ClUBS, RANK_4),
        PlayingCard(ClUBS, RANK_5),
        PlayingCard(ClUBS, RANK_6),
        PlayingCard(ClUBS, RANK_7),
        PlayingCard(ClUBS, RANK_8),
        PlayingCard(ClUBS, RANK_9),
        PlayingCard(ClUBS, RANK_10),
        PlayingCard(ClUBS, RANK_J),
        PlayingCard(ClUBS, RANK_Q),
        PlayingCard(ClUBS, RANK_K),

        PlayingCard(DIAMONDS, RANK_A),
        PlayingCard(DIAMONDS, RANK_2),
        PlayingCard(DIAMONDS, RANK_3),
        PlayingCard(DIAMONDS, RANK_4),
        PlayingCard(DIAMONDS, RANK_5),
        PlayingCard(DIAMONDS, RANK_6),
        PlayingCard(DIAMONDS, RANK_7),
        PlayingCard(DIAMONDS, RANK_8),
        PlayingCard(DIAMONDS, RANK_9),
        PlayingCard(DIAMONDS, RANK_10),
        PlayingCard(DIAMONDS, RANK_J),
        PlayingCard(DIAMONDS, RANK_Q),
        PlayingCard(DIAMONDS, RANK_K),

        PlayingCard(HEARTS, RANK_A),
        PlayingCard(HEARTS, RANK_2),
        PlayingCard(HEARTS, RANK_3),
        PlayingCard(HEARTS, RANK_4),
        PlayingCard(HEARTS, RANK_5),
        PlayingCard(HEARTS, RANK_6),
        PlayingCard(HEARTS, RANK_7),
        PlayingCard(HEARTS, RANK_8),
        PlayingCard(HEARTS, RANK_9),
        PlayingCard(HEARTS, RANK_10),
        PlayingCard(HEARTS, RANK_J),
        PlayingCard(HEARTS, RANK_Q),
        PlayingCard(HEARTS, RANK_K),

        PlayingCard(SPADES, RANK_A),
        PlayingCard(SPADES, RANK_2),
        PlayingCard(SPADES, RANK_3),
        PlayingCard(SPADES, RANK_4),
        PlayingCard(SPADES, RANK_5),
        PlayingCard(SPADES, RANK_6),
        PlayingCard(SPADES, RANK_7),
        PlayingCard(SPADES, RANK_8),
        PlayingCard(SPADES, RANK_9),
        PlayingCard(SPADES, RANK_10),
        PlayingCard(SPADES, RANK_J),
        PlayingCard(SPADES, RANK_Q),
        PlayingCard(SPADES, RANK_K),
    ).shuffled())

}
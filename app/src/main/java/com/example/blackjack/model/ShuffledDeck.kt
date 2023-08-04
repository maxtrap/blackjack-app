package com.example.blackjack.model

import com.example.blackjack.R
import com.example.blackjack.model.Rank.*
import com.example.blackjack.model.Suit.*

class ShuffledDeck {
    private var cards = freshDeck()

    fun draw(): PlayingCard {
        if (cards.isEmpty())
            cards = freshDeck()
        return cards.removeLast()
    }


    private fun freshDeck() = ArrayDeque(listOf(
        PlayingCard(CLUBS, RANK_A, R.drawable.clubs_ace),
        PlayingCard(CLUBS, RANK_2, R.drawable.clubs_2),
        PlayingCard(CLUBS, RANK_3, R.drawable.clubs_3),
        PlayingCard(CLUBS, RANK_4, R.drawable.clubs_4),
        PlayingCard(CLUBS, RANK_5, R.drawable.clubs_5),
        PlayingCard(CLUBS, RANK_6, R.drawable.clubs_6),
        PlayingCard(CLUBS, RANK_7, R.drawable.clubs_7),
        PlayingCard(CLUBS, RANK_8, R.drawable.clubs_8),
        PlayingCard(CLUBS, RANK_9, R.drawable.clubs_9),
        PlayingCard(CLUBS, RANK_10, R.drawable.clubs_10),
        PlayingCard(CLUBS, RANK_J, R.drawable.clubs_jack),
        PlayingCard(CLUBS, RANK_Q, R.drawable.clubs_queen),
        PlayingCard(CLUBS, RANK_K, R.drawable.clubs_king),

        PlayingCard(DIAMONDS, RANK_A, R.drawable.diamonds_ace),
        PlayingCard(DIAMONDS, RANK_2, R.drawable.diamonds_2),
        PlayingCard(DIAMONDS, RANK_3, R.drawable.diamonds_3),
        PlayingCard(DIAMONDS, RANK_4, R.drawable.diamonds_4),
        PlayingCard(DIAMONDS, RANK_5, R.drawable.diamonds_5),
        PlayingCard(DIAMONDS, RANK_6, R.drawable.diamonds_6),
        PlayingCard(DIAMONDS, RANK_7, R.drawable.diamonds_7),
        PlayingCard(DIAMONDS, RANK_8, R.drawable.diamonds_8),
        PlayingCard(DIAMONDS, RANK_9, R.drawable.diamonds_9),
        PlayingCard(DIAMONDS, RANK_10, R.drawable.diamonds_10),
        PlayingCard(DIAMONDS, RANK_J, R.drawable.diamonds_jack),
        PlayingCard(DIAMONDS, RANK_Q, R.drawable.diamonds_queen),
        PlayingCard(DIAMONDS, RANK_K, R.drawable.diamonds_king),

        PlayingCard(HEARTS, RANK_A, R.drawable.hearts_ace),
        PlayingCard(HEARTS, RANK_2, R.drawable.hearts_2),
        PlayingCard(HEARTS, RANK_3, R.drawable.hearts_3),
        PlayingCard(HEARTS, RANK_4, R.drawable.hearts_4),
        PlayingCard(HEARTS, RANK_5, R.drawable.hearts_5),
        PlayingCard(HEARTS, RANK_6, R.drawable.hearts_6),
        PlayingCard(HEARTS, RANK_7, R.drawable.hearts_7),
        PlayingCard(HEARTS, RANK_8, R.drawable.hearts_8),
        PlayingCard(HEARTS, RANK_9, R.drawable.hearts_9),
        PlayingCard(HEARTS, RANK_10, R.drawable.hearts_10),
        PlayingCard(HEARTS, RANK_J, R.drawable.hearts_jack),
        PlayingCard(HEARTS, RANK_Q, R.drawable.hearts_queen),
        PlayingCard(HEARTS, RANK_K, R.drawable.hearts_king),

        PlayingCard(SPADES, RANK_A, R.drawable.spades_ace),
        PlayingCard(SPADES, RANK_2, R.drawable.spades_2),
        PlayingCard(SPADES, RANK_3, R.drawable.spades_3),
        PlayingCard(SPADES, RANK_4, R.drawable.spades_4),
        PlayingCard(SPADES, RANK_5, R.drawable.spades_5),
        PlayingCard(SPADES, RANK_6, R.drawable.spades_6),
        PlayingCard(SPADES, RANK_7, R.drawable.spades_7),
        PlayingCard(SPADES, RANK_8, R.drawable.spades_8),
        PlayingCard(SPADES, RANK_9, R.drawable.spades_9),
        PlayingCard(SPADES, RANK_10, R.drawable.spades_10),
        PlayingCard(SPADES, RANK_J, R.drawable.spades_jack),
        PlayingCard(SPADES, RANK_Q, R.drawable.spades_queen),
        PlayingCard(SPADES, RANK_K, R.drawable.spades_king),
    ).shuffled())

}
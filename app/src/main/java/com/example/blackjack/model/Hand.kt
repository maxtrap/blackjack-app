package com.example.blackjack.model

import org.jetbrains.annotations.TestOnly

class Hand(firstCard: PlayingCard, secondCard: PlayingCard) {

    val cards: List<PlayingCard>
        get() = cardsImpl

    private val cardsImpl = mutableListOf(firstCard, secondCard)


    //Returns sum of playing hand. Aces are counted as 11 or 1 depending on the other cards
    val sum: Int
        get() {
            var sum = cards.sumOf { it.rank.rankValue }
            sum += cards.count { it.rank == Rank.RANK_A }
            if (sum <= 11)
                sum += 10
            return sum
        }


    fun hit(card: PlayingCard) {
        cardsImpl.add(card)
    }


    @TestOnly
    internal constructor(cards: MutableList<PlayingCard>) : this(cards[0], cards[1]) {
        cardsImpl.clear()
        cardsImpl.addAll(cards)
    }

}
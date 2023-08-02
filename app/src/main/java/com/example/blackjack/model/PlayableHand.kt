package com.example.blackjack.model

import org.jetbrains.annotations.TestOnly


interface Hand {
    val cards: List<PlayingCard>
    val sum: Int
}


class HandExpiredException : Exception("This hand is no longer playable")

class PlayableHand(private val deck: ShuffledDeck, private val game: PayoutObserver) {


    private var isHandExpired = false

    private class HandImpl(val cardsImpl: MutableList<PlayingCard>) : Hand {
        override val cards: List<PlayingCard>
            get() = cardsImpl

        //Returns sum of playing hand. Aces are counted as 11 or 1 depending on the other cards
        override val sum: Int
            get() {
                var sum = cards.sumOf { it.rank.rankValue }
                sum += cards.count { it.rank == Rank.RANK_A }
                if (sum <= 11)
                    sum += 10
                return sum
            }
    }



    private val hand = HandImpl(mutableListOf(deck.draw(), deck.draw()))






    fun hit() {
        if (isHandExpired)
            throwHandExpired()

        hand.cardsImpl.add(deck.draw())
        if (hand.sum >= 21) {
            payout()
        }
    }

    
    


    private fun payout() {
        game.payout(listOf(hand))
        isHandExpired = true
    }

    private fun throwHandExpired(): Nothing = throw HandExpiredException()

    @TestOnly
    internal constructor(cards: MutableList<PlayingCard>) : this(ShuffledDeck(), BlackjackGame()) {
        hand.cardsImpl.clear()
        hand.cardsImpl.addAll(cards)
    }

}

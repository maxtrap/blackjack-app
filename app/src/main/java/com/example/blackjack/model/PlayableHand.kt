package com.example.blackjack.model


//Hand is meant to represent a read-only version of PlayableHand
interface Hand {
    val cards: List<PlayingCard>
    val sum: Int
    val bet: Int
}


class HandExpiredException : Exception("This hand is no longer playable")




class PlayableHand(
    private val deck: ShuffledDeck,
    vararg startingCards: PlayingCard,
    private val game: PayoutObserver? = null,
    bet: Int = 0,
    val splitAllowed: Boolean = true
) {


    var isHandExpired = false
        private set

    private class HandImpl(val cardsImpl: MutableList<PlayingCard>, var betImpl: Int) : Hand {
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

        override val bet: Int
            get() = betImpl
    }


    private val handImpl = HandImpl(startingCards.toMutableList(), bet)

    val hand: Hand = handImpl


    init {
        if (hand.sum >= 21)
            payout()
    }

    constructor(deck: ShuffledDeck, game: PayoutObserver? = null, bet: Int = 0, splitAllowed: Boolean = false) : this(
        deck,
        deck.draw(),
        deck.draw(),
        game = game,
        bet = bet,
        splitAllowed = splitAllowed
    )


    fun hit() {
        if (isHandExpired)
            throwHandExpired()

        handImpl.cardsImpl.add(deck.draw())
        if (handImpl.sum >= 21) {
            payout()
        }
    }

    fun stand() {
        if (isHandExpired)
            throwHandExpired()

        payout()
    }

    fun doubleDown() {
        if (isHandExpired)
            throwHandExpired()

        handImpl.cardsImpl.add(deck.draw())
        handImpl.betImpl *= 2
        payout()
    }

    fun split(): SplitHand? {
        if (isHandExpired)
            throwHandExpired()

        if (!canSplit())
            return null

        isHandExpired = true
        return SplitHand(this, game)
    }

    fun canSplit() = splitAllowed && hand.cards.size == 2 && hand.cards[0].rank == hand.cards[1].rank


    //Creates a hand that has the same deck and bet, but with different cards and payout observer
    //Should only be used for splitting implementation
    internal fun sameHandDifferentCards(
        payoutObserver: PayoutObserver,
        vararg cards: PlayingCard
    ): PlayableHand = PlayableHand(
        deck,
        *cards,
        splitAllowed = false,
        game = payoutObserver,
        bet = hand.bet
    )


    private fun payout() {
        isHandExpired = true
        game?.onPayout(listOf(handImpl))
    }

}


class SplitHand(initialHand: PlayableHand, private val game: PayoutObserver?) : PayoutObserver {

    private var isHandExpired = false

    private val hand1: PlayableHand =
        initialHand.sameHandDifferentCards(payoutObserver = this, initialHand.hand.cards[0])
    private val hand2: PlayableHand =
        initialHand.sameHandDifferentCards(payoutObserver = this, initialHand.hand.cards[1])

    operator fun component1(): PlayableHand {
        if (isHandExpired)
            throwHandExpired()

        return hand1
    }

    operator fun component2(): PlayableHand {
        if (isHandExpired)
            throwHandExpired()

        return hand2
    }

    override fun onPayout(hands: List<Hand>) {
        if (hand1.isHandExpired && hand2.isHandExpired) {
            isHandExpired = true
            game?.onPayout(listOf(hand1.hand, hand2.hand))
        }
    }

}

private fun throwHandExpired(): Nothing = throw HandExpiredException()
package com.example.blackjack.model


//Hand is meant to represent a read-only version of PlayableHand
interface Hand {
    val cards: List<PlayingCard>
    val sum: Int
    val bet: Int
}




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
                if (cards.any { it.rank == Rank.RANK_A } && sum <= 11)
                    sum += 10
                return sum
            }

        override val bet: Int
            get() = betImpl

        override fun toString(): String {
            return "HandImpl(cardsImpl=$cardsImpl, betImpl=$betImpl)"
        }


    }


    private val handImpl = HandImpl(startingCards.toMutableList(), bet)

    val hand: Hand = handImpl


    init {
        if (hand.sum >= 21)
            payout()
    }

    constructor(deck: ShuffledDeck, game: PayoutObserver? = null, bet: Int = 0, splitAllowed: Boolean = true) : this(
        deck,
        deck.draw(),
        deck.draw(),
        game = game,
        bet = bet,
        splitAllowed = splitAllowed
    )


    fun hit() {
        if (isHandExpired)
            return

        handImpl.cardsImpl.add(deck.draw())
        if (handImpl.sum >= 21) {
            payout()
        }
    }

    fun stand() {
        if (isHandExpired)
            return

        payout()
    }

    fun doubleDown() {
        if (isHandExpired)
            return

        handImpl.cardsImpl.add(deck.draw())
        handImpl.betImpl *= 2
        payout()
    }

    fun split(): SplitHand? {
        if (isHandExpired)
            return null

        if (!canSplit())
            return null

        isHandExpired = true
        return SplitHand(this, deck, game)
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


class SplitHand(initialHand: PlayableHand, deck: ShuffledDeck, private val game: PayoutObserver?) : PayoutObserver {

    val handLeft: PlayableHand =
        initialHand.sameHandDifferentCards(payoutObserver = this, initialHand.hand.cards[0], deck.draw())
    val handRight: PlayableHand =
        initialHand.sameHandDifferentCards(payoutObserver = this, initialHand.hand.cards[1], deck.draw())

    init {
        onPayout(listOf())
    }

    override fun onPayout(hands: List<Hand>) {
        if (handLeft?.isHandExpired == true && handRight?.isHandExpired == true) {
            game?.onPayout(listOf(handLeft.hand, handRight.hand))
        }
    }

}

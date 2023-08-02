package com.example.blackjack.model

class DealerHand(deck: ShuffledDeck) {
    private val backingHand = PlayableHand(deck)

    fun hit(): Boolean {
        if (backingHand.hand.sum > 16)
            return false

        backingHand.hit()
        return true
    }

    val hand = backingHand.hand
}
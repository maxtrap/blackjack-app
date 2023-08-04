package com.example.blackjack.model

class DealerHand(deck: ShuffledDeck) {
    private val backingHand = PlayableHand(deck)

    fun hitDealer() {
        while (backingHand.hand.sum <= 16)
            backingHand.hit()
    }

    val hand = backingHand.hand

    val firstCardValue = PlayableHand(deck, hand.cards[0]).hand.sum
}
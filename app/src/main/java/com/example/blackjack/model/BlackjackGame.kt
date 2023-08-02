package com.example.blackjack.model

class BlackjackGame : PayoutObserver {

    private val deck = ShuffledDeck()
    private var handInPlay = false


    fun getPlayerHand(): PlayableHand? {
        if (handInPlay)
            return null

        handInPlay = true
        return PlayableHand(deck, this, 100)
    }
    override fun onPayout(hands: List<Hand>) {
        handInPlay = false
    }


}
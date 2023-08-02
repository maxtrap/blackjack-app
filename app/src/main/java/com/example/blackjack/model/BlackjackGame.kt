package com.example.blackjack.model

class BlackjackGame : PayoutObserver {

    private val deck = ShuffledDeck()
    private var dealerHand = createStartingHand()


    private fun createStartingHand() = PlayableHand(deck, this, 100)
    override fun onPayout(hands: List<Hand>) {
        TODO("Not yet implemented")
    }


}
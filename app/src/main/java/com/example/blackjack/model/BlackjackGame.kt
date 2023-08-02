package com.example.blackjack.model

class BlackjackGame : PayoutObserver {

    private val deck = ShuffledDeck()
    private var dealerHand = createStartingHand()


    private fun createStartingHand() = PlayableHand(deck, this)



}
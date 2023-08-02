package com.example.blackjack.model

class BlackjackGame {

    private val deck = ShuffledDeck()
    private var dealerHand = createStartingHand()


    private fun createStartingHand() = Hand(deck.draw(), deck.draw())





}
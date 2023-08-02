package com.example.blackjack.model

interface PayoutObserver {

    fun onPayout(hands: List<Hand>)

}
package com.example.blackjack.model

interface PayoutObserver {

    fun payout(hands: List<Hand>)

}
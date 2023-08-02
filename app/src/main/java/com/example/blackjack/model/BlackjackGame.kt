package com.example.blackjack.model

class BlackjackGame(var playerBalance: Int = 0) : PayoutObserver {

    private val deck = ShuffledDeck()
    private var handInPlay = false
    private var dealerHand = DealerHand(deck)



    fun getPlayerHand(): PlayableHand? {
        if (handInPlay)
            return null

        handInPlay = true
        return PlayableHand(deck, this, 100)
    }


    fun hitDealer() {
        while (dealerHand.hit()) {}
    }

    override fun onPayout(hands: List<Hand>) {
        hitDealer()
        hands.forEach {
            playerBalance += calculateWinnings(it)
        }
        handInPlay = false
    }

    private fun calculateWinnings(hand: Hand): Int {
        val handSum = hand.sum
        val dealerSum = dealerHand.hand.sum
        return when {
            handSum > 21 -> -hand.bet //Player busts
            hand.isBlackjack() && !dealerHand.hand.isBlackjack() -> (hand.bet * 1.5).toInt() //Player has blackjack and dealer doesn't
            !hand.isBlackjack() && dealerHand.hand.isBlackjack() -> -hand.bet //Dealer has blackjack and player doesn't
            dealerSum > 21 -> hand.bet //Dealer busts

            handSum < dealerSum -> -hand.bet //Dealer has a higher sum than player
            handSum > dealerSum -> hand.bet //Player has higher sum than dealer
            else -> 0 //Player and dealer have the same sum
        }
    }

    private fun Hand.isBlackjack() = cards.size == 2 && sum == 21


}
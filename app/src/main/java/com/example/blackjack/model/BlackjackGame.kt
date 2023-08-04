package com.example.blackjack.model

object BlackjackGame : PayoutObserver {

    var playerBalance: Int = 1000
        private set

    private val deck = ShuffledDeck()
    var isHandInPlay = true
        private set
    var dealerHand = DealerHand(deck)
        private set

    var playerHand: PlayableHand? = PlayableHand(deck, this, 100)
        private set

    var splitHand: SplitHand? = null
        private set


    fun resetHands() {
        if (isHandInPlay)
            return

        dealerHand = DealerHand(deck)
        splitHand = null
        isHandInPlay = true
        playerHand = PlayableHand(deck, this, 100)
    }


    override fun onPayout(hands: List<Hand>) {
        dealerHand.hitDealer()
        hands.forEach {
            playerBalance += calculateWinnings(it)
        }
        isHandInPlay = false
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
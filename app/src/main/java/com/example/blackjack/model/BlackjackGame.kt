package com.example.blackjack.model

import androidx.annotation.StringRes
import com.example.blackjack.R
import com.example.blackjack.model.HandWinState.*

enum class HandWinState(@StringRes val message: Int) {
    PLAYER_BUST(R.string.player_bust),
    PLAYER_BLACKJACK(R.string.player_blackjack),
    DEALER_BLACKJACK(R.string.dealer_blackjack),
    DEALER_BUST(R.string.dealer_bust),
    DEALER_HIGHER(R.string.dealer_higher),
    PLAYER_HIGHER(R.string.player_higher),
    CHOP(R.string.chop)
}

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

    fun getHandWinner(hand: Hand): HandWinState {
        val handSum = hand.sum
        val dealerSum = dealerHand.hand.sum
        return when {
            handSum > 21 -> PLAYER_BUST //Player busts
            hand.isBlackjack() && !dealerHand.hand.isBlackjack() -> PLAYER_BLACKJACK //Player has blackjack and dealer doesn't
            !hand.isBlackjack() && dealerHand.hand.isBlackjack() -> DEALER_BLACKJACK //Dealer has blackjack and player doesn't
            dealerSum > 21 -> DEALER_BUST //Dealer busts

            handSum < dealerSum -> DEALER_HIGHER //Dealer has a higher sum than player
            handSum > dealerSum -> PLAYER_HIGHER //Player has higher sum than dealer
            else -> CHOP //Player and dealer have the same sum
        }
    }

    private fun calculateWinnings(hand: Hand): Int {
        return when (getHandWinner(hand)) {
            PLAYER_BUST -> -hand.bet //Player busts
            PLAYER_BLACKJACK -> (hand.bet * 1.5).toInt() //Player has blackjack and dealer doesn't
            DEALER_BLACKJACK -> -hand.bet //Dealer has blackjack and player doesn't
            DEALER_BUST -> hand.bet //Dealer busts

            DEALER_HIGHER -> -hand.bet //Dealer has a higher sum than player
            PLAYER_HIGHER -> hand.bet //Player has higher sum than dealer
            CHOP -> 0 //Player and dealer have the same sum
        }
    }

    private fun Hand.isBlackjack() = cards.size == 2 && sum == 21
}

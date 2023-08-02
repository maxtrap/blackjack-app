package com.example.blackjack.model

data class PlayingCard(
    val suit: Suit,
    val rank: Rank
)

enum class Suit {
    ClUBS, DIAMONDS, HEARTS, SPADES
}

enum class Rank(val rankValue: Int) {
    RANK_2(2),
    RANK_3(3),
    RANK_4(4),
    RANK_5(5),
    RANK_6(6),
    RANK_7(7),
    RANK_8(8),
    RANK_9(9),
    RANK_10(10),
    RANK_J(10),
    RANK_Q(10),
    RANK_K(10),
    RANK_A(0)
    //Obviously Aces are not worth 0 but this seems like a decent solution.
    //The logic for calculating ace values is delegated to use cases in the program
}
package com.example.blackjack.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.blackjack.BlackjackApp
import com.example.blackjack.model.ShuffledDeck
import com.example.blackjack.ui.theme.BlackjackTheme

@Composable
fun MainBlackjackScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        DealerCards()
    }
}

@Composable
fun DealerCards() {

    val deck = ShuffledDeck()

    Row {
       Box {
           Image(
               painter = painterResource(deck.draw().imageRes),
               contentDescription = null
           )
       }
    }
}



@Preview(showSystemUi = true)
@Composable
fun BlackjackScreenPreview() {
    BlackjackTheme {
        MainBlackjackScreen()
    }
}
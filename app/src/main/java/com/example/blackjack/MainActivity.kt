package com.example.blackjack

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.blackjack.model.BlackjackGame
import com.example.blackjack.ui.theme.BlackjackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent {
            BlackjackTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BlackjackApp()
                }
            }
        }
    }
}


@Composable
fun BlackjackApp() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff045900))
    ) {

        MainBlackjackScreen()
    }
}

@Composable
fun TitleBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayLarge
        )
    }
}


@Composable
fun MainBlackjackScreen(modifier: Modifier = Modifier) {

    var onUpdate by remember { mutableStateOf(false) }

    fun updateUI() {
        onUpdate = !onUpdate
    }
    onUpdate

    Column(
        modifier = modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            RowOfCards(
                if (BlackjackGame.isHandInPlay)
                    listOf(
                        R.drawable.back_of_card,
                        BlackjackGame.dealerHand.hand.cards[0].imageRes
                    )
                else
                    BlackjackGame.dealerHand.hand.cards.map { it.imageRes },
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 24.dp)
                    .fillMaxWidth(),
            )
            DealerTotal(onUpdate)
        }

        Row {
            PlayerHandView(onUpdate, { updateUI() })
        }
    }
}


@Composable
fun PlayingCardImage(@DrawableRes imageRes: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .height(180.dp)
            .aspectRatio(0.7f),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()

        )
    }
}


@Composable
fun DealerTotal(onUpdate: Boolean, modifier: Modifier = Modifier) {
    onUpdate
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(
                R.string.dealer_total_text,
                if (BlackjackGame.isHandInPlay) BlackjackGame.dealerHand.firstCardValue else BlackjackGame.dealerHand.hand.sum
            ),
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun BlackjackAppPreview() {
    BlackjackTheme {
        BlackjackApp()
    }
}
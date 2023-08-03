package com.example.blackjack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.blackjack.model.BlackjackGame
import com.example.blackjack.model.ShuffledDeck
import com.example.blackjack.ui.theme.BlackjackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff045900))
    ) {
        TitleBar()
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
    Column(
        modifier = modifier
            .padding(bottom = 16.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            RowOfCards(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                R.drawable.resource_abstract,
                ShuffledDeck().draw().imageRes
            )
            DealerTotal()
        }

        Row(modifier.fillMaxWidth(0.5f)) {
            PlayerHandView()
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


private const val DEGREE_SPACING = 5

private data class CardPosition(val xOffset: Int, val yOffset: Int)

@Composable
fun RowOfCards(modifier: Modifier = Modifier, @DrawableRes vararg cardRes: Int) {

    val firstRotation = -(DEGREE_SPACING * cardRes.size / 2)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        cardRes.forEachIndexed { index, cardRes ->
            val cardRotation = firstRotation + (DEGREE_SPACING * index)
            PlayingCardImage(
                cardRes,
                Modifier
                    .rotate(cardRotation.toFloat())
            )
        }

    }
}


@Composable
fun DealerTotal(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.dealer_total_text, 0),
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
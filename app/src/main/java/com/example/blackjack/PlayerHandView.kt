package com.example.blackjack

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blackjack.Split.NO_SPLIT
import com.example.blackjack.Split.SPLIT_LEFT
import com.example.blackjack.Split.SPLIT_RIGHT
import com.example.blackjack.model.BlackjackGame
import com.example.blackjack.model.BlackjackGame.isBlackjack
import com.example.blackjack.ui.theme.BlackjackTheme
import kotlinx.coroutines.delay


enum class Split {
    NO_SPLIT, SPLIT_LEFT, SPLIT_RIGHT
}


@Composable
fun PlayerHandView(
    onUpdate: Boolean,
    updateUI: () -> Unit,
    modifier: Modifier = Modifier,
    split: Split = NO_SPLIT
) {
    onUpdate

    var showPopupMessage by remember { mutableStateOf(!BlackjackGame.isHandInPlay) }
    var isBlackjack by remember { mutableStateOf(BlackjackGame.playerHand?.hand?.sum == 21) }


    val thisHand = when (split) {
        NO_SPLIT -> BlackjackGame.playerHand!!
        SPLIT_LEFT -> BlackjackGame.splitHand!!.handLeft
        SPLIT_RIGHT -> BlackjackGame.splitHand!!.handRight
    }

    if (thisHand.hand.isBlackjack()) {
        showPopupMessage = true
        if (split == NO_SPLIT || (BlackjackGame.splitHand!!.handLeft.hand.isBlackjack() && BlackjackGame.splitHand!!.handRight.hand.isBlackjack()))
            isBlackjack = true
    }

    Column(modifier = modifier) {
        if (showPopupMessage) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalAlignment = CenterHorizontally
            ) {
                Card(
                    colors = CardDefaults.cardColors(Color(0xFF09750B)),
                    elevation = CardDefaults.elevatedCardElevation(8.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(16.dp),
                ) {
                    Column(
                        horizontalAlignment = CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(IntrinsicSize.Max)
                    ) {
                        val message =
                            if (split == NO_SPLIT || (BlackjackGame.splitHand!!.handLeft.isHandExpired && BlackjackGame.splitHand!!.handRight.isHandExpired)) {
                                BlackjackGame.getHandWinner(thisHand.hand).message
                            } else {
                                R.string.pending
                            }
                        Text(
                            text = stringResource(message),
                            style = MaterialTheme.typography.displaySmall,
                            textAlign = TextAlign.Center
                        )
                        if (isBlackjack) {
                            Spacer(modifier = Modifier.height(8.dp))
                            BlackjackActionButton(
                                onClick = {
                                    BlackjackGame.resetHands()
                                    isBlackjack = false
                                    showPopupMessage = false
                                    updateUI()
                                },
                                textRes = R.string.continue_label,
                                modifier
                                    .width(120.dp)
                                    .height(40.dp)
                            )
                        } else if (split == NO_SPLIT || (BlackjackGame.splitHand!!.handLeft.isHandExpired && BlackjackGame.splitHand!!.handRight.isHandExpired)) {
                            LaunchedEffect(Unit) {
                                delay(3000)
                                BlackjackGame.resetHands()
                                if (BlackjackGame.isHandInPlay)
                                    showPopupMessage = false
                                else
                                    isBlackjack = true
                                updateUI()
                            }
                        }
                    }
                }
            }

        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    BlackjackActionButton(
                        onClick = {
                            thisHand.hit()
                            if (thisHand.isHandExpired) {
                                showPopupMessage = true
                            }
                            updateUI()
                        },
                        R.string.hit,
                        modifier = Modifier.weight(1f)
                    )
                    BlackjackActionButton(
                        onClick = {
                            thisHand.stand()
                            showPopupMessage = true
                            updateUI()
                        },
                        R.string.stand,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    BlackjackActionButton(
                        onClick = {
                            thisHand.doubleDown()
                            showPopupMessage = true
                            updateUI()
                        },
                        R.string.double_down,
                        modifier = Modifier.weight(1f),
                        enabled = thisHand.hand.cards.size <= 2
                    )
                    BlackjackActionButton(
                        onClick = {
                            BlackjackGame.splitHand()
                            updateUI()
                        },
                        R.string.split,
                        modifier = Modifier.weight(1f),
                        enabled = split == NO_SPLIT && thisHand.canSplit()
                    )
                }
            }
        }

        RowOfCards(
            thisHand.hand.cards.map { it.imageRes },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )


        Text(
            text = stringResource(R.string.your_total, thisHand.hand.sum),
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(vertical = 8.dp)
        )
    }

}

@Composable
fun BlackjackActionButton(
    onClick: () -> Unit,
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xff00a800),
            disabledContainerColor = Color(0xFF006D00)
        ),
        shape = MaterialTheme.shapes.medium,
        elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
        modifier = modifier,
        contentPadding = PaddingValues(4.dp),
        enabled = enabled
    ) {
        Text(
            text = stringResource(textRes),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview(showBackground = true, backgroundColor = 0xff045900, showSystemUi = true)
@Composable
fun PlayerHandPreview() {
    BlackjackTheme {
        Box {
            Box(Modifier.fillMaxWidth(0.5f)) {
                PlayerHandView(false, { })
            }
        }
    }
}
package com.example.blackjack

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blackjack.model.ShuffledDeck
import com.example.blackjack.ui.theme.BlackjackTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerHandView(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
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
                    onClick = { /*TODO*/ },
                    R.string.hit,
                    modifier = Modifier.weight(1f)
                )
                BlackjackActionButton(
                    onClick = { /*TODO*/ },
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
                    onClick = { /*TODO*/ },
                    R.string.double_down,
                    modifier = Modifier.weight(1f)
                )
                BlackjackActionButton(
                    onClick = { /*TODO*/ },
                    R.string.split,
                    modifier = Modifier.weight(1f)
                )
            }
        }


        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Center
        ) {
            PlayingCardImage(
                ShuffledDeck().draw().imageRes,
                modifier
                    .padding(horizontal = 8.dp)
            )
            PlayingCardImage(
                ShuffledDeck().draw().imageRes,
                modifier
                    .padding(horizontal = 8.dp)
            )
        }


        Text(
            text = stringResource(R.string.your_total, 0),
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(top = 16.dp)
        )
    }


}

@Composable
fun BlackjackActionButton(
    onClick: () -> Unit,
    @StringRes textRes: Int,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Color(0xff00a800)),
        shape = MaterialTheme.shapes.medium,
        elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
        modifier = modifier,
        contentPadding = PaddingValues(4.dp)
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
                PlayerHandView()
            }
        }
    }
}
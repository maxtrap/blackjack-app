package com.example.blackjack

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.tan


private const val DEGREE_SPACING = 8.0
private const val CARD_OFFSET_RADIUS = 130
private data class CardPosition(val xOffset: Int, val yOffset: Int)

@Composable
fun RowOfCards(modifier: Modifier = Modifier, cardImages: List<Int>) {

    val firstRotation = -(DEGREE_SPACING * (cardImages.size - 1) / 2)
    val centerOffset = findOffsetFromRotation(firstRotation).yOffset / 2

    Box(
        modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        cardImages.forEachIndexed { index, cardRes ->
            val cardRotation = firstRotation + (DEGREE_SPACING * index)
            val (xOffset, yOffset) = findOffsetFromRotation(cardRotation)

            PlayingCardImage(
                cardRes,
                Modifier
                    .rotate(cardRotation.toFloat())
                    .absoluteOffset(x = xOffset.dp, (yOffset - centerOffset).dp)
            )

        }
    }
}

private fun findOffsetFromRotation(rotation: Double): CardPosition {
    val xOffset = CARD_OFFSET_RADIUS * sin(rotation.toRadians())
    val yOffset = xOffset / tan(PI/2 - (rotation.toRadians() / 2) )
    return CardPosition(xOffset.roundToInt(), yOffset.roundToInt())
}

private fun Double.toRadians() = this * PI / 180

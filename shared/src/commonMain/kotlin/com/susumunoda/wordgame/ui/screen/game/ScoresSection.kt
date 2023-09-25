package com.susumunoda.wordgame.ui.screen.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.susumunoda.wordgame.data.PlayerData
import com.susumunoda.wordgame.ui.component.VerticalDivider

private val SCORE_BOARD_HEIGHT = 60.dp

@Composable
fun ScoresSection(
    playerOneData: PlayerData,
    playerTwoData: PlayerData,
    currentTurnPlayer: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.height(SCORE_BOARD_HEIGHT).fillMaxWidth()
    ) {
        PlayerScore(
            name = playerOneData.name,
            score = playerOneData.score,
            isCurrentTurnPlayer = playerOneData.name == currentTurnPlayer,
            modifier = Modifier.weight(1f)
        )
        VerticalDivider(Modifier.fillMaxHeight())
        PlayerScore(
            name = playerTwoData.name,
            score = playerTwoData.score,
            isCurrentTurnPlayer = playerTwoData.name == currentTurnPlayer,
            modifier = Modifier.weight(1f)
        )
    }
}

private val SCORE_PADDING = 4.dp
private val SCORE_FONT_SIZE = 24.sp

@Composable
private fun PlayerScore(
    name: String,
    score: Int,
    isCurrentTurnPlayer: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            // Originally, the border was implemented with a then() that conditionally returned
            // Modifier.border(). However, doing it the current way is ideal as we get both padding
            // and border behavior by changing just a single value.
            .then(if (isCurrentTurnPlayer) Modifier.background(Color.Green) else Modifier)
            .padding(SCORE_PADDING)
            .background(Color.White)
    ) {
        Text(name)
        Text(
            text = score.toString(),
            fontSize = SCORE_FONT_SIZE,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
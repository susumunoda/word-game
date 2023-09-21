package com.susumunoda.wordgame.ui

import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.susumunoda.wordgame.WordGameState
import com.susumunoda.wordgame.ui.component.VerticalDivider

private val SCORE_BOARD_HEIGHT = 60.dp

@Composable
fun ScoreBoard(state: WordGameState, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.height(SCORE_BOARD_HEIGHT).fillMaxWidth()
    ) {
        PlayerScore(
            name = state.playerOneName,
            score = state.playerOneScore,
            isCurrentTurnPlayer = state.playerOneName == state.currentTurnPlayer,
            modifier = Modifier.weight(1f)
        )
        VerticalDivider(Modifier.fillMaxHeight())
        PlayerScore(
            name = state.playerTwoName,
            score = state.playerTwoScore,
            isCurrentTurnPlayer = state.playerTwoName == state.currentTurnPlayer,
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
            .then(
                if (isCurrentTurnPlayer) {
                    Modifier.border(
                        width = SCORE_PADDING,
                        brush = SolidColor(Color.Green),
                        shape = RectangleShape
                    )
                } else Modifier
            )
            .padding(SCORE_PADDING)
    ) {
        Text(name)
        Text(
            text = score.toString(),
            fontSize = SCORE_FONT_SIZE,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
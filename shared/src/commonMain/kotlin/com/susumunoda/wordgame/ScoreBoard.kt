package com.susumunoda.wordgame

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.susumunoda.wordgame.components.VerticalDivider

private val SCORE_BOARD_HEIGHT = 60.dp

@Composable
fun ScoreBoard(
    playerOneScoreData: ScoreData,
    playerTwoScoreData: ScoreData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.height(SCORE_BOARD_HEIGHT).fillMaxWidth()
    ) {
        PlayerScore(playerOneScoreData, Modifier.weight(1f))
        VerticalDivider(Modifier.fillMaxHeight())
        PlayerScore(playerTwoScoreData, Modifier.weight(1f))
    }
}

private val SCORE_PADDING = 4.dp
private val SCORE_FONT_SIZE = 24.sp

@Composable
private fun PlayerScore(scoreData: ScoreData, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxHeight().padding(SCORE_PADDING)) {
        Text(scoreData.name)
        Text(
            text = scoreData.score.toString(),
            fontSize = SCORE_FONT_SIZE,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
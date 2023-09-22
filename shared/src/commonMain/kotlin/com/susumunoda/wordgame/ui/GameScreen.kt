package com.susumunoda.wordgame.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.susumunoda.wordgame.WordGameState

private val HORIZONTAL_PADDING = PaddingValues(horizontal = 4.dp)

@Composable
fun GameScreen(state: WordGameState) {
    Column {
        ScoreBoard(
            playerOneData = state.playerOneData,
            playerTwoData = state.playerTwoData,
            currentTurnPlayer = state.currentTurnPlayer
        )
        GameBoard(Modifier.padding(HORIZONTAL_PADDING))
        PlayerTiles()
    }
}
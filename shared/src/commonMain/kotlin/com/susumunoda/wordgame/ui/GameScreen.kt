package com.susumunoda.wordgame.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.susumunoda.wordgame.WordGameState

@Composable
fun GameScreen(state: WordGameState) {
    Column {
        ScoreBoard(
            playerOneData = state.playerOneData,
            playerTwoData = state.playerTwoData,
            currentTurnPlayer = state.currentTurnPlayer
        )
        GameBoard()
        PlayerTiles()
    }
}
package com.susumunoda.wordgame.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.susumunoda.wordgame.WordGameState
import com.susumunoda.wordgame.WordGameViewModel

private val HORIZONTAL_PADDING = PaddingValues(horizontal = 4.dp)

@Composable
fun GameScreen(viewModel: WordGameViewModel, state: WordGameState) {
    Column {
        ScoreBoard(
            playerOneData = state.playerOneData,
            playerTwoData = state.playerTwoData,
            currentTurnPlayer = state.currentTurnPlayer
        )
        GameBoard(Modifier.padding(HORIZONTAL_PADDING))
        TilesSection(
            tiles = if (state.currentTurnPlayer == state.playerOneData.name) {
                state.playerOneData.tiles
            } else {
                state.playerTwoData.tiles
            },
            showTiles = state.showUserTiles,
            toggleShowTiles = viewModel::setShowUserTiles,
            modifier = Modifier.padding(HORIZONTAL_PADDING)
        )
    }
}
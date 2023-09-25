package com.susumunoda.wordgame.ui.screen.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.susumunoda.wordgame.WordGameState
import com.susumunoda.wordgame.WordGameViewModel

private val HORIZONTAL_PADDING = PaddingValues(horizontal = 16.dp)

@Composable
fun GameScreen(viewModel: WordGameViewModel, state: WordGameState, modifier: Modifier = Modifier) {
    Column(modifier.padding(HORIZONTAL_PADDING)) {
        ScoresSection(
            playerOneData = state.playerOneData,
            playerTwoData = state.playerTwoData,
            currentTurnPlayer = state.currentTurnPlayer
        )
        GridSection()
        TilesSection(
            tiles = state.currentTurnPlayerTiles,
            showTiles = state.showUserTiles,
            toggleShowTiles = viewModel::setShowUserTiles
        )
        ControlsSection()
    }
}

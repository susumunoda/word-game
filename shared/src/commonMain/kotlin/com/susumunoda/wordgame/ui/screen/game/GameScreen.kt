package com.susumunoda.wordgame.ui.screen.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.susumunoda.wordgame.Tile
import com.susumunoda.wordgame.WordGameState
import com.susumunoda.wordgame.WordGameViewModel
import com.susumunoda.wordgame.ui.component.DragContext

private val HORIZONTAL_PADDING = PaddingValues(horizontal = 16.dp)

// DragContext provides access to the DragTarget and DropTarget composables, which will register
// their wrapped composables as valid targets for dragging and dropping, respectively.
val LocalTileDragContext = compositionLocalOf { DragContext<Tile>() }

@Composable
fun GameScreen(viewModel: WordGameViewModel, state: WordGameState, modifier: Modifier = Modifier) {
    Column(modifier.padding(HORIZONTAL_PADDING)) {
        ScoresSection(
            playerOneData = state.playerOneData,
            playerTwoData = state.playerTwoData,
            currentTurnPlayer = state.currentTurnPlayer
        )
        GridSection()
        PlayerTilesSection(
            tiles = state.currentTurnPlayerTiles,
            tileVisibility = state.showUserTiles,
            onTileVisibilityChanged = viewModel::setShowUserTiles
        )
        ControlsSection()
        RemainingTilesSection(
            remainingTileCounts = state.remainingTileCounts,
            remainingTileTypes = state.remainingTileTypes
        )
    }
}

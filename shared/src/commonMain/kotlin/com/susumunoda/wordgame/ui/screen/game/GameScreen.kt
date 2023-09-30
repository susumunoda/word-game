package com.susumunoda.wordgame.ui.screen.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
fun GameScreen(
    wordGameViewModel: WordGameViewModel,
    wordGameState: WordGameState,
    gridViewModel: GridViewModel = gridViewModel(),
    modifier: Modifier = Modifier
) {
    val gridState by gridViewModel.gridState.collectAsState()
    Column(modifier.padding(HORIZONTAL_PADDING)) {
        ScoresSection(
            playerOneData = wordGameState.playerOneData,
            playerTwoData = wordGameState.playerTwoData,
            currentTurnPlayer = wordGameState.currentTurnPlayer
        )
        GridSection(
            // Caveat: Passing in the view model directly causes Compose to always recompose
            // GridSection any time that GameScreen is recomposed due to the view model being an
            // unstable type. Furthermore, any lambdas within GridSection that would reference the
            // view model would themselves be considered unstable types, causing every grid cell to
            // be recomposed with a new lambda instance. This breaks functionality because
            // `rememberDropTargetState` in `DragContext` disposes of the previous drop target state
            // whenever the configured lambdas change, causing the drag target to no longer be
            // associated with the desired drop target.
            // See https://developer.android.com/jetpack/compose/performance/stability
            //
            // The use of `remember` here is a performance optimization. Without caching these
            // lambdas, even if each grid cell does not get recomposed on every recomposition of the
            // UI, the grid itself would still go through recomposition because Compose detects that
            // the lambdas being passed in the current recomposition are referentially different
            // from the previous recomposition. Caching here ensures that `GridSection` does not
            // get recomposed at all unless something in the grid has changed (e.g. tile placement).
            onGetTile = remember { gridViewModel::getTile },
            onSetTile = remember { gridViewModel::setTile },
            onRemoveTile = remember { gridViewModel::removeTile }
        )
        PlayerTilesSection(
            tiles = wordGameState.currentTurnPlayerTiles,
            tileVisibility = wordGameState.showUserTiles,
            onTileVisibilityChanged = wordGameViewModel::setShowUserTiles,
            isSubmitEnabled = gridState.isSubmitEnabled
        )
        ControlsSection()
        RemainingTilesSection(
            remainingTileCounts = wordGameState.remainingTileCounts,
            remainingTileTypes = wordGameState.remainingTileTypes
        )
    }
}

@Composable
private fun gridViewModel() = remember { GridViewModel() }
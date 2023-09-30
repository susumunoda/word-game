package com.susumunoda.wordgame.ui.screen.game

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import co.touchlab.kermit.Logger
import com.susumunoda.wordgame.GRID
import com.susumunoda.wordgame.Tile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class PlacedTile(val tile: Tile, val isSubmitted: Boolean)

class GridViewModel {
    companion object {
        private const val TAG = "GridViewModel"
    }

    private val _gridState = MutableStateFlow(GridState())
    val gridState = _gridState.asStateFlow()

    // Keeping this state separate from GridState as we want to avoid copying this 2D array repeatedly
    // every time a tile is placed or removed
    private val placedTiles = List<List<MutableState<PlacedTile?>>>(GRID.size) {
        MutableList(GRID.size) {
            mutableStateOf(null)
        }
    }

    fun getTile(row: Int, column: Int) = placedTiles[row][column].value

    fun setTile(tile: Tile, row: Int, column: Int) {
        validateCoordinates(row, column)

        val currentTile = placedTiles[row][column].value
        if (currentTile != null) {
            Logger.w(
                tag = TAG,
                messageString = "Overriding tile $currentTile already present at [$row,$column]"
            )
        }

        placedTiles[row][column].value = PlacedTile(tile, false)

        val newWords = getNewWords()
        val isValidConfiguration = validateConfiguration()

        _gridState.update {
            it.copy(
                placedTileCount = it.placedTileCount + 1,
                isSubmitEnabled = newWords.isNotEmpty() && isValidConfiguration
            )
        }
    }

    fun removeTile(row: Int, column: Int) {
        validateCoordinates(row, column)

        if (placedTiles[row][column].value == null) {
            Logger.w(
                tag = TAG,
                messageString = "No tile present at [$row,$column]"
            )
        }

        placedTiles[row][column].value = null

        val newWords = getNewWords()
        val isValidConfiguration = validateConfiguration()

        _gridState.update {
            it.copy(
                placedTileCount = it.placedTileCount - 1,
                isSubmitEnabled = newWords.isNotEmpty() && isValidConfiguration
            )
        }
    }

    private fun getNewWords() = emptyList<String>()

    private fun validateConfiguration() = true

    private fun validateCoordinates(row: Int, column: Int) {
        val rowMax = placedTiles.size - 1
        // Assume there is at least 1 row
        val columnMax = placedTiles[0].size - 1
        if (row < 0 || row > rowMax || column < 0 || column > columnMax) {
            throw IllegalArgumentException("Tile coordinates must be between [0,0] and [$rowMax,$columnMax$]")
        }
    }
}

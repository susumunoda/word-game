package com.susumunoda.wordgame

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import co.touchlab.kermit.Logger

data class PlacedTile(val tile: Tile, val isSubmitted: Boolean)

class GridState {
    companion object {
        private const val TAG = "GridState"
    }

    val placedTiles = List<List<MutableState<PlacedTile?>>>(GRID.size) {
        MutableList(GRID.size) {
            mutableStateOf(null)
        }
    }

    fun getTile(row: Int, column: Int) = placedTiles[row][column].value

    fun placeTile(tile: Tile, row: Int, column: Int): Boolean {
        validateCoordinates(row, column)

        val currentTile = placedTiles[row][column].value
        if (currentTile != null) {
            Logger.w(
                tag = TAG,
                messageString = "Tile $currentTile already present at [$row,$column]. Skipping..."
            )
            return false
        }

        placedTiles[row][column].value = PlacedTile(tile, false)
        return true
    }

    fun removeTile(row: Int, column: Int): Boolean {
        validateCoordinates(row, column)

        if (placedTiles[row][column].value == null) {
            Logger.w(
                tag = TAG,
                messageString = "No tile present at [$row,$column]"
            )
        }

        placedTiles[row][column].value = null
        return true
    }

    fun validate(): List<String> {
        return emptyList()
    }

    private fun validateCoordinates(row: Int, column: Int) {
        val rowMax = placedTiles.size - 1
        // Assume there is at least 1 row
        val columnMax = placedTiles[0].size - 1
        if (row < 0 || row > rowMax || column < 0 || column > columnMax) {
            throw IllegalArgumentException("Tile coordinates must be between [0,0] and [$rowMax,$columnMax$]")
        }
    }
}

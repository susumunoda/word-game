package com.susumunoda.wordgame

import com.susumunoda.wordgame.data.PlayerData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WordGameViewModel {
    companion object {
        private const val TILES_PER_USER = 7
    }

    private val _uiState = MutableStateFlow(WordGameState())
    val uiState = _uiState.asStateFlow()

    fun startNewGame(playerOneName: String, playerTwoName: String, startPlayer: String) {
        val initialTiles = getInitialTiles()

        _uiState.value = WordGameState(
            playerOneData = PlayerData(
                name = playerOneName,
                tiles = getUserTiles(initialTiles)
            ),
            playerTwoData = PlayerData(
                name = playerTwoName,
                tiles = getUserTiles(initialTiles)
            ),
            currentTurnPlayer = startPlayer,
            gameStatus = GameStatus.STARTED,
            availableTiles = initialTiles
        )
    }

    fun setShowUserTiles(showUserTiles: Boolean) {
        _uiState.update { it.copy(showUserTiles = showUserTiles) }
    }

    private fun getUserTiles(availableTiles: MutableList<Tile>): List<Tile> {
        val userTiles = mutableListOf<Tile>()
        while (userTiles.size < TILES_PER_USER && availableTiles.isNotEmpty()) {
            val randomTile = availableTiles.random()
            availableTiles.remove(randomTile)
            userTiles.add(randomTile)
        }
        return userTiles
    }

    private fun getInitialTiles() =
        Tile.values().flatMap { tile -> List(tile.distribution) { tile } }.toMutableList()
}

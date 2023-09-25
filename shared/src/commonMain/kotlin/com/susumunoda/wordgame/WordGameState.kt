package com.susumunoda.wordgame

import com.susumunoda.wordgame.data.PlayerData

data class WordGameState(
    val gameStatus: GameStatus = GameStatus.NOT_STARTED,
    val playerOneData: PlayerData = PlayerData(),
    val playerTwoData: PlayerData = PlayerData(),
    val currentTurnPlayer: String = "",
    val remainingTiles: List<Tile> = emptyList(),
    val showUserTiles: Boolean = false
) {
    val currentTurnPlayerTiles: List<Tile>
        get() = when (currentTurnPlayer) {
            playerOneData.name -> playerOneData.tiles
            playerTwoData.name -> playerTwoData.tiles
            else -> throw IllegalStateException("Invalid player: $currentTurnPlayer")
        }

    private lateinit var _remainingTileCounts: Map<Tile, Int>
    val remainingTileCounts: Map<Tile, Int>
        get() {
            if (!this::_remainingTileCounts.isInitialized) {
                _remainingTileCounts = remainingTiles.groupingBy { it }.eachCount()
            }
            return _remainingTileCounts
        }

    private lateinit var _remainingTileTypes: List<Tile>
    val remainingTileTypes: List<Tile>
        get() {
            if (!this::_remainingTileTypes.isInitialized) {
                _remainingTileTypes = Tile.values().filter { remainingTileCounts[it] != null }
            }
            return _remainingTileTypes
        }
}

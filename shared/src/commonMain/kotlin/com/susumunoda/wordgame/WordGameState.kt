package com.susumunoda.wordgame

import com.susumunoda.wordgame.data.PlayerData

data class WordGameState(
    val gameStatus: GameStatus = GameStatus.NOT_STARTED,
    val playerOneData: PlayerData = PlayerData(),
    val playerTwoData: PlayerData = PlayerData(),
    val currentTurnPlayer: String = "",
    val availableTiles: List<Tile> = emptyList(),
    val showUserTiles: Boolean = false
) {
    val currentTurnPlayerTiles: List<Tile>
        get() = when (currentTurnPlayer) {
            playerOneData.name -> playerOneData.tiles
            playerTwoData.name -> playerTwoData.tiles
            else -> throw IllegalStateException("Invalid player: $currentTurnPlayer")
        }
}

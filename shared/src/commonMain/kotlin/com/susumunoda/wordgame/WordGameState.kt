package com.susumunoda.wordgame

import com.susumunoda.wordgame.data.PlayerData

data class WordGameState(
    val gameStatus: GameStatus = GameStatus.NOT_STARTED,
    val playerOneData: PlayerData = PlayerData(),
    val playerTwoData: PlayerData = PlayerData(),
    val currentTurnPlayer: String = "",
)

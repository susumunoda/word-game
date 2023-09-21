package com.susumunoda.wordgame

data class WordGameState(
    val gameStatus: GameStatus = GameStatus.NOT_STARTED,
    val playerOneName: String = "",
    val playerOneScore: Int = 0,
    val playerOneTiles: List<Char> = emptyList(),
    val playerTwoName: String = "",
    val playerTwoScore: Int = 0,
    val playerTwoTiles: List<Char> = emptyList(),
    val currentTurnPlayer: String? = null
)

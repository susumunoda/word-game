package com.susumunoda.wordgame

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WordGameViewModel {
    private val _uiState = MutableStateFlow(WordGameState())
    val uiState = _uiState.asStateFlow()

    fun startNewGame(playerOneName: String, playerTwoName: String, startPlayer: String) {
        _uiState.value = WordGameState(
            playerOneName = playerOneName,
            playerTwoName = playerTwoName,
            currentTurnPlayer = startPlayer,
            gameStatus = GameStatus.STARTED
        )
    }
}
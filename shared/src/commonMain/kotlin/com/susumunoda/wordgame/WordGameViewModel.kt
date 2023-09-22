package com.susumunoda.wordgame

import com.susumunoda.wordgame.data.PlayerData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WordGameViewModel {
    private val _uiState = MutableStateFlow(WordGameState())
    val uiState = _uiState.asStateFlow()

    fun startNewGame(playerOneName: String, playerTwoName: String, startPlayer: String) {
        _uiState.value = WordGameState(
            playerOneData = PlayerData(
                name = playerOneName,
            ),
            playerTwoData = PlayerData(
                name = playerTwoName,
            ),
            currentTurnPlayer = startPlayer,
            gameStatus = GameStatus.STARTED
        )
    }
}
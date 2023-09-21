package com.susumunoda.wordgame

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WordGameViewModel {
    private val _uiState = MutableStateFlow(WordGameState())
    val uiState = _uiState.asStateFlow()

    fun setPlayerNames(playerOneName: String, playerTwoName: String) {
        _uiState.update {
            it.copy(
                playerOneName = playerOneName,
                playerTwoName = playerTwoName
            )
        }
    }
}
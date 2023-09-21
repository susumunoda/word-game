package com.susumunoda.wordgame

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WordGameViewModel {
    private val _uiState = MutableStateFlow(WordGameState())
    val uiState = _uiState.asStateFlow()
}
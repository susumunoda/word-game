package com.susumunoda.wordgame

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.susumunoda.wordgame.ui.GameScreen
import com.susumunoda.wordgame.ui.LandingScreen

@Composable
fun WordGame(viewModel: WordGameViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    if (!uiState.isInitialized) {
        LandingScreen(viewModel::setPlayerNames)
    } else {
        GameScreen(uiState)
    }
}

@Composable
private fun viewModel(): WordGameViewModel = remember { WordGameViewModel() }
package com.susumunoda.wordgame

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.susumunoda.wordgame.ui.screen.game.GameScreen
import com.susumunoda.wordgame.ui.screen.landing.LandingScreen
import com.susumunoda.wordgame.ui.screen.summary.SummaryScreen

@Composable
fun WordGame(viewModel: WordGameViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState.gameStatus) {
        GameStatus.NOT_STARTED -> {
            LandingScreen(
                onSubmitPlayerNames = { playerOneName, playerTwoName ->
                    viewModel.startNewGame(
                        playerOneName = playerOneName,
                        playerTwoName = playerTwoName,
                        startPlayer = playerOneName
                    )
                }
            )
        }

        GameStatus.STARTED -> {
            GameScreen(viewModel, uiState)
        }

        GameStatus.FINISHED -> {
            SummaryScreen(
                playerOneData = uiState.playerOneData,
                playerTwoData = uiState.playerTwoData
            )
        }
    }
}

@Composable
private fun viewModel(): WordGameViewModel = remember { WordGameViewModel() }
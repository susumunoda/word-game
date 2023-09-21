package com.susumunoda.wordgame

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.susumunoda.wordgame.ui.GameScreen
import com.susumunoda.wordgame.ui.LandingScreen
import com.susumunoda.wordgame.ui.SummaryScreen

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
            GameScreen(uiState)
        }

        GameStatus.FINISHED -> {
            SummaryScreen(
                playerOneName = uiState.playerOneName,
                playerOneScore = uiState.playerOneScore,
                playerTwoName = uiState.playerTwoName,
                playerTwoScore = uiState.playerTwoScore
            )
        }
    }
}

@Composable
private fun viewModel(): WordGameViewModel = remember { WordGameViewModel() }
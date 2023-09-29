package com.susumunoda.wordgame

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.susumunoda.wordgame.ui.screen.game.GameScreen
import com.susumunoda.wordgame.ui.screen.landing.LandingScreen
import com.susumunoda.wordgame.ui.screen.summary.SummaryScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

// FUTURE: Provide these via dependency injection
private val globalScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
private val wordListProvider = WordListProvider(globalScope)

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
            if (wordListProvider.wordListState != null) {
                GameScreen(viewModel, uiState)
            } else {
                // Prevent interactions until the word list has finished being loaded
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
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

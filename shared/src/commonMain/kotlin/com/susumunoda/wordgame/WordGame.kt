package com.susumunoda.wordgame

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.susumunoda.wordgame.ui.GameBoard
import com.susumunoda.wordgame.ui.PlayerTiles
import com.susumunoda.wordgame.ui.ScoreBoard


@Composable
fun WordGame(viewModel: WordGameViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    MaterialTheme {
        Column {
            ScoreBoard(uiState)
            GameBoard()
            PlayerTiles()
        }
    }
}

@Composable
private fun viewModel(): WordGameViewModel = remember { WordGameViewModel() }
package com.susumunoda.wordgame

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.susumunoda.wordgame.data.ScoreData
import com.susumunoda.wordgame.ui.GameBoard
import com.susumunoda.wordgame.ui.ScoreBoard


@Composable
fun WordGame() {
    val playerOneScoreData = ScoreData("Bob", 10)
    val playerTwoScoreData = ScoreData("Sally", 95)

    MaterialTheme {
        Column {
            ScoreBoard(
                playerOneScoreData = playerOneScoreData,
                playerTwoScoreData = playerTwoScoreData,
                nextMovePlayer = "Bob"
            )
            GameBoard()
        }
    }
}

package com.susumunoda.wordgame.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val HORIZONTAL_PADDING = 24.dp
private val SPACER_SIZE = 8.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(onSubmitPlayerNames: (String, String) -> Unit) {
    var playerOneName by remember { mutableStateOf("") }
    var playerTwoName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Word Game!") })
        }
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(contentPadding)
                .padding(PaddingValues(horizontal = HORIZONTAL_PADDING))
        ) {
            OutlinedTextField(
                label = { Text("Player 1") },
                value = playerOneName,
                onValueChange = { playerOneName = it },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                label = { Text("Player 2") },
                value = playerTwoName,
                onValueChange = { playerTwoName = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.size(SPACER_SIZE))
            Button(
                enabled = playerOneName.isNotBlank() && playerTwoName.isNotBlank(),
                onClick = { onSubmitPlayerNames(playerOneName, playerTwoName) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Start")
            }
        }
    }
}
package com.susumunoda.wordgame.ui.screen.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ControlsSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(BUTTON_SPACING),
            modifier = modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = { },
                modifier = Modifier.weight(1f)
            ) {
                Text("Resign")
            }
            OutlinedButton(
                onClick = { },
                modifier = Modifier.weight(1f)
            ) {
                Text("Skip")
            }
            OutlinedButton(
                onClick = { },
                modifier = Modifier.weight(1f)
            ) {
                Text("Swap")
            }
        }
    }
}

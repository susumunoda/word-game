package com.susumunoda.wordgame.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private val BUTTON_SPACING = 8.dp
private val ICON_SIZE = 16.dp

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ControlsSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(BUTTON_SPACING),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { },
            ) {
                Icon(
                    painter = painterResource("shuffle_icon.xml"),
                    contentDescription = "Shuffle tiles",
                    modifier = Modifier.size(ICON_SIZE)
                )
            }
            Button(
                onClick = { },
                modifier = Modifier.weight(1f)
            ) {
                Text("Submit")
            }
            Button(
                onClick = { },
            ) {
                Icon(
                    painter = painterResource("undo_icon.xml"),
                    contentDescription = "Clear tiles",
                    modifier = Modifier.size(ICON_SIZE)
                )
            }
        }
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

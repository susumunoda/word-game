package com.susumunoda.wordgame.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.susumunoda.wordgame.Tile

@Composable
fun TilesSection(
    tiles: List<Tile>,
    showTiles: Boolean,
    toggleShowTiles: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Show tiles")
            Switch(
                checked = showTiles,
                onCheckedChange = toggleShowTiles
            )
        }
        AnimatedVisibility(visible = showTiles) {
            Tiles(tiles = tiles)
        }
    }
}

private val TILE_SPACING = 8.dp

@Composable
private fun Tiles(tiles: List<Tile>, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier) {
        val tileSize = (maxWidth - (TILE_SPACING * (tiles.size - 1))) / tiles.size
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            tiles.forEach { tile ->
                Box(
                    modifier = Modifier
                        .size(tileSize)
                        .background(Color.Yellow)
                ) {
                    if (tile != Tile.BLANK) {
                        Text("${tile.name}(${tile.points})")
                    }
                }
            }
        }
    }
}
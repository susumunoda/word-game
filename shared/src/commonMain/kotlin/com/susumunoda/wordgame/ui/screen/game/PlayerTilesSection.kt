package com.susumunoda.wordgame.ui.screen.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.susumunoda.wordgame.Tile
import com.susumunoda.wordgame.ui.component.DragOptions
import com.susumunoda.wordgame.ui.component.withDragContext

private val TILES_ROW_BOTTOM_PADDING = 8.dp

@Composable
fun PlayerTilesSection(
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
            PlayerTiles(
                tiles = tiles,
                modifier = Modifier.padding(bottom = TILES_ROW_BOTTOM_PADDING)
            )
        }
    }
}

private val TILE_SPACING = 8.dp

@Composable
private fun PlayerTiles(tiles: List<Tile>, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier) {
        val tileSize = (maxWidth - (TILE_SPACING * (tiles.size - 1))) / tiles.size
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            tiles.forEach { tile ->
                Tile(
                    tile = tile,
                    tileSize = tileSize
                )
            }
        }
    }
}

private const val TILE_DRAG_SCALE_FACTOR = 0.5f
private val TILE_LETTER_FONT_SIZE = 24.sp
private val TILE_POINTS_FONT_SIZE = 16.sp
private val TILE_POINTS_PADDING = 2.dp

@Composable
private fun Tile(tile: Tile, tileSize: Dp, modifier: Modifier = Modifier) {
    withDragContext(LocalTileDragContext.current) {
        DragTarget(
            data = tile,
            dragOptions = DragOptions(
                onDragScaleX = TILE_DRAG_SCALE_FACTOR,
                onDragScaleY = TILE_DRAG_SCALE_FACTOR
            )
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .size(tileSize)
                    .background(Color.Yellow)
            ) {
                if (tile != Tile.BLANK) {
                    Text(
                        text = tile.name,
                        fontSize = TILE_LETTER_FONT_SIZE,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = tile.points.toString(),
                        fontSize = TILE_POINTS_FONT_SIZE,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(TILE_POINTS_PADDING)
                    )
                }
            }
        }
    }
}

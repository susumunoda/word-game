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
import com.susumunoda.wordgame.ui.component.SnapPosition
import com.susumunoda.wordgame.ui.component.withDragContext

private val TILES_ROW_BOTTOM_PADDING = 8.dp

@Composable
fun PlayerTilesSection(
    tiles: List<Tile>,
    showTiles: Boolean,
    toggleShowTiles: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val dragContext = LocalTileDragContext.current
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Show tiles")
            Switch(
                checked = showTiles,
                onCheckedChange = { checked ->
                    toggleShowTiles(checked)
                    if (!checked) {
                        // This is not strictly necessary as DragContext state will automatically
                        // get cleaned up after the tiles leave the composition, but there is a
                        // slight delay until the state is cleared (e.g. for any hovered cells to
                        // go back to an unhovered state). Manually clear state for better UX.
                        dragContext.resetDragTargets()
                    }
                }
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

// This value was somewhat arbitrarily chosen and happens to work well for this specific instance,
// but it is obviously a coincidence based on the dimensions of the grid and number of player tiles.
// In the future, the grid cell and tile sizes should probably be hard-coded instead of calculated
// based on the available screen width, as it is today.
private const val TILE_DROP_SCALE_FACTOR = 0.5f
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
                onDragScaleY = TILE_DRAG_SCALE_FACTOR,
                onDropScaleX = TILE_DROP_SCALE_FACTOR,
                onDropScaleY = TILE_DROP_SCALE_FACTOR,
                snapPosition = SnapPosition.CENTER
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

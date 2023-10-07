package com.susumunoda.wordgame.ui.screen.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.susumunoda.compose.gestures.withDragContext
import com.susumunoda.wordgame.CellType
import com.susumunoda.wordgame.GRID
import com.susumunoda.wordgame.Tile

private val TILE_SPACING = 2.dp

@Composable
internal fun GridSection(
    onGetTile: (row: Int, column: Int) -> PlacedTile?,
    onSetTile: (tile: Tile, row: Int, column: Int) -> Unit,
    onRemoveTile: (row: Int, column: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier) {
        val cellSize = (maxWidth - (TILE_SPACING * (GRID.size - 1))) / GRID.size

        // Assuming we are in portrait mode, the board is a square that is width * width in size
        Box(Modifier.size(maxWidth)) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                GRID.forEachIndexed { i, row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        row.forEachIndexed { j, cellType ->
                            GridCell(
                                row = i,
                                column = j,
                                cellType = cellType,
                                cellSize = cellSize,
                                onGetTile = onGetTile,
                                onSetTile = onSetTile,
                                onRemoveTile = onRemoveTile
                            )
                        }
                    }
                }
            }
        }
    }
}

private val HOVERED_BORDER_WIDTH = 2.dp

@Composable
private fun GridCell(
    row: Int,
    column: Int,
    cellType: CellType,
    cellSize: Dp,
    onGetTile: (row: Int, column: Int) -> PlacedTile?,
    onSetTile: (tile: Tile, row: Int, column: Int) -> Unit,
    onRemoveTile: (row: Int, column: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    withDragContext(LocalTileDragContext.current) {
        DropTarget(
            onDragTargetAdded = { onSetTile(it, row, column) },
            onDragTargetRemoved = { onRemoveTile(row, column) }
        ) { isHovered ->
            // Important: Observe the tile state here and not in `GridSection` in order to avoid
            // recomposing the entire grid whenever the state of a single tile is updated (i.e.
            // looping through every cell just to recompose the one cell whose data changed)
            val placedTile = onGetTile(row, column)?.tile
            if (placedTile == null) {
                EmptyCell(cellType, cellSize, isHovered)
            } else {
                TileCell(placedTile, cellSize)
            }
        }
    }
}

private val EMPTY_CELL_FONT_SIZE = 8.sp
private val TILE_ROUNDING = 4.dp

@Composable
private fun EmptyCell(
    cellType: CellType,
    cellSize: Dp,
    isHovered: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(cellSize)
            .clip(RoundedCornerShape(TILE_ROUNDING))
            .background(cellType.color)
            .then(
                if (isHovered) {
                    Modifier.border(HOVERED_BORDER_WIDTH, Color.Red)
                } else {
                    Modifier
                }
            )
    ) {
        if (cellType == CellType.ST) {
            Icon(Icons.Filled.Star, null, tint = Color.White)
        } else {
            Text(
                cellType.name,
                fontSize = EMPTY_CELL_FONT_SIZE,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private val TILE_LETTER_FONT_SIZE = 10.sp
private val TILE_POINTS_FONT_SIZE = 6.sp
private val TILE_POINTS_PADDING = 2.dp

@Composable
private fun TileCell(tile: Tile, cellSize: Dp, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(cellSize)
            .clip(RoundedCornerShape(TILE_ROUNDING))
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

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
import co.touchlab.kermit.Logger
import com.susumunoda.wordgame.CellType
import com.susumunoda.wordgame.GRID
import com.susumunoda.wordgame.Tile
import com.susumunoda.wordgame.ui.component.withDragContext

private val TILE_SPACING = 2.dp
private val TILE_FONT_SIZE = 8.sp
private val TILE_ROUNDING = 4.dp

@Composable
internal fun GridSection(modifier: Modifier = Modifier) {
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
                            EmptyCell(
                                cellType = cellType,
                                cellSize = cellSize,
                                onTilePlaced = { tile ->
                                    if (tile != null) {
                                        Logger.i(
                                            tag = "GridSection",
                                            messageString = "Placed ${tile.name} at [$i,$j]"
                                        )
                                    }
                                },
                                onTileRemoved = { tile ->
                                    if (tile != null) {
                                        Logger.i(
                                            tag = "GridSection",
                                            messageString = "Removed ${tile.name} from [$i,$j]"
                                        )
                                    }
                                }
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
private fun EmptyCell(
    cellType: CellType,
    cellSize: Dp,
    onTilePlaced: (Tile?) -> Unit,
    onTileRemoved: (Tile?) -> Unit,
    modifier: Modifier = Modifier
) {
    withDragContext(LocalTileDragContext.current) {
        DropTarget(
            onDrop = onTilePlaced,
            onDrag = onTileRemoved
        ) { isHovered ->
            Box(
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
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (cellType == CellType.ST) {
                    Icon(Icons.Filled.Star, null, tint = Color.White)
                } else {
                    Text(
                        cellType.name,
                        fontSize = TILE_FONT_SIZE,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
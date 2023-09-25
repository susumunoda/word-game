package com.susumunoda.wordgame.ui.screen.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.susumunoda.wordgame.Tile

private const val TILE_COUNTS_PER_COLUMN = 7

@Composable
fun RemainingTilesSection(
    remainingTileCounts: Map<Tile, Int>,
    remainingTileTypes: List<Tile>,
    modifier: Modifier = Modifier
) {
    var remainingCountsToDisplay = remainingTileTypes.size
    if (remainingCountsToDisplay > 0) {
        Row(modifier = modifier.fillMaxWidth()) {
            var rangeStart = 0
            var rangeEnd = TILE_COUNTS_PER_COLUMN
            while (remainingCountsToDisplay > 0) {
                RemainingTilesColumn(
                    remainingTileCounts = remainingTileCounts,
                    remainingTileTypes = remainingTileTypes,
                    rangeStartInclusive = rangeStart,
                    rangeEndExclusive = rangeEnd,
                    modifier = Modifier.weight(1f)
                )
                rangeStart += TILE_COUNTS_PER_COLUMN
                rangeEnd += TILE_COUNTS_PER_COLUMN
                remainingCountsToDisplay -= TILE_COUNTS_PER_COLUMN
            }
        }
    }
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
private fun RemainingTilesColumn(
    remainingTileCounts: Map<Tile, Int>,
    remainingTileTypes: List<Tile>,
    rangeStartInclusive: Int,
    rangeEndExclusive: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        for (i in rangeStartInclusive..<rangeEndExclusive) {
            if (i < remainingTileTypes.size) {
                val tile = remainingTileTypes[i]
                val tileCount = remainingTileCounts[tile]
                Text("${tile.displayName}: ${tileCount!!}")
            }
        }
    }
}

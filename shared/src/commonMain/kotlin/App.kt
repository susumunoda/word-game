import TileType.BL
import TileType.DL
import TileType.DW
import TileType.ST
import TileType.TL
import TileType.TW
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private enum class TileType(val color: Color) {
    TW(Color(255, 74, 195)), // Triple word
    TL(Color(37, 98, 250)), // Triple letter
    DW(Color(255, 107, 74)), // Double word
    DL(Color(177, 220, 252)), // Double letter
    BL(Color(192, 193, 194)), // Blank
    ST(Color(20, 66, 112)), // Start
}

private val GRID = arrayOf(
    arrayOf(TW, BL, BL, DL, BL, BL, BL, TW, BL, BL, BL, DL, BL, BL, TW), ////////
    arrayOf(BL, DW, BL, BL, BL, TL, BL, BL, BL, TL, BL, BL, BL, DW, BL), ///////
    arrayOf(BL, BL, DW, BL, BL, BL, DL, BL, DL, BL, BL, BL, DW, BL, BL), //////
    arrayOf(DL, BL, BL, DW, BL, BL, BL, DL, BL, BL, BL, DW, BL, BL, DL), /////
    arrayOf(BL, BL, BL, BL, DW, BL, BL, BL, BL, BL, DW, BL, BL, BL, BL), ////
    arrayOf(BL, TL, BL, BL, BL, TL, BL, BL, BL, TL, BL, BL, BL, TL, BL), ///
    arrayOf(BL, BL, DL, BL, BL, BL, DL, BL, DL, BL, BL, BL, DL, BL, BL), //
    arrayOf(TW, BL, BL, DL, BL, BL, BL, ST, BL, BL, BL, DL, BL, BL, TW),
    arrayOf(BL, BL, DL, BL, BL, BL, DL, BL, DL, BL, BL, BL, DL, BL, BL), //
    arrayOf(BL, TL, BL, BL, BL, TL, BL, BL, BL, TL, BL, BL, BL, TL, BL), ///
    arrayOf(BL, BL, BL, BL, DW, BL, BL, BL, BL, BL, DW, BL, BL, BL, BL), ////
    arrayOf(DL, BL, BL, DW, BL, BL, BL, DL, BL, BL, BL, DW, BL, BL, DL), /////
    arrayOf(BL, BL, DW, BL, BL, BL, DL, BL, DL, BL, BL, BL, DW, BL, BL), //////
    arrayOf(BL, DW, BL, BL, BL, TL, BL, BL, BL, TL, BL, BL, BL, DW, BL), ///////
    arrayOf(TW, BL, BL, DL, BL, BL, BL, TW, BL, BL, BL, DL, BL, BL, TW), ////////
)

private val GRID_PADDING = 8.dp
private val TILE_SPACING = 2.dp
private val TILE_FONT_SIZE = 8.sp
private val TILE_ROUNDING = 4.dp

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun App() {
    MaterialTheme {
        BoxWithConstraints(Modifier.fillMaxSize().padding(GRID_PADDING)) {
            val tileSize = (maxWidth - (TILE_SPACING * (GRID.size - 1))) / GRID.size
            Column(verticalArrangement = Arrangement.spacedBy(TILE_SPACING)) {
                GRID.forEachIndexed { _, row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(TILE_SPACING)) {
                        row.forEachIndexed { _, tileType ->
                            Box(
                                modifier = Modifier
                                    .size(tileSize)
                                    .clip(RoundedCornerShape(TILE_ROUNDING))
                                    .background(tileType.color),
                                contentAlignment = Alignment.Center,
                            ) {
                                if (tileType == ST) {
                                    Icon(Icons.Filled.Star, null, tint = Color.White)
                                } else {
                                    Text(
                                        tileType.name,
                                        fontSize = TILE_FONT_SIZE,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

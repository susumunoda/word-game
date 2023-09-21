package com.susumunoda.wordgame

import androidx.compose.ui.graphics.Color

internal enum class TileType(val color: Color) {
    TW(Color(255, 74, 195)), // Triple word
    TL(Color(37, 98, 250)), // Triple letter
    DW(Color(255, 107, 74)), // Double word
    DL(Color(177, 220, 252)), // Double letter
    BL(Color(192, 193, 194)), // Blank
    ST(Color(20, 66, 112)), // Start
}

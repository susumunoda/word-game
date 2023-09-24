package com.susumunoda.wordgame

import androidx.compose.ui.graphics.Color
import com.susumunoda.wordgame.CellType.BL
import com.susumunoda.wordgame.CellType.DL
import com.susumunoda.wordgame.CellType.DW
import com.susumunoda.wordgame.CellType.ST
import com.susumunoda.wordgame.CellType.TL
import com.susumunoda.wordgame.CellType.TW

internal enum class CellType(val color: Color) {
    TW(Color(255, 74, 195)), // Triple word
    TL(Color(37, 98, 250)), // Triple letter
    DW(Color(255, 107, 74)), // Double word
    DL(Color(177, 220, 252)), // Double letter
    BL(Color(192, 193, 194)), // Blank
    ST(Color(20, 66, 112)), // Start
}

internal val GRID = arrayOf(
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

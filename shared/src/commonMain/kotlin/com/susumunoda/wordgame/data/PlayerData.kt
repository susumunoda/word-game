package com.susumunoda.wordgame.data

import com.susumunoda.wordgame.Tile

data class PlayerData(
    val name: String = "",
    val score: Int = 0,
    val tiles: List<Tile> = emptyList()
)

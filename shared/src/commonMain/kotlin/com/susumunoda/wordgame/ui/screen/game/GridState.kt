package com.susumunoda.wordgame.ui.screen.game

data class GridState(
    val placedTileCount: Int = 0,
    val errorMessages: List<String> = emptyList(),
    val isSubmitEnabled: Boolean = false
)

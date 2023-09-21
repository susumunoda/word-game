package com.susumunoda.wordgame.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = Color.Black
) {
    Box(modifier.width(thickness).background(color))
}
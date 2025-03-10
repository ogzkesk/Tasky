package com.ogzkesk.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DismissBoxLayout(
    modifier: Modifier = Modifier,
    state: SwipeToDismissBoxState,
    content: @Composable RowScope.() -> Unit
) {
    SwipeToDismissBox(
        modifier = modifier,
        state = state,
        backgroundContent = {
            if (state.targetValue == SwipeToDismissBoxValue.EndToStart) {
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.errorContainer),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        modifier = Modifier.padding(16.dp),
                        imageVector = Icons.Rounded.Delete,
                        tint = Color.White,
                        contentDescription = null
                    )
                }
            } else if (state.targetValue == SwipeToDismissBoxValue.StartToEnd) {
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Icon(
                        modifier = Modifier.padding(16.dp),
                        imageVector = Icons.Rounded.CheckCircle,
                        tint = Color.White,
                        contentDescription = null
                    )
                }
            }
        },
        content = content
    )
}
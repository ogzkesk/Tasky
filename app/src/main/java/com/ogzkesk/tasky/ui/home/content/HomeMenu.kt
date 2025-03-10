package com.ogzkesk.tasky.ui.home.content

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ogzkesk.tasky.ui.home.HomeScreenState

@Composable
fun HomeMenu(
    expanded: Boolean,
    sortingMethod: HomeScreenState.SortingMethod,
    onExpandChange: (Boolean) -> Unit,
    onSort: (HomeScreenState.SortingMethod) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onExpandChange(false) }
    ) {
        HomeScreenState.SortingMethod.entries.forEach { method ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = method.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (sortingMethod == method) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            Color.Unspecified
                        }
                    )
                },
                onClick = {
                    onSort(method)
                }
            )
        }
    }
}
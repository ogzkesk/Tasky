package com.ogzkesk.tasky.ui.home.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ogzkesk.tasky.R
import com.ogzkesk.tasky.ui.home.HomeScreenEvent
import com.ogzkesk.tasky.ui.home.HomeScreenState

@Composable
fun HomeMenu(
    state: HomeScreenState,
    onEvent: (HomeScreenEvent) -> Unit,
) {
    Column {
        IconButton(
            onClick = {
                onEvent(HomeScreenEvent.ToggleDropdownMenu(true))
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Sort,
                contentDescription = "sort"
            )
        }
        SortMenu(
            expanded = state.showSortDropdown,
            sortingMethod = state.sortingMethod,
            onExpandChange = { onEvent(HomeScreenEvent.ToggleDropdownMenu(it)) },
            onSort = { onEvent(HomeScreenEvent.OnSortMethodChanged(it)) }
        )
    }
    Column {
        IconButton(
            onClick = {
                onEvent(HomeScreenEvent.ToggleMenu(true))
            }
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "menu"
            )
        }
        Menu(
            expanded = state.showMenu,
            onExpandChange = { onEvent(HomeScreenEvent.ToggleMenu(it)) },
            onClearClick = { onEvent(HomeScreenEvent.ToggleClearDialog(true)) },
            onInsertTasksClick = { onEvent(HomeScreenEvent.InsertTestData) },
            onSettingsClick = { onEvent(HomeScreenEvent.NavigateToSettings(true)) }
        )
    }
}

@Composable
private fun SortMenu(
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

@Composable
private fun Menu(
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onSettingsClick: () -> Unit,
    onClearClick: () -> Unit,
    onInsertTasksClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onExpandChange(false) }
    ) {
        DropdownItem(
            title = stringResource(R.string.home_screen_menu_settings),
            icon = Icons.Default.Settings,
            contentDescription = "settings",
            onClick = onSettingsClick
        )
        DropdownItem(
            title = stringResource(R.string.home_screen_clear_tasks),
            icon = Icons.Outlined.Delete,
            contentDescription = "delete",
            onClick = onClearClick
        )
        DropdownItem(
            title = stringResource(R.string.home_screen_menu_insert_tasks),
            icon = Icons.Default.UploadFile,
            contentDescription = "insert",
            onClick = onInsertTasksClick
        )
    }
}

@Composable
fun DropdownItem(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        modifier = modifier,
        text = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        leadingIcon = {
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = icon,
                contentDescription = contentDescription
            )
        },
        onClick = onClick
    )
}

package com.ogzkesk.tasky.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ogzkesk.domain.ext.toLocalDateTime
import com.ogzkesk.domain.model.Task
import com.ogzkesk.tasky.R
import com.ogzkesk.ui.composable.TaskyAlertDialog
import com.ogzkesk.ui.composable.TaskyTopBar
import com.ogzkesk.ui.theme.TaskyTheme
import com.ogzkesk.ui.theme.semiBold
import com.ogzkesk.ui.util.ThemedPreviews
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
    state: DetailScreenState,
    onEvent: (DetailScreenEvent) -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    TaskyAlertDialog(
        enabled = state.showTrashDialog,
        title = stringResource(R.string.detail_screen_trash_dialog_title),
        message = stringResource(R.string.detail_screen_trash_dialog_message),
        onConfirm = {
            onEvent(DetailScreenEvent.ToggleTrashDialog(false))
            onEvent(
                DetailScreenEvent.MoveToTrash {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.detail_screen_moved_to_trash_message),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            )
        },
        onDismiss = {
            onEvent(DetailScreenEvent.ToggleTrashDialog(false))
        }
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TaskyTopBar(
                title = stringResource(R.string.detail_screen_title),
                onBack = navController::popBackStack,
                actions = {
                    if (state.task?.isDeleted == true) {
                        IconButton(
                            onClick = {
                                onEvent(
                                    DetailScreenEvent.RestoreFromTrash {
                                        coroutineScope.launch {
                                            snackBarHostState.showSnackbar(
                                                message = context.getString(R.string.detail_screen_restored_from_trash_message),
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    }
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Restore,
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = "restore_from_trash"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {
                                onEvent(DetailScreenEvent.ToggleTrashDialog(true))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "move_to_trash"
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.task?.let { task ->
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.headlineMedium.semiBold
                )

                Text(
                    text = task.description.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = task.createdAt
                        .toLocalDateTime()
                        .format(DateTimeFormatter.ISO_DATE) ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Priority:",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = task.priority.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Completed:",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Checkbox(
                        checked = task.isCompleted,
                        onCheckedChange = {
                            onEvent(DetailScreenEvent.ToggleTaskCompleted(it))
                        }
                    )
                }
            }
        }
    }
}

@ThemedPreviews
@Composable
private fun HomeScreenPreview() {
    TaskyTheme {
        DetailScreen(
            navController = rememberNavController(),
            state = DetailScreenState(
                task = Task(
                    title = "Test task",
                    description = "Test description",
                    createdAt = System.currentTimeMillis(),
                    priority = Task.Priority.HIGH,
                    isCompleted = true,
                    isDeleted = false
                )
            ),
            onEvent = {}
        )
    }
}
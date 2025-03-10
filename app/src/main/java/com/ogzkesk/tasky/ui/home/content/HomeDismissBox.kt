package com.ogzkesk.tasky.ui.home.content

import android.content.Context
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ogzkesk.domain.model.Task
import com.ogzkesk.tasky.R
import com.ogzkesk.tasky.ui.home.HomeScreenEvent
import com.ogzkesk.ui.composable.DismissBoxLayout
import kotlinx.coroutines.launch

@Composable
fun LazyItemScope.HomeDismissBox(
    modifier: Modifier = Modifier,
    task: Task,
    snackbarHostState: SnackbarHostState,
    onEvent: (HomeScreenEvent) -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    DismissBoxLayout(
        modifier = modifier.animateItem(
            fadeInSpec = tween(200),
            fadeOutSpec = tween(200),
        ),
        state = rememberSwipeToDismissBoxState(
            confirmValueChange = {
                return@rememberSwipeToDismissBoxState when (it) {

                    SwipeToDismissBoxValue.StartToEnd -> {
                        if (!task.isCompleted) {
                            coroutineScope.launch {
                                onEvent(HomeScreenEvent.CompleteTask(task))
                                showUndoSnackbar(
                                    context,
                                    snackbarHostState,
                                    context.getString(R.string.home_screen_completed_task_message),
                                ) {
                                    onEvent(
                                        HomeScreenEvent.UndoCompletedTask(task)
                                    )
                                }
                            }
                        } else {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    context.getString(R.string.home_screen_already_completed_message),
                                )
                            }
                        }
                        false
                    }

                    SwipeToDismissBoxValue.EndToStart -> {
                        coroutineScope.launch {
                            onEvent(HomeScreenEvent.RemoveTask(task))
                            showUndoSnackbar(
                                context,
                                snackbarHostState,
                                context.getString(R.string.home_screen_removed_task_message),
                            ) {
                                onEvent(HomeScreenEvent.UndoRemovedTask(task))
                            }
                        }
                        true
                    }

                    SwipeToDismissBoxValue.Settled -> false
                }
            }
        ),
        content = content
    )
}

private suspend fun showUndoSnackbar(
    context: Context,
    snackbarHostState: SnackbarHostState,
    message: String,
    onUndo: () -> Unit
) {
    val result = snackbarHostState.showSnackbar(
        message = message,
        duration = SnackbarDuration.Short,
        actionLabel = context.getString(R.string.home_screen_snackbar_undo_action),
    )
    if (result == SnackbarResult.ActionPerformed) {
        onUndo()
    }
}
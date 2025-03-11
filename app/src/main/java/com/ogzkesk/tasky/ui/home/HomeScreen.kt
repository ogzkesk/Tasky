package com.ogzkesk.tasky.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ogzkesk.database.util.getRelativeTimeSpanString
import com.ogzkesk.domain.ext.toLocalDateTime
import com.ogzkesk.domain.model.Task
import com.ogzkesk.tasky.R
import com.ogzkesk.tasky.navigation.CreationScreenRoute
import com.ogzkesk.tasky.navigation.DetailScreenRoute
import com.ogzkesk.tasky.navigation.SettingsScreenRoute
import com.ogzkesk.tasky.ui.home.content.HomeDismissBox
import com.ogzkesk.tasky.ui.home.content.HomeHeaderContent
import com.ogzkesk.tasky.ui.home.content.HomeMenu
import com.ogzkesk.tasky.ui.home.content.HomeSpotlight
import com.ogzkesk.tasky.ui.home.content.HomeTabRow
import com.ogzkesk.ui.composable.TaskyAlertDialog
import com.ogzkesk.ui.theme.ColorDate
import com.ogzkesk.ui.theme.ColorPriorityHigh
import com.ogzkesk.ui.theme.ColorPriorityLow
import com.ogzkesk.ui.theme.ColorPriorityMedium
import com.ogzkesk.ui.theme.TaskyTheme
import com.ogzkesk.ui.util.ThemedPreviews
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    state: HomeScreenState,
    onEvent: (HomeScreenEvent) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var spotlightBounds: Rect? by remember { mutableStateOf(null) }
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state) {
        if (state.navigateToSettings) {
            navController.navigate(SettingsScreenRoute)
            onEvent(HomeScreenEvent.NavigateToSettings(false))
        }
    }

    TaskyAlertDialog(
        enabled = state.clearDialogState,
        title = stringResource(R.string.home_screen_clear_dialog_title),
        message = stringResource(R.string.home_screen_clear_dialog_desc),
        onDismiss = { onEvent(HomeScreenEvent.ToggleClearDialog(false)) },
        onConfirm = {
            onEvent(HomeScreenEvent.ToggleClearDialog(false))
            onEvent(HomeScreenEvent.ClearTasks)
        }
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.home_screen_title))
                },
                scrollBehavior = topAppBarScrollBehavior,
                actions = {
                    HomeMenu(
                        state = state,
                        onEvent = onEvent,
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.onGloballyPositioned {
                    if (spotlightBounds == null) {
                        coroutineScope.launch {
                            delay(1000)
                            spotlightBounds = it.boundsInWindow()
                        }
                    }
                },
                onClick = dropUnlessResumed {
                    navController.navigate(CreationScreenRoute)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            HomeTabRow(
                selectedTab = state.selectedTab,
                onEvent = onEvent
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    bottom = paddingValues.calculateBottomPadding() + 16.dp,
                    start = 8.dp,
                    end = 8.dp
                )
            ) {
                item {
                    HomeHeaderContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        state = state,
                    )
                }

                items(
                    items = when (state.selectedTab) {
                        HomeScreenState.HomeTab.All -> state.tasks.orEmpty()
                        HomeScreenState.HomeTab.Pending -> state.pendingTasks.orEmpty()
                        HomeScreenState.HomeTab.Completed -> state.completedTasks.orEmpty()
                    },
                    key = { it.id }
                ) { task ->
                    HomeDismissBox(
                        task = task,
                        snackbarHostState = snackbarHostState,
                        onEvent = onEvent
                    ) {
                        TaskItem(
                            task = task,
                            onClick = dropUnlessResumed {
                                navController.navigate(DetailScreenRoute(task.id))
                            }
                        )
                    }
                }
            }
        }
    }

    AnimatedVisibility(
        visible = !state.isSpotlightShowed &&
                state.isTasksEmpty &&
                spotlightBounds != null,
        enter = fadeIn(tween(700)),
        exit = fadeOut(tween(700)),
        label = ""
    ) {
        spotlightBounds?.let {
            HomeSpotlight(
                bounds = it,
                onConfirm = {
                    onEvent(HomeScreenEvent.ToggleSpotlight(true))
                }
            )
        }
    }
}

@Composable
private fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 12.dp),
                    text = task.title,
                    style = MaterialTheme.typography.headlineSmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Text(
                    text = task.createdAt.getRelativeTimeSpanString(),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }

            Text(
                text = task.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Row(
                modifier = Modifier.align(Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val tint = when (task.priority) {
                        Task.Priority.HIGH -> ColorPriorityHigh
                        Task.Priority.MEDIUM -> ColorPriorityMedium
                        Task.Priority.LOW -> ColorPriorityLow
                    }

                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Outlined.Book,
                        contentDescription = "priority",
                        tint = tint
                    )
                    Text(
                        text = task.priority.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = tint
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "date",
                        tint = ColorDate
                    )
                    Text(
                        text = task.date
                            .toLocalDateTime()
                            .format(DateTimeFormatter.ISO_DATE),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = ColorDate
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
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
        HomeScreen(
            navController = rememberNavController(),
            state = HomeScreenState(
                tasks = listOf(
                    Task(
                        id = 1,
                        title = "Task 1",
                        description = "Description 1",
                        priority = Task.Priority.HIGH,
                        createdAt = System.currentTimeMillis(),
                        date = System.currentTimeMillis(),
                        isCompleted = false,
                    ),
                    Task(
                        id = 2,
                        title = "Task 2",
                        description = "Description 2",
                        priority = Task.Priority.MEDIUM,
                        createdAt = System.currentTimeMillis(),
                        date = System.currentTimeMillis(),
                        isCompleted = true
                    ),
                    Task(
                        id = 3,
                        title = "Task 3",
                        description = "Description 3",
                        priority = Task.Priority.LOW,
                        createdAt = System.currentTimeMillis(),
                        date = System.currentTimeMillis(),
                        isCompleted = true
                    )
                )
            ),
            onEvent = {}
        )
    }
}

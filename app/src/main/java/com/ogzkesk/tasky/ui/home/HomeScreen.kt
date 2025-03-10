package com.ogzkesk.tasky.ui.home

import android.text.format.DateUtils
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ogzkesk.domain.ext.toLocalDateTime
import com.ogzkesk.domain.model.Task
import com.ogzkesk.tasky.R
import com.ogzkesk.tasky.navigation.CreationScreenRoute
import com.ogzkesk.tasky.navigation.DetailScreenRoute
import com.ogzkesk.ui.composable.DismissBoxLayout
import com.ogzkesk.ui.theme.TaskyTheme
import com.ogzkesk.ui.theme.lowAlpha
import com.ogzkesk.ui.util.ThemedPreviews
import kotlinx.collections.immutable.ImmutableList
import java.time.format.DateTimeFormatter
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    state: HomeScreenState,
    onEvent: (HomeScreenEvent) -> Unit,
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.home_screen_title))
                },
                scrollBehavior = topAppBarScrollBehavior,
                actions = {
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
                    SortDropdown(
                        expanded = state.showDropdownMenu,
                        sortingMethod = state.sortingMethod,
                        onExpandChange = { onEvent(HomeScreenEvent.ToggleDropdownMenu(it)) },
                        onSort = { onEvent(HomeScreenEvent.OnSortMethodChanged(it)) }
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = dropUnlessResumed {
                    navController.navigate(CreationScreenRoute)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { paddingValues ->
        if (state.tasks == null) {
            EmptyTaskUI()
        } else {
            Column(
                modifier = Modifier.padding(
                    top = paddingValues.calculateTopPadding()
                )
            ) {
                TabRow(
                    selectedTabIndex = state.selectedTab.ordinal
                ) {
                    HomeScreenState.HomeTab.entries.forEach {
                        Tab(
                            selected = state.selectedTab == it,
                            onClick = {
                                onEvent(HomeScreenEvent.OnTabSelected(it))
                            },
                            text = {
                                Text(text = it.name)
                            }
                        )
                    }
                }

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
                    items(
                        items = when (state.selectedTab) {
                            HomeScreenState.HomeTab.All -> state.tasks
                            HomeScreenState.HomeTab.Pending -> state.pendingTasks.orEmpty()
                            HomeScreenState.HomeTab.Completed -> state.completedTasks.orEmpty()
                        },
                        key = { it.id }
                    ) { task ->
                        DismissBoxLayout(
                            modifier = Modifier.animateItem(
                                fadeInSpec = tween(200),
                                fadeOutSpec = tween(200),
                            ),
                            state = rememberSwipeToDismissBoxState(
                                confirmValueChange = {
                                    return@rememberSwipeToDismissBoxState when (it) {
                                        SwipeToDismissBoxValue.StartToEnd -> {
                                            if (!task.isCompleted) {
                                                onEvent(HomeScreenEvent.CompleteTask(task))
                                            }
                                            false
                                        }

                                        SwipeToDismissBoxValue.EndToStart -> {
                                            onEvent(HomeScreenEvent.RemoveTask(task))
                                            true
                                        }

                                        SwipeToDismissBoxValue.Settled -> false
                                    }
                                }
                            )
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
    }
}

@Composable
private fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    onClick: () -> Unit = {}
) {
    val feedback = LocalHapticFeedback.current

    Card(
        modifier = modifier,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.headlineSmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = task.description ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = stringResource(R.string.home_screen_priority, task.priority.toString()),
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = task.date.toLocalDateTime().format(DateTimeFormatter.ISO_DATE),
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = convertDateFormat(task.createdAt),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
            Icon(
                modifier = Modifier
                    .size(42.dp)
                    .clickable {
                        feedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    },
                imageVector = Icons.Default.Menu,
                contentDescription = "drag",
                tint = MaterialTheme.colorScheme.onBackground.lowAlpha
            )
        }
    }
}

@Composable
fun HeaderItem(
    modifier: Modifier = Modifier,
    tasks: ImmutableList<Task>,
) {

}

@Composable
fun EmptyTaskUI(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier,
            imageVector = Icons.Default.Add,
            contentDescription = "Add"
        )
        Text(
            text = "Empty",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
private fun SortDropdown(
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

private fun convertDateFormat(timeMillis: Long): String {
    val date = Date(timeMillis)
    return if (Date().time - date.time < 2 * DateUtils.MINUTE_IN_MILLIS) {
        "Just Now"
    } else {
        DateUtils.getRelativeTimeSpanString(
            date.time,
            Date().time,
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.FORMAT_SHOW_DATE
        ).toString()
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
                        id = 0,
                        title = "Task 1",
                        description = "Description 1",
                        priority = Task.Priority.HIGH,
                        createdAt = System.currentTimeMillis(),
                        date = System.currentTimeMillis(),
                        isCompleted = false,
                    ),
                    Task(
                        id = 0,
                        title = "Task 1",
                        description = "Description 1",
                        priority = Task.Priority.MEDIUM,
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

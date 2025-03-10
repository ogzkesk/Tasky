package com.ogzkesk.tasky.ui.home

import android.text.format.DateUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ogzkesk.domain.model.Task
import com.ogzkesk.tasky.R
import com.ogzkesk.tasky.navigation.CreationScreenRoute
import com.ogzkesk.tasky.navigation.DetailScreenRoute
import com.ogzkesk.ui.theme.TaskyTheme
import com.ogzkesk.ui.util.ThemedPreviews
import kotlinx.collections.immutable.ImmutableList
import java.util.Date
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.schedule

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
            LargeTopAppBar(
                title = {
                    Text(stringResource(R.string.home_screen_title))
                },
                expandedHeight = 120.dp,
                scrollBehavior = topAppBarScrollBehavior,
                actions = {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "menu"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + 16.dp,
                start = 8.dp,
                end = 8.dp
            )
        ) {
            items(
                items = state.tasks ?: emptyList(),
            ) { task ->
                TaskItem(
                    task = task,
                    onClick = { id ->
                        navController.navigate(DetailScreenRoute(id))
                    }
                )
            }
        }
    }
}

@Composable
private fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    onClick: (id: Long) -> Unit = {}
) {
    Card(
        modifier = modifier,
        onClick = {
            onClick(task.id)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = task.description ?: "",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                modifier = Modifier.align(Alignment.End),
                text = convertDateFormat(task.createdAt),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.primary
                )
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

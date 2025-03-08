package com.ogzkesk.tasky.ui.home

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ogzkesk.domain.ext.toLocalDateTime
import com.ogzkesk.domain.model.Task
import com.ogzkesk.tasky.R
import com.ogzkesk.tasky.navigation.CreationScreenRoute
import com.ogzkesk.tasky.navigation.DetailScreenRoute
import com.ogzkesk.ui.theme.TaskyTheme
import com.ogzkesk.ui.util.ThemedPreviews
import kotlinx.collections.immutable.ImmutableList
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    state: HomeScreenState,
    onEvent: (HomeScreenEvent) -> Unit,
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

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
                        onClick = {}
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
                top = paddingValues.calculateTopPadding() + 16.dp,
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
                    onClick = {
                        navController.navigate(DetailScreenRoute)
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
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        border = BorderStroke(
            width = 1.dp,
            color = Color(task.priority.colorHex)
        )
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
                text = task.date
                    .toLocalDateTime()
                    .format(DateTimeFormatter.ISO_DATE),
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

@ThemedPreviews
@Composable
private fun HomeScreenPreview() {
    TaskyTheme {
        HomeScreen(
            navController = rememberNavController(),
            state = HomeScreenState(
                tasks = mockTasks
            ),
            onEvent = {}
        )
    }
}

val mockTasks = listOf(
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.HIGH,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.MEDIUM,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.LOW,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = null,
        priority = Task.Priority.HIGH,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.HIGH,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.LOW,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.MEDIUM,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.HIGH,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.HIGH,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.MEDIUM,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.HIGH,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.MEDIUM,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.HIGH,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.LOW,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.HIGH,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.MEDIUM,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.HIGH,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.HIGH,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.HIGH,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.HIGH,
        date = System.currentTimeMillis()
    ),
    Task(
        id = 0,
        title = "Task 1",
        description = "Description 1",
        priority = Task.Priority.HIGH,
        date = System.currentTimeMillis()
    ),
)
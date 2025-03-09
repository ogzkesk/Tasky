package com.ogzkesk.tasky.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ogzkesk.domain.ext.toLocalDateTime
import com.ogzkesk.tasky.R
import com.ogzkesk.ui.composable.TaskyTopBar
import com.ogzkesk.ui.theme.TaskyTheme
import com.ogzkesk.ui.util.ThemedPreviews
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
    state: DetailScreenState,
    onEvent: (DetailScreenEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TaskyTopBar(
                title = stringResource(R.string.detail_screen_title),
                onBack = navController::popBackStack
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
                    style = MaterialTheme.typography.titleLarge
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

@ThemedPreviews
@Composable
private fun HomeScreenPreview() {
    TaskyTheme {
        DetailScreen(
            navController = rememberNavController(),
            state = DetailScreenState(),
            onEvent = {}
        )
    }
}
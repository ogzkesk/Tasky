package com.ogzkesk.tasky.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.PriorityHigh
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ogzkesk.database.util.getRelativeTimeSpanString
import com.ogzkesk.domain.ext.toLocalDateTime
import com.ogzkesk.domain.model.Task
import com.ogzkesk.tasky.R
import com.ogzkesk.ui.composable.TaskyAlertDialog
import com.ogzkesk.ui.composable.TaskyTopBar
import com.ogzkesk.ui.theme.ColorDate
import com.ogzkesk.ui.theme.ColorPriorityHigh
import com.ogzkesk.ui.theme.ColorPriorityLow
import com.ogzkesk.ui.theme.ColorPriorityMedium
import com.ogzkesk.ui.theme.TaskyTheme
import com.ogzkesk.ui.theme.semiBold
import com.ogzkesk.ui.util.ThemedPreviews
import java.time.format.DateTimeFormatter

class CircularRevealShape(
    private val center: Offset,
    private val radius: Float
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            addOval(
                Rect(
                    center = center,
                    radius = radius
                )
            )
        }

        val rectPath = Path().apply {
            addRect(Rect(Offset.Zero, size))
        }

        val finalPath = Path().apply {
            op(rectPath, path, PathOperation.Intersect)
        }

        return Outline.Generic(finalPath)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
    state: DetailScreenState,
    onEvent: (DetailScreenEvent) -> Unit,
) {
    LaunchedEffect(state) {
        if (state.isDeleted) navController.popBackStack()
    }

    TaskyAlertDialog(
        enabled = state.showTrashDialog,
        title = stringResource(R.string.detail_screen_delete_dialog_title),
        message = stringResource(R.string.detail_screen_delete_dialog_message),
        onConfirm = dropUnlessResumed {
            onEvent(DetailScreenEvent.ToggleTrashDialog(false))
            onEvent(DetailScreenEvent.DeleteTask)
        },
        onDismiss = {
            onEvent(DetailScreenEvent.ToggleTrashDialog(false))
        }
    )

    Scaffold(
        topBar = {
            TaskyTopBar(
                title = stringResource(R.string.detail_screen_title),
                onBack = dropUnlessResumed {
                    navController.popBackStack()
                },
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(DetailScreenEvent.ToggleTrashDialog(true))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "delete"
                        )
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            state.task?.let { task ->
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.headlineMedium.semiBold
                )

                Text(
                    modifier = Modifier.padding(bottom = 24.dp),
                    text = task.description.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium
                )

                HorizontalDivider()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
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
                            imageVector = Icons.Outlined.PriorityHigh,
                            contentDescription = "priority",
                        )
                        Text(
                            text = buildAnnotatedString {
                                append(stringResource(R.string.detail_screen_priority_title))
                                withStyle(SpanStyle(color = tint)) {
                                    append(" ")
                                    append(task.priority.toString())
                                }
                            },
                            style = MaterialTheme.typography.bodyLarge.semiBold,
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
                        )
                        Text(
                            text = buildAnnotatedString {
                                append(stringResource(R.string.detail_screen_date_title))
                                withStyle(SpanStyle(color = ColorDate)) {
                                    append("  ")
                                    append(
                                        task.date
                                            .toLocalDateTime()
                                            .format(DateTimeFormatter.ISO_DATE)
                                    )
                                }
                            },
                            style = MaterialTheme.typography.bodyLarge.semiBold,
                        )
                    }
                }

                HorizontalDivider()

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.detail_screen_completed_checkbox_text),
                        style = MaterialTheme.typography.bodyLarge.semiBold,
                    )
                    Checkbox(
                        checked = task.isCompleted,
                        onCheckedChange = {
                            onEvent(DetailScreenEvent.ToggleTaskCompleted(it))
                        }
                    )
                    Spacer(modifier = Modifier.weight(1F))
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.detail_screen_created_at_title))
                            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                append(" ")
                                append(task.createdAt.getRelativeTimeSpanString())
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium.semiBold.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
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
                    date = System.currentTimeMillis()
                )
            ),
            onEvent = {}
        )
    }
}
package com.ogzkesk.tasky.ui.creation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ogzkesk.domain.model.Task
import com.ogzkesk.tasky.R
import com.ogzkesk.ui.composable.TaskyTopBar
import com.ogzkesk.ui.theme.TaskyTheme
import com.ogzkesk.ui.theme.halfAlpha
import com.ogzkesk.ui.theme.placeholder
import com.ogzkesk.ui.theme.semiBold
import com.ogzkesk.ui.util.ThemedPreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreationScreen(
    navController: NavHostController,
    state: CreationScreenState,
    onEvent: (CreationScreenEvent) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val titleFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        titleFocusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            TaskyTopBar(
                title = stringResource(R.string.creation_screen_title),
                onBack = navController::popBackStack
            )
        },
        bottomBar = {
            BottomAppBar(
                windowInsets = WindowInsets.ime
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                        focusManager.clearFocus()
                        onEvent(
                            CreationScreenEvent.OnCreate {
                                navController.popBackStack()
                            }
                        )
                    }
                ) {
                    Text(stringResource(R.string.creation_action_save_button_text))
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(vertical = 16.dp, horizontal = 8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(titleFocusRequester),
                value = state.task.title,
                singleLine = true,
                maxLines = 1,
                onValueChange = {
                    onEvent(CreationScreenEvent.TitleTextChangedEvent(it))
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.creation_title_field_placeholder),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.placeholder
                        )
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.creation_title_field_label),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                value = state.task.description.orEmpty(),
                singleLine = false,
                onValueChange = {
                    onEvent(CreationScreenEvent.DescriptionTextChangedEvent(it))
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.creation_description_field_placeholder),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.placeholder
                        )
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.creation_description_field_label),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                }
            )

            Column(
                modifier = Modifier.padding(top = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.creation_priority_title),
                    style = MaterialTheme.typography.bodyMedium.semiBold
                )
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Task.Priority.entries.forEach { priority ->
                        SegmentedButton(
                            selected = priority == state.task.priority,
                            shape = when (priority) {
                                Task.Priority.LOW -> RoundedCornerShape(
                                    topStart = MaterialTheme.shapes.medium.topStart,
                                    bottomStart = MaterialTheme.shapes.medium.bottomStart,
                                    topEnd = ZeroCornerSize,
                                    bottomEnd = ZeroCornerSize
                                )

                                Task.Priority.MEDIUM -> RoundedCornerShape(0.dp)

                                Task.Priority.HIGH -> RoundedCornerShape(
                                    topStart = ZeroCornerSize,
                                    bottomStart = ZeroCornerSize,
                                    topEnd = MaterialTheme.shapes.medium.topEnd,
                                    bottomEnd = MaterialTheme.shapes.medium.bottomEnd
                                )
                            },
                            onClick = {
                                onEvent(CreationScreenEvent.PriorityChangedEvent(priority))
                            },
                            label = {
                                Text(text = priority.name)
                            }
                        )
                    }
                }
            }
        }
    }
}

@ThemedPreviews
@Composable
fun CreationScreenPreview() {
    TaskyTheme {
        CreationScreen(
            navController = rememberNavController(),
            state = CreationScreenState(),
            onEvent = {}
        )
    }
}
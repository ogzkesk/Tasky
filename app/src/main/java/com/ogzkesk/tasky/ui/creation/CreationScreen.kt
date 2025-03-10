package com.ogzkesk.tasky.ui.creation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ogzkesk.domain.model.Task
import com.ogzkesk.tasky.R
import com.ogzkesk.ui.composable.TaskyDatePickerField
import com.ogzkesk.ui.composable.TaskyTextField
import com.ogzkesk.ui.composable.TaskyTopBar
import com.ogzkesk.ui.theme.TaskyTheme
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
    val dateFocusRequester = remember { FocusRequester() }

    val popBack = dropUnlessResumed {
        navController.popBackStack()
    }

    LaunchedEffect(Unit) {
        titleFocusRequester.requestFocus()
    }

    LaunchedEffect(state) {
        if (state.titleFieldError) {
            titleFocusRequester.requestFocus()
        }
        if (state.dateFieldError) {
            dateFocusRequester.requestFocus()
        }
    }

    Scaffold(
        topBar = {
            TaskyTopBar(
                title = stringResource(R.string.creation_screen_title),
                onBack = popBack
            )
        },
        bottomBar = {
            BottomAppBar(
                windowInsets = WindowInsets.safeContent.only(WindowInsetsSides.Bottom)
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
                                popBack()
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
            TaskyTextField(
                value = state.task.title,
                onValueChange = {
                    onEvent(CreationScreenEvent.TitleTextChangedEvent(it))
                },
                focusRequester = titleFocusRequester,
                isError = state.titleFieldError,
                placeHolder = stringResource(R.string.creation_title_field_placeholder),
                label = stringResource(R.string.creation_title_field_label),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )

            TaskyTextField(
                modifier = Modifier.height(200.dp),
                value = state.task.description.orEmpty(),
                singleLine = false,
                onValueChange = {
                    onEvent(CreationScreenEvent.DescriptionTextChangedEvent(it))
                },
                placeHolder = stringResource(R.string.creation_description_field_placeholder),
                label = stringResource(R.string.creation_description_field_label),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions {
                    dateFocusRequester.requestFocus()
                }
            )

            TaskyDatePickerField(
                modifier = Modifier.fillMaxWidth(),
                focusRequester = dateFocusRequester,
                date = state.task.date,
                isError = state.dateFieldError,
                onDateSelected = {
                    onEvent(CreationScreenEvent.OnDateSelected(it))
                }
            )

            PriorityButtonRow(
                priority = state.task.priority,
                onClick = { priority ->
                    onEvent(CreationScreenEvent.PriorityChangedEvent(priority))
                }
            )
        }
    }
}

@Composable
private fun PriorityButtonRow(
    priority: Task.Priority,
    onClick: (Task.Priority) -> Unit
) {
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
            Task.Priority.entries.forEach {
                SegmentedButton(
                    selected = it == priority,
                    shape = when (it) {
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
                        onClick(it)
                    },
                    label = {
                        Text(text = it.name)
                    }
                )
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
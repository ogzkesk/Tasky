package com.ogzkesk.template.ui.first

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ogzkesk.template.R
import com.ogzkesk.ui.composable.CustomTopBar
import com.ogzkesk.ui.resource.AlertDialogData
import com.ogzkesk.ui.resource.BottomSheetData
import kotlinx.coroutines.launch

@Suppress("MaxLineLength")
@Composable
fun FirstScreen(viewModel: FirstViewModel = hiltViewModel()) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CustomTopBar(title = stringResource(R.string.app_name))
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            Column {
                Button(onClick = {
                    coroutineScope.launch {
                        viewModel.updateBottomSheet(
                            BottomSheetData.Data1(
                                message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry",
                                onConfirm = {},
                            ),
                        )
                    }
                }) {
                    Text(text = "Show test BottomSheet")
                }

                Button(onClick = {
                    coroutineScope.launch {
                        viewModel.updateDialog(
                            AlertDialogData(
                                message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                                title = "A Short Title Is Best",
                            ),
                        )
                    }
                }) {
                    Text(text = "Alert Dialog")
                }

                Button(onClick = viewModel::showThemeBottomSheet) {
                    Text(text = "Theme Dialog")
                }

                Button(onClick = viewModel::navigateToSecondScreen) {
                    Text(text = "Navigate Second Screen")
                }
            }
        }
    }
}

package com.ogzkesk.template.ui.sheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.ogzkesk.ui.composable.CustomBottomSheet
import com.ogzkesk.ui.resource.BottomSheetData
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContainer(
    modifier: Modifier,
    data: BottomSheetData?,
    onDismiss: () -> Unit,
) {
    data?.let {
        val coroutineScope = rememberCoroutineScope()
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
        )

        CustomBottomSheet(
            sheetState = sheetState,
            onDismissBottomSheet = {
                coroutineScope.launch {
                    onDismiss()
                    sheetState.hide()
                }
            },
        ) {
            Surface(
                modifier = modifier,
                color = MaterialTheme.colorScheme.surfaceVariant,
            ) {
                when (data) {
                    is BottomSheetData.Data1 ->
                        FirstBottomSheet(
                            bottomSheetData = data,
                            onDismiss = onDismiss,
                        )

                    is BottomSheetData.ThemeData -> {
                        ThemeBottomSheet(sheetData = data)
                    }
                }
            }
        }
    }
}

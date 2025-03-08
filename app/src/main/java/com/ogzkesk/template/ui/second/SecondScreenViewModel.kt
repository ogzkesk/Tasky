package com.ogzkesk.template.ui.second

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.ogzkesk.template.navigation.SecondScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecondScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val item: String = savedStateHandle.toRoute<SecondScreenRoute>().arg
}

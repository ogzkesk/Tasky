package com.ogzkesk.template.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ogzkesk.domain.prefs.PreferencesRepository
import com.ogzkesk.domain.prefs.model.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    preferencesRepository: PreferencesRepository,
) : ViewModel() {
    val theme: StateFlow<Theme> = preferencesRepository.getPreferences()
        .map { it.theme }
        .stateIn(viewModelScope, SharingStarted.Eagerly, Theme.SYSTEM)
}

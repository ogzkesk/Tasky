package com.ogzkesk.template.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ogzkesk.template.navigation.AppContentRepository
import com.ogzkesk.template.navigation.FirstScreenRoute
import com.ogzkesk.template.navigation.NavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appContentRepository: AppContentRepository,
) : ViewModel() {
    @Suppress("MagicNumber")
    fun start() {
        viewModelScope.launch {
            delay(3000)
            appContentRepository.updateNavigation(
                NavigationEvent.NavigateAndPop(FirstScreenRoute),
            )
        }
    }
}

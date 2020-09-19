package com.smarttoolfactory.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smarttoolfactory.domain.usecase.property.GetDashboardStatsUseCase
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope

class DashboardViewModelFactory @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val dashboardStatsUseCase: GetDashboardStatsUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(coroutineScope, dashboardStatsUseCase) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

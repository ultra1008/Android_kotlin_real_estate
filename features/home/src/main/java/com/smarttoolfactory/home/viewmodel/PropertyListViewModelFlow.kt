package com.smarttoolfactory.home.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.smarttoolfactory.core.util.Event
import com.smarttoolfactory.core.util.convertToFlowViewState
import com.smarttoolfactory.core.viewstate.Status
import com.smarttoolfactory.core.viewstate.ViewState
import com.smarttoolfactory.domain.model.PropertyItem
import com.smarttoolfactory.domain.usecase.GetPropertiesUseCaseFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class PropertyListViewModelFlow @ViewModelInject constructor(
    private val coroutineScope: CoroutineScope,
    private val getPropertiesUseCase: GetPropertiesUseCaseFlow
) : AbstractPropertyListVM() {

    private val _goToDetailScreen = MutableLiveData<Event<PropertyItem>>()

    override val goToDetailScreen: LiveData<Event<PropertyItem>>
        get() = _goToDetailScreen

    private val _propertyViewState = MutableLiveData<ViewState<List<PropertyItem>>>()

    override val propertyListViewState: LiveData<ViewState<List<PropertyItem>>>
        get() = _propertyViewState

    /**
     * Function to retrieve data from repository with offline-first which checks
     * local data source first.
     *
     * * Check out Local Source first
     * * If empty data or null returned throw empty set exception
     * * If error occurred while fetching data from remote: Try to fetch data from db
     * * If data is fetched from remote source: delete old data, save new data and return new data
     * * If both network and db don't have any data throw empty set exception
     *
     */
    override fun getPropertyList() {

        getPropertiesUseCase.getPropertiesOfflineFirst("")
            .convertToFlowViewState()
            .onStart {
                _propertyViewState.value = ViewState(status = Status.LOADING)
            }
            .onEach {
                _propertyViewState.value = it
            }
            .launchIn(coroutineScope)
    }

    override fun refreshPropertyList() {
        getPropertiesUseCase.getPropertiesOfflineLast("")
            .convertToFlowViewState()
            .onStart {
                _propertyViewState.value = ViewState(status = Status.LOADING)
            }
            .onEach {
                _propertyViewState.value = it
            }
            .launchIn(coroutineScope)
    }

    override fun onClick(item: PropertyItem) {
        _goToDetailScreen.value = Event(item)
    }
}

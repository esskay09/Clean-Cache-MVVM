package com.terranullius.clean_cache_mvvm.framework.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terranullius.clean_cache_mvvm.business.domain.model.DataState
import com.terranullius.clean_cache_mvvm.business.domain.state.StateResource
import com.terranullius.clean_cache_mvvm.business.interactors.common.UserInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userInteractors: UserInteractors
) : ViewModel() {

    private val _viewState: MutableStateFlow<StateResource<DataState>> =
        MutableStateFlow(StateResource.Loading)
    val viewState: StateFlow<StateResource<DataState>>
        get() = _viewState

    private val allUsersStateFlow: MutableStateFlow<StateResource<DataState>> =
        MutableStateFlow(StateResource.Loading)
    private val savedUserStateFlow: MutableStateFlow<StateResource<DataState>> =
        MutableStateFlow(StateResource.Loading)

}
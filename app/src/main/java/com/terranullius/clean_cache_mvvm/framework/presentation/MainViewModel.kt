package com.terranullius.clean_cache_mvvm.framework.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terranullius.clean_cache_mvvm.business.domain.model.DataState
import com.terranullius.clean_cache_mvvm.business.domain.model.User
import com.terranullius.clean_cache_mvvm.business.domain.state.StateResource
import com.terranullius.clean_cache_mvvm.business.interactors.UserInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
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

    init {
        getUsers()
        getSavedUsers()
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            userInteractors.insertUser(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            userInteractors.deleteUser(user)
        }
    }

    fun getSavedUsers() {
        viewModelScope.launch {
            userInteractors.getSavedUsers().collectLatest {
                savedUserStateFlow.value = it
            }
        }
    }

    fun getUsers(page: Int = 1) {
        viewModelScope.launch {
            userInteractors.getUsers(page).collectLatest {
                allUsersStateFlow.value = it
            }
        }
    }


}
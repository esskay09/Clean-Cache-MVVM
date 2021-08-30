package com.terranullius.clean_cache_mvvm.framework.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terranullius.clean_cache_mvvm.business.domain.model.DataState
import com.terranullius.clean_cache_mvvm.business.domain.model.User
import com.terranullius.clean_cache_mvvm.business.domain.state.StateResource
import com.terranullius.clean_cache_mvvm.business.interactors.UserInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    private val _savedUserStateFlow: MutableStateFlow<StateResource<DataState>> =
        MutableStateFlow(StateResource.Loading)

    val savedUserStateFlow: StateFlow<StateResource<DataState>>
        get() = _savedUserStateFlow

    init {
        getUsers()
        getSavedUsers()
        viewModelScope.launch {
            combineApiAndCacheResponse().collectLatest {
                _viewState.value = it
            }
        }
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
                _savedUserStateFlow.value = it
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

    private fun combineApiAndCacheResponse(): Flow<StateResource<DataState>> {
        return combine(_savedUserStateFlow, allUsersStateFlow) { savedUsers, allUsers ->
            if (savedUsers is StateResource.Error || allUsers is StateResource.Error) {
                StateResource.Error(message = "Error from savedUsersFlow or allUsersFlow")
            } else if (savedUsers is StateResource.Success && allUsers is StateResource.Success) {

                val userListFromApi = allUsers.data.userList
                val userListFromCache = savedUsers.data.userList

                val updatedListWithCache = userListFromApi.map {
                    if (userListFromCache.contains(it)) {
                        val user = it.copy(cached = true)
                        user
                    } else {
                        val user = it.copy(cached = false)
                        user
                    }
                }
                val dataState = DataState(
                    currentPage = allUsers.data.currentPage,
                    userList = updatedListWithCache,
                    pageCount = allUsers.data.pageCount
                )


                StateResource.Success(dataState)
            } else {
                StateResource.Loading
            }
        }
    }
}
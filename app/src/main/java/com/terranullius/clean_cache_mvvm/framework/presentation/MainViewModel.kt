package com.terranullius.clean_cache_mvvm.framework.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
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

private const val PAGE_LIMIT = 20

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

    private val _pagedUserFlow: MutableStateFlow<List<User>> =
        MutableStateFlow(listOf())
    val pagedUserList: StateFlow<List<User>>
        get() = _pagedUserFlow

    private var userListScrollPosition = 0
    private var currentPage = mutableStateOf(1)

    init {
        getUsers()
        viewModelScope.launch {
            combineApiAndCacheResponse().collectLatest {
                if (it is StateResource.Success) refreshPagedList(it.data.userList)
                _viewState.value = it
            }
        }
        viewModelScope.launch {
            getSavedUsers()
        }
    }

    fun setScrollPosition(position: Int) {
        userListScrollPosition = position
    }


    fun insertUser(user: User) {
        viewModelScope.launch {
            userInteractors.insertUser(user).collectLatest {
                if (it is StateResource.Success) getSavedUsers()
            }
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            userInteractors.deleteUser(user).collectLatest {
                if (it is StateResource.Success) getSavedUsers()
            }
        }

    }

    suspend fun getSavedUsers() {
        userInteractors.getSavedUsers().map { stateRes ->
            if (stateRes is StateResource.Success) {
                val userList = stateRes.data.userList.map { user ->
                    user.copy(cached = true)
                }
                val dataState = stateRes.data.copy(userList = userList)
                StateResource.Success(dataState)
            } else stateRes
        }.collectLatest {
            _savedUserStateFlow.value = it
        }
    }


    private fun getUsers(page: Int = 1) {
        viewModelScope.launch {
            userInteractors.getUsers(page).collectLatest {
                allUsersStateFlow.value = it
            }
        }
    }

    fun nextPage() {
        if (userListScrollPosition + 1 >= PAGE_LIMIT * currentPage.value)
            if (_viewState.value is StateResource.Success) {
                currentPage.value = currentPage.value + 1
                getUsers(currentPage.value)
            }
    }

    private fun refreshPagedList(newList: List<User>) {
        val oldList = ArrayList(_pagedUserFlow.value)

        newList.forEach { newUser ->
            oldList.find { oldUser ->
                oldUser.id == newUser.id
            }?.let {
                oldList[oldList.indexOf(it)] = newUser
            } ?: oldList.add(newUser)
        }

        _pagedUserFlow.value = oldList
    }

    private fun combineApiAndCacheResponse(): Flow<StateResource<DataState>> {
        return combine(_savedUserStateFlow, allUsersStateFlow) { savedUsers, allUsers ->
            if (savedUsers is StateResource.Error || allUsers is StateResource.Error) {
                StateResource.Error(message = "Error from savedUsersFlow or allUsersFlow")
            } else if (savedUsers is StateResource.Success && allUsers is StateResource.Success) {

                val userListFromApi = allUsers.data.userList
                val userIdListFromCache = savedUsers.data.userList.map { it.id }

                val updatedListWithCache = userListFromApi.map { userApi ->
                    if (userIdListFromCache.contains(userApi.id)) {
                        val user = userApi.copy(cached = true)
                        user
                    } else {
                        val user = userApi.copy(cached = false)
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
package com.terranullius.clean_cache_mvvm.framework.presentation.users.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.terranullius.clean_cache_mvvm.business.domain.model.DataState
import com.terranullius.clean_cache_mvvm.business.domain.model.User
import com.terranullius.clean_cache_mvvm.business.domain.state.StateResource
import com.terranullius.clean_cache_mvvm.framework.presentation.MainViewModel
import com.terranullius.clean_cache_mvvm.framework.presentation.composables.ErrorComposable
import com.terranullius.clean_cache_mvvm.framework.presentation.composables.LoadingComposable
import com.terranullius.clean_cache_mvvm.framework.presentation.composables.UserItem

@Composable
fun AllUserComposable(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: MainViewModel
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    )
    {
        val viewState = viewModel.viewState.collectAsState()

        when (viewState.value) {
            is StateResource.Loading -> {
                LoadingComposable()
            }
            is StateResource.Error -> {
                ErrorComposable()
            }
            is StateResource.Success -> {
                val userList = (viewState.value as StateResource.Success<DataState>).data.userList
                UserColumn(userList = userList, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun UserColumn(
    modifier: Modifier = Modifier.fillMaxSize(),
    userList: List<User>,
    viewModel: MainViewModel
) {

    LazyColumn(
        modifier = modifier,
    ) {
        items(userList) { user ->

            UserItem(user){ isChecked ->
                onCheckChange(
                    isChecked = isChecked,
                    user = user,
                    viewModel = viewModel
                )
            }
        }
    }
}


fun onCheckChange(
    isChecked: Boolean,
    user: User,
    viewModel: MainViewModel
) {
    if (isChecked) viewModel.insertUser(
        user
    ) else viewModel.deleteUser(user)

}

package com.terranullius.clean_cache_mvvm.framework.presentation.users.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.terranullius.clean_cache_mvvm.business.domain.model.DataState
import com.terranullius.clean_cache_mvvm.business.domain.model.User
import com.terranullius.clean_cache_mvvm.business.domain.state.StateResource
import com.terranullius.clean_cache_mvvm.framework.presentation.MainViewModel
import com.terranullius.clean_cache_mvvm.framework.presentation.composables.ErrorComposable
import com.terranullius.clean_cache_mvvm.framework.presentation.composables.LoadingComposable
import com.terranullius.clean_cache_mvvm.framework.presentation.composables.UserItem
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
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
        val viewState =
            viewModel.viewState.debounce(50).collectAsState(initial = StateResource.Loading)

        val lazyListState = rememberLazyListState()

        when (viewState.value) {
            is StateResource.Loading -> {
                LoadingComposable()
            }
            is StateResource.Error -> {
                ErrorComposable()
            }
            is StateResource.Success -> {
                val userList = viewModel.pagedUserList.collectAsState().value

                if (lazyListState.isScrolledToTheEnd()){
                    viewModel.nextPage()
                }

                UserColumn(
                    userList = userList,
                    viewModel = viewModel,
                    lazyListState = lazyListState
                )
            }
        }
    }
}

@Composable
fun UserColumn(
    modifier: Modifier = Modifier.fillMaxSize(),
    userList: List<User>,
    lazyListState: LazyListState,
    viewModel: MainViewModel,
) {

    LazyColumn(
        modifier = modifier,
        state = lazyListState
    ) {
        itemsIndexed(userList) { index, user ->

            viewModel.setScrollPosition(index)

            UserItem(user) { isChecked ->
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

private fun LazyListState.isScrolledToTheEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

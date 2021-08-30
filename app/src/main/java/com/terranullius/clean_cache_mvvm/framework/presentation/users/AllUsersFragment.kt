package com.terranullius.clean_cache_mvvm.framework.presentation.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.terranullius.clean_cache_mvvm.framework.presentation.MainViewModel
import com.terranullius.clean_cache_mvvm.framework.presentation.composables.MyApp
import com.terranullius.clean_cache_mvvm.framework.presentation.composables.theme.mainPadding
import com.terranullius.clean_cache_mvvm.framework.presentation.users.composables.AllUserComposable

class AllUsersFragment : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MyApp {
                    AllUserComposable(
                        viewModel = viewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                mainPadding
                            )
                    )
                }
            }
        }
    }
}
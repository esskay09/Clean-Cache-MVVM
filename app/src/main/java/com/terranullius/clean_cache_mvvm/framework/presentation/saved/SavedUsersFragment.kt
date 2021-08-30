package com.terranullius.clean_cache_mvvm.framework.presentation.saved

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
import com.terranullius.clean_cache_mvvm.framework.presentation.composables.theme.mainPadding
import com.terranullius.clean_cache_mvvm.framework.presentation.saved.composables.SavedUsersComposable

class SavedUsersFragment: Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SavedUsersComposable(
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
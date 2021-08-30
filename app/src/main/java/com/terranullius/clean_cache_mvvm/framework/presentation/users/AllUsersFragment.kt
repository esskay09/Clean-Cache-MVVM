package com.terranullius.clean_cache_mvvm.framework.presentation.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.terranullius.clean_cache_mvvm.framework.presentation.saved.composables.SavedUsersComposable

class AllUsersFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SavedUsersComposable()
            }
        }
    }
}
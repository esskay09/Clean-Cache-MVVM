package com.terranullius.clean_cache_mvvm.framework.presentation.composables

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.terranullius.clean_cache_mvvm.framework.presentation.composables.theme.CleanCacheTheme

@Composable
fun MyApp(
    content: @Composable () -> Unit
) {
    CleanCacheTheme() {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            content()
        }
    }
}
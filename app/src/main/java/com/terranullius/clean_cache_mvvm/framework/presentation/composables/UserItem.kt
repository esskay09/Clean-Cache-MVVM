package com.terranullius.clean_cache_mvvm.framework.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.terranullius.clean_cache_mvvm.business.domain.model.User

@Composable
fun UserItem(
    user: User,
    onCheckedChange : (Boolean) -> Unit = {}
) {
    var isChecked by remember {
        mutableStateOf(user.cached)
    }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = user.email)
        Checkbox(checked = isChecked, onCheckedChange = {
            isChecked = it
            onCheckedChange(it)
        })
    }
}
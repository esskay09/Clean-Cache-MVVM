package com.terranullius.clean_cache_mvvm.framework.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.terranullius.clean_cache_mvvm.business.domain.model.User

@Composable
fun UserItem(
    user: User,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    var isChecked by remember {
        mutableStateOf(user.cached)
    }

    Column() {
        Card(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            elevation = 4.dp,
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = user.name, style = MaterialTheme.typography.h6)

                IconButton(onClick = {
                    isChecked = !isChecked
                    onCheckedChange(isChecked)
                }) {
                    val starColor = if (isChecked) MaterialTheme.colors.secondary else MaterialTheme.colors.onBackground
                    Icon(Icons.Default.Star, "star", tint = starColor)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
    
}
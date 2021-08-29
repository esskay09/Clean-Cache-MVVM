package com.terranullius.clean_cache_mvvm.framework.datasource.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_users")
data class UserCacheEntity(

    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val email: String
)

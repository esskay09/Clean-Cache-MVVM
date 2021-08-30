package com.terranullius.clean_cache_mvvm.business.data.cache.abstraction

import com.terranullius.clean_cache_mvvm.business.domain.model.DataState
import com.terranullius.clean_cache_mvvm.business.domain.model.User

interface UserCacheDataSource {

    suspend fun insertUser(user: User): Long

    suspend fun deleteUser(user: User): Int

    suspend fun getSavedUsers(): DataState
}
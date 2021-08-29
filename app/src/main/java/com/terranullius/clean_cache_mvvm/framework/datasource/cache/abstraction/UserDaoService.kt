package com.terranullius.clean_cache_mvvm.framework.datasource.cache.abstraction

import com.terranullius.clean_cache_mvvm.business.domain.model.User

interface UserDaoService {

    suspend fun getSavedUsers(): List<User>

    suspend fun insertUser(user: User): Long

    suspend fun deleteUser(user: User): Long
}
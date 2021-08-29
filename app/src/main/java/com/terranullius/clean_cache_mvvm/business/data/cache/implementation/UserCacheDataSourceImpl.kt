package com.terranullius.clean_cache_mvvm.business.data.cache.implementation

import com.terranullius.clean_cache_mvvm.business.data.cache.abstraction.UserCacheDataSource
import com.terranullius.clean_cache_mvvm.business.domain.model.User
import com.terranullius.clean_cache_mvvm.framework.datasource.cache.abstraction.UserDaoService
import javax.inject.Inject

class UserCacheDataSourceImpl @Inject constructor(
    private val userDaoService: UserDaoService
) : UserCacheDataSource {

    override suspend fun insertUser(user: User) = userDaoService.insertUser(user)

    override suspend fun deleteUser(user: User) = userDaoService.deleteUser(user)

    override suspend fun getSavedUsers() = userDaoService.getSavedUsers()
}
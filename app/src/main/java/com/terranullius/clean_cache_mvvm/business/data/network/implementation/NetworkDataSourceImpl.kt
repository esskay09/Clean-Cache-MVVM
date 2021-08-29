package com.terranullius.clean_cache_mvvm.business.data.network.implementation

import com.terranullius.clean_cache_mvvm.business.data.network.abstraction.NetworkDataSource
import com.terranullius.clean_cache_mvvm.framework.datasource.network.abstraction.UserApiService
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    private val userApiService: UserApiService
) : NetworkDataSource {

    override suspend fun getUsers(page: Int) = userApiService.getUsers(page)
}
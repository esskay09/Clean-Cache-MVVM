package com.terranullius.clean_cache_mvvm.framework.datasource.network.implementation

import com.terranullius.clean_cache_mvvm.business.domain.model.DataState
import com.terranullius.clean_cache_mvvm.framework.datasource.network.abstraction.UserApiService
import com.terranullius.clean_cache_mvvm.framework.datasource.network.mappers.NetworkMapper
import javax.inject.Inject

class UserApiServiceImpl @Inject constructor(
    private val goRestApiService: GoRestApiService,
    private val networkMapper: NetworkMapper
) : UserApiService {

    override suspend fun getUsers(page: Int): DataState {
        val goRestApiResponse = goRestApiService.getUsers(page)
        return networkMapper.mapFromEntity(goRestApiResponse)
    }
}
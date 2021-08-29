package com.terranullius.clean_cache_mvvm.business.interactors

import com.terranullius.clean_cache_mvvm.business.data.cache.CacheResponseHandler
import com.terranullius.clean_cache_mvvm.business.data.cache.abstraction.UserCacheDataSource
import com.terranullius.clean_cache_mvvm.business.data.util.safeCacheCall
import com.terranullius.clean_cache_mvvm.business.domain.model.DataState
import com.terranullius.clean_cache_mvvm.business.domain.state.StateResource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSavedUsers @Inject constructor(
    private val userCacheDataSource: UserCacheDataSource
) {
    suspend fun getSavedUsers(): Flow<StateResource<DataState>> = flow {
        emit(StateResource.Loading)

        val cacheResult = safeCacheCall(IO) {
            userCacheDataSource.getSavedUsers()
        }

        val result = object : CacheResponseHandler<DataState>(cacheResult) {

            override suspend fun handleSuccess(resultObj: DataState) =
                StateResource.Success(resultObj)
        }.getResult()

        emit(result)
    }
}
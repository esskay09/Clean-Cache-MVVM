package com.terranullius.clean_cache_mvvm.business.interactors

import com.terranullius.clean_cache_mvvm.business.data.cache.CacheErrors
import com.terranullius.clean_cache_mvvm.business.data.cache.CacheResponseHandler
import com.terranullius.clean_cache_mvvm.business.data.cache.abstraction.UserCacheDataSource
import com.terranullius.clean_cache_mvvm.business.data.util.safeCacheCall
import com.terranullius.clean_cache_mvvm.business.domain.model.User
import com.terranullius.clean_cache_mvvm.business.domain.state.StateResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertUser @Inject constructor(
    private val userCacheDataSource: UserCacheDataSource
) {
    suspend fun insertUser(user: User): Flow<StateResource<Long>> = flow {
        emit(StateResource.Loading)

        val cacheResult = safeCacheCall(Dispatchers.IO) {
            userCacheDataSource.insertUser(user)
        }

        val result = object : CacheResponseHandler<Long>(cacheResult) {
            override suspend fun handleSuccess(resultObj: Long): StateResource<Long> {
                return if (resultObj < 0) {
                    StateResource.Error(message = CacheErrors.CACHE_ERROR)
                } else {
                    StateResource.Success(resultObj)
                }
            }

        }.getResult()

        emit(result)
    }
}
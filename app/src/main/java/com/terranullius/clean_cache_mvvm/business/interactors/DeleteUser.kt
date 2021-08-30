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

class DeleteUser @Inject constructor(
    private val userCacheDataSource: UserCacheDataSource
) {
    suspend fun deleteUser(user: User): Flow<StateResource<Int>> = flow {
        emit(StateResource.Loading)

        val cacheResult = safeCacheCall(Dispatchers.IO) {
            userCacheDataSource.deleteUser(user)
        }

        val result = object : CacheResponseHandler<Int>(cacheResult) {
            override suspend fun handleSuccess(resultObj: Int): StateResource<Int> {
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
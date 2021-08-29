package com.terranullius.clean_cache_mvvm.business.data.cache

import com.terranullius.clean_cache_mvvm.business.data.cache.CacheErrors.CACHE_DATA_NULL
import com.terranullius.clean_cache_mvvm.business.domain.state.StateResource


abstract class CacheResponseHandler<Data>(
    private val response: CacheResult<Data?>,
) {
    suspend fun getResult(): StateResource<Data> {

        return when (response) {

            is CacheResult.GenericError -> {
                StateResource.Error(
                    message = "Reason: ${response.errorMessage}"
                )
            }

            is CacheResult.Success -> {
                if (response.value == null) {
                    StateResource.Error(
                        message = "Reason: ${CACHE_DATA_NULL}."
                    )
                } else {
                    handleSuccess(resultObj = response.value)
                }
            }

        }
    }

    abstract suspend fun handleSuccess(resultObj: Data): StateResource<Data>

}
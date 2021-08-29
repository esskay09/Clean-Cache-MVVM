package com.terranullius.clean_cache_mvvm.business.interactors

import com.terranullius.clean_cache_mvvm.business.data.network.ApiResponseHandler
import com.terranullius.clean_cache_mvvm.business.data.network.NetworkErrors.NETWORK_DATA_NULL
import com.terranullius.clean_cache_mvvm.business.data.network.abstraction.NetworkDataSource
import com.terranullius.clean_cache_mvvm.business.data.util.safeApiCall
import com.terranullius.clean_cache_mvvm.business.domain.model.DataState
import com.terranullius.clean_cache_mvvm.business.domain.state.StateResource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUsers @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {
    suspend fun getUsers(page: Int): Flow<StateResource<DataState>> = flow {

        emit(StateResource.Loading)

        val apiResult = safeApiCall(IO) {
            networkDataSource.getUsers(page)
        }

        val result = object : ApiResponseHandler<DataState>(apiResult) {

            override suspend fun handleSuccess(resultObj: DataState): StateResource<DataState> =
                StateResource.Success(resultObj)
        }.getResult()

        result?.let {
            emit(it)
        } ?: emit(StateResource.Error(message = NETWORK_DATA_NULL))
    }
}
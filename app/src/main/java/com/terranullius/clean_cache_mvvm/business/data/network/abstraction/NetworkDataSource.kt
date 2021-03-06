package com.terranullius.clean_cache_mvvm.business.data.network.abstraction

import com.terranullius.clean_cache_mvvm.business.domain.model.DataState

interface NetworkDataSource {

    suspend fun getUsers(page: Int): DataState
}
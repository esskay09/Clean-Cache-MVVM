package com.terranullius.clean_cache_mvvm.framework.datasource.network.implementation

import com.terranullius.clean_cache_mvvm.framework.datasource.network.models.GoRestApiResponse

interface GoRestApiService {

    fun getUsers(page: Int): GoRestApiResponse
}

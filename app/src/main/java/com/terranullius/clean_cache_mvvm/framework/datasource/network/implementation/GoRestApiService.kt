package com.terranullius.clean_cache_mvvm.framework.datasource.network.implementation

import com.terranullius.clean_cache_mvvm.framework.datasource.network.models.GoRestApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoRestApiService {

    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): GoRestApiResponse
}

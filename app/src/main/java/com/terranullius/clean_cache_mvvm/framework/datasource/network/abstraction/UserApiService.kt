package com.terranullius.clean_cache_mvvm.framework.datasource.network.abstraction

import com.terranullius.clean_cache_mvvm.business.domain.model.DataState

interface UserApiService {

    fun getUsers(page: Int): DataState
}
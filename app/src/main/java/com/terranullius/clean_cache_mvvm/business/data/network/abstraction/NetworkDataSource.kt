package com.terranullius.clean_cache_mvvm.business.data.network.abstraction

import com.terranullius.clean_cache_mvvm.business.domain.model.DataState

interface NetworkDataSource {

    fun getUsers(page: Int): DataState
}
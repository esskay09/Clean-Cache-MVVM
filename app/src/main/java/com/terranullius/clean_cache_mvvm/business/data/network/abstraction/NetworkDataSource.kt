package com.terranullius.clean_cache_mvvm.business.data.network.abstraction

interface NetworkDataSource {

    fun getUsers(page: Int)
}
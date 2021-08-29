package com.terranullius.clean_cache_mvvm.business.domain.model

data class DataState(
    val currentPage: Int,
    val userList: List<User>,
    val pageCount: Int
)
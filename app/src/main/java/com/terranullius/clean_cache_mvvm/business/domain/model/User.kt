package com.terranullius.clean_cache_mvvm.business.domain.model

data class User(
    val id: Int,
    val name: String,
    var cached: Boolean = false
)
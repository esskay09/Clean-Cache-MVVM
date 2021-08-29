package com.terranullius.clean_cache_mvvm.framework.datasource.network.models

import com.squareup.moshi.Json

data class UserDto(

    @Json(name = "email")
    val email: String,

    @Json(name = "gender")
    val gender: String,

    @Json(name = "id")
    val id: Int,

    @Json(name = "name")
    val name: String,
)
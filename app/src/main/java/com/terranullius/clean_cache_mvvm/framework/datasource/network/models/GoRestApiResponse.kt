package com.terranullius.clean_cache_mvvm.framework.datasource.network.models

import com.squareup.moshi.Json

data class GoRestApiResponse(

    @Json(name = "Data")
    val userList: List<UserDto>,

    @Json(name = "meta")
    val meta: Meta
)
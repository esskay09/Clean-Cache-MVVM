package com.terranullius.clean_cache_mvvm.framework.datasource.network.models

import com.squareup.moshi.Json

data class Meta(

    @Json(name = "pagination")
    val pageDetail: PageDetail
)
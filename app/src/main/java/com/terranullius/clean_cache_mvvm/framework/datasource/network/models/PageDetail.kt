package com.terranullius.clean_cache_mvvm.framework.datasource.network.models

import com.squareup.moshi.Json

data class PageDetail(

    @Json(name = "page")
    val currentPage: Int,

    @Json(name = "pages")
    val pageCount: Int,
)
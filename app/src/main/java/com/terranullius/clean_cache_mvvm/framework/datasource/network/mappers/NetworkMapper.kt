package com.terranullius.clean_cache_mvvm.framework.datasource.network.mappers

import com.terranullius.clean_cache_mvvm.business.domain.model.DataState
import com.terranullius.clean_cache_mvvm.business.domain.model.User
import com.terranullius.clean_cache_mvvm.business.domain.util.EntityMapper
import com.terranullius.clean_cache_mvvm.framework.datasource.network.models.GoRestApiResponse
import com.terranullius.clean_cache_mvvm.framework.datasource.network.models.UserDto

class NetworkMapper : EntityMapper<GoRestApiResponse, DataState> {

    override fun mapFromEntity(entity: GoRestApiResponse): DataState {
        val currentPage = entity.meta.pageDetail.currentPage
        val numPages = entity.meta.pageDetail.pageCount

        val listUser = entity.userList.toListUser()

        return DataState(
            currentPage = currentPage,
            pageCount = numPages,
            userList = listUser
        )
    }

    override fun mapToEntity(domainModel: DataState): GoRestApiResponse {
        TODO("Not yet implemented")
    }
}

private fun List<UserDto>.toListUser() = this.map {
    User(
        id = it.id,
        name = it.name
    )
}
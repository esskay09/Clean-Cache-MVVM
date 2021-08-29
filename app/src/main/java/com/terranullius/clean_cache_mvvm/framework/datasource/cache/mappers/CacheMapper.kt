package com.terranullius.clean_cache_mvvm.framework.datasource.cache.mappers

import com.terranullius.clean_cache_mvvm.business.domain.model.User
import com.terranullius.clean_cache_mvvm.business.domain.util.EntityMapper
import com.terranullius.clean_cache_mvvm.framework.datasource.cache.model.UserCacheEntity

class CacheMapper : EntityMapper<UserCacheEntity, User> {

    override fun mapFromEntity(entity: UserCacheEntity): User {
        return User(id = entity.id, email = entity.email)
    }

    override fun mapToEntity(domainModel: User): UserCacheEntity {
        return UserCacheEntity(domainModel.id, domainModel.email)
    }

    fun mapListEntityToListDomain(listEntity: List<UserCacheEntity>) = listEntity.map {
        mapFromEntity(it)
    }

    fun mapListDomainToListEntity(listDomain: List<User>) = listDomain.map {
        mapToEntity(it)
    }

}
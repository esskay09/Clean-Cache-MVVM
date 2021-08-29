package com.terranullius.clean_cache_mvvm.framework.datasource.cache.implementation

import com.terranullius.clean_cache_mvvm.business.domain.model.User
import com.terranullius.clean_cache_mvvm.framework.datasource.cache.abstraction.UserDaoService
import com.terranullius.clean_cache_mvvm.framework.datasource.cache.database.UserDao
import com.terranullius.clean_cache_mvvm.framework.datasource.cache.mappers.CacheMapper
import javax.inject.Inject

class UserDaoServiceImpl @Inject constructor(
    private val userDao: UserDao,
    private val cacheMapper: CacheMapper
) : UserDaoService {

    override suspend fun getSavedUsers(): List<User> {
        val userList = cacheMapper.mapListEntityToListDomain(userDao.getSavedUsers())
        return userList
    }

    override suspend fun insertUser(user: User): Long {
        val userEntity = cacheMapper.mapToEntity(user)
        return userDao.insertUser(userEntity)
    }

    override suspend fun deleteUser(user: User): Long {
        val userEntity = cacheMapper.mapToEntity(user)
        return userDao.deleteUser(userEntity)
    }
}
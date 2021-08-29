package com.terranullius.clean_cache_mvvm.framework.datasource.cache.database

import androidx.room.Database
import com.terranullius.clean_cache_mvvm.framework.datasource.cache.model.UserCacheEntity

@Database(entities = [UserCacheEntity::class], version = 1)
abstract class UserDatabase {

    abstract fun getUserDao() : UserDao
}
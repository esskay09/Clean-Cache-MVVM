package com.terranullius.clean_cache_mvvm.framework.datasource.cache.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.terranullius.clean_cache_mvvm.business.domain.model.User
import com.terranullius.clean_cache_mvvm.framework.datasource.cache.model.UserCacheEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserCacheEntity): Long

    @Delete
    suspend fun deleteUser(user: UserCacheEntity): Long

    @Query("SELECT * FROM saved_users")
    suspend fun getSavedUsers(): List<UserCacheEntity>

}
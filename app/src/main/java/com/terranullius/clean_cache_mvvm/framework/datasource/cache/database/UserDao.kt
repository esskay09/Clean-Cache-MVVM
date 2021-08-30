package com.terranullius.clean_cache_mvvm.framework.datasource.cache.database

import androidx.room.*
import com.terranullius.clean_cache_mvvm.framework.datasource.cache.model.UserCacheEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserCacheEntity): Long

    @Delete
    suspend fun deleteUser(user: UserCacheEntity): Int

    @Query("SELECT * FROM saved_users")
    suspend fun getSavedUsers(): List<UserCacheEntity>

}
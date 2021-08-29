package com.terranullius.clean_cache_mvvm.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.terranullius.clean_cache_mvvm.business.data.cache.abstraction.UserCacheDataSource
import com.terranullius.clean_cache_mvvm.business.data.cache.implementation.UserCacheDataSourceImpl
import com.terranullius.clean_cache_mvvm.business.data.network.abstraction.NetworkDataSource
import com.terranullius.clean_cache_mvvm.business.data.network.implementation.NetworkDataSourceImpl
import com.terranullius.clean_cache_mvvm.business.interactors.*
import com.terranullius.clean_cache_mvvm.framework.datasource.cache.abstraction.UserDaoService
import com.terranullius.clean_cache_mvvm.framework.datasource.cache.database.UserDao
import com.terranullius.clean_cache_mvvm.framework.datasource.cache.database.UserDatabase
import com.terranullius.clean_cache_mvvm.framework.datasource.cache.implementation.UserDaoServiceImpl
import com.terranullius.clean_cache_mvvm.framework.datasource.cache.mappers.CacheMapper
import com.terranullius.clean_cache_mvvm.framework.datasource.network.abstraction.UserApiService
import com.terranullius.clean_cache_mvvm.framework.datasource.network.implementation.GoRestApiService
import com.terranullius.clean_cache_mvvm.framework.datasource.network.implementation.UserApiServiceImpl
import com.terranullius.clean_cache_mvvm.framework.datasource.network.mappers.NetworkMapper
import com.terranullius.clean_cache_mvvm.util.Constants.BASE_URL
import com.terranullius.clean_cache_mvvm.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun providesGoRestApiService(
        moshi: Moshi
    ): GoRestApiService {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
            .create(GoRestApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesNetworkMapper(): NetworkMapper {
        return NetworkMapper()
    }

    @Singleton
    @Provides
    fun providesUserApiService(
        goRestApiService: GoRestApiService,
        networkMapper: NetworkMapper
    ): UserApiService {
        return UserApiServiceImpl(goRestApiService, networkMapper)
    }

    @Singleton
    @Provides
    fun providesNetworkDataSource(userApiService: UserApiService): NetworkDataSource {
        return NetworkDataSourceImpl(userApiService)
    }

    @Singleton
    @Provides
    fun providesGetUsers(
        networkDataSource: NetworkDataSource
    ): GetUsers {
        return GetUsers(networkDataSource)
    }

    @Singleton
    @Provides
    fun providesUserDao(
        appContext: Context
    ) = Room.databaseBuilder(
        appContext,
        UserDatabase::class.java,
        DATABASE_NAME
    ).build().getUserDao()

    @Singleton
    @Provides
    fun providesCacheMapper() = CacheMapper()

    @Singleton
    @Provides
    fun providesUserDaoService(
        userDao: UserDao,
        cacheMapper: CacheMapper
    ): UserDaoService {
        return UserDaoServiceImpl(
            userDao,
            cacheMapper
        )
    }

    @Singleton
    @Provides
    fun providesUserCacheDataSource(userDaoService: UserDaoService): UserCacheDataSource {
        return UserCacheDataSourceImpl(
            userDaoService
        )
    }

    @Singleton
    @Provides
    fun providesGetSavedUsersUseCase(
        userCacheDataSource: UserCacheDataSource
    ): GetSavedUsers {
        return GetSavedUsers(
            userCacheDataSource
        )
    }

    @Singleton
    @Provides
    fun providesInserUserUseCase(
        userCacheDataSource: UserCacheDataSource
    ): InsertUser {
        return InsertUser(
            userCacheDataSource
        )
    }

    @Singleton
    @Provides
    fun providesDeleteUserUseCase(
        userCacheDataSource: UserCacheDataSource
    ): DeleteUser {
        return DeleteUser(
            userCacheDataSource
        )
    }

    @Singleton
    @Provides
    fun providesUserInteractors(
        getUsers: GetUsers,
        getSavedUsers: GetSavedUsers,
        deleteUser: DeleteUser,
        insertUser: InsertUser
    ): UserInteractors {
        return UserInteractors(
            getUsers,
            getSavedUsers,
            deleteUser,
            insertUser
        )
    }


}
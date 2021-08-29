package com.terranullius.clean_cache_mvvm.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.terranullius.clean_cache_mvvm.business.data.network.implementation.NetworkDataSourceImpl
import com.terranullius.clean_cache_mvvm.framework.datasource.network.abstraction.UserApiService
import com.terranullius.clean_cache_mvvm.framework.datasource.network.implementation.GoRestApiService
import com.terranullius.clean_cache_mvvm.framework.datasource.network.implementation.UserApiServiceImpl
import com.terranullius.clean_cache_mvvm.framework.datasource.network.mappers.NetworkMapper
import com.terranullius.clean_cache_mvvm.util.Constants.BASE_URL
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
    fun providesNetworkDataSource(userApiService: UserApiService): NetworkDataSourceImpl {
        return NetworkDataSourceImpl(userApiService)
    }
}
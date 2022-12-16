package com.papierflieger.di

import com.papierflieger.data.network.service.ApiService
import com.papierflieger.data.repository.AuthenticationRepository
import com.papierflieger.data.repository.BasicAuthRepoImpl
import com.papierflieger.data.repository.DestinationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDestinationRepository(
        apiService: ApiService
    ) : DestinationRepository {
        return DestinationRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideBasicAuthRepo(
        apiService: ApiService
    ): AuthenticationRepository {
        return BasicAuthRepoImpl(apiService)
    }
}
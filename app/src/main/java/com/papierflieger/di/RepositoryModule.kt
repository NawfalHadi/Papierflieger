package com.papierflieger.di

import com.papierflieger.data.network.service.ApiService
import com.papierflieger.data.repository.*
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

    @Provides
    @Singleton
    fun provideSessionRepo(
        apiService: ApiService
    ) : SessionRepository {
        return SessionRepository(apiService)
    }

    @Provides
    @Singleton
    fun providesUserRepo(
        apiService: ApiService
    ) : UserRepository {
        return UserRepository(apiService)
    }

    @Provides
    @Singleton
    fun providesTicketRepo(
        apiService: ApiService
    ) : TicketRepository {
        return TicketRepository(apiService)
    }
}
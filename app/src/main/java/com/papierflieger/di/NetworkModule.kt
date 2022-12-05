package com.papierflieger.di

import com.papierflieger.BuildConfig
import com.papierflieger.data.network.service.ApiService
import com.papierflieger.data.repository.AuthenticationRepository
import com.papierflieger.data.repository.BasicAuthRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun logging() : HttpLoggingInterceptor
    {
        val loggingInterceptor = if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }
        else { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE) }

        return loggingInterceptor
    }

    @Provides
    fun okHtppClient(logging: HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    @Provides
    @Singleton
    fun provideApiService () : ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // Providing Repository

    @Provides
    @Singleton
    fun provideBasicAuthRepo(apiService: ApiService): AuthenticationRepository {
        return BasicAuthRepoImpl(apiService)
    }
}
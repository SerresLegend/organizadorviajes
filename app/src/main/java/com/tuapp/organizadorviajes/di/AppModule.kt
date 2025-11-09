package com.tuapp.organizadorviajes.di

import com.tuapp.organizadorviajes.data.remote.TravelApi
import com.tuapp.organizadorviajes.data.remote.repository.TravelRepositoryImpl
import com.tuapp.organizadorviajes.domain.repository.TravelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://apipractico3.jmacboy.com/api/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTravelApi(retrofit: Retrofit): TravelApi {
        return retrofit.create(TravelApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTravelRepository(api: TravelApi): TravelRepository {
        return TravelRepositoryImpl(api)
    }
}
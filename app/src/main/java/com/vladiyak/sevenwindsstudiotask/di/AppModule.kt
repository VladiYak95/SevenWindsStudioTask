package com.vladiyak.sevenwindsstudiotask.di

import android.content.Context
import android.content.SharedPreferences
import com.vladiyak.sevenwindsstudiotask.data.network.coffeeapi.CoffeeApiService
import com.vladiyak.sevenwindsstudiotask.utils.Constants
import com.vladiyak.sevenwindsstudiotask.utils.TokenInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): CoffeeApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { apiKeyAsQuery(it) }
            .build()
    }

    private fun apiKeyAsQuery(chain: Interceptor.Chain) = chain.proceed(
        chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${TokenInstance.getToken()?.token}")
            .build()
    )
}
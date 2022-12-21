package com.purushoth.muviereckcart.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.purushoth.muviereckcart.BuildConfig
import com.purushoth.muviereckcart.network.IProductApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://dummyjson.com/"

private const val READ_TIMEOUT = 60L
private const val CONNECT_TIMEOUT = 60L

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    /** To create [OkHttpClient],Used to create retrofit instance. */
    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder().addInterceptor(loggingInterceptor)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .build()

        } else {
            OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .build()
        }
    }

    /** To create [Gson] object, used to create retrofit instance */
    @Singleton
    @Provides
    fun providesGson() = GsonBuilder().setLenient().create()


    /** To create retrofit object based on [okHttpClient] [baseUrl] and [gson] configuration */
    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL)
        .client(okHttpClient).build()


    /** Tell to Hilt, how to construct [IMoiConfig] object */
    @Provides
    fun provideIPiRetrofitApi(
        retrofit: Retrofit
    ): IProductApi = retrofit.create(IProductApi::class.java)
}
package com.schools.nycschools.di

import com.schools.nycschools.data.ApiMainHeadersProvider
import com.schools.nycschools.data.services.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Network module
 *
 * @constructor Create empty Network module
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Bind api main headers provider
     *
     * @return
     */
    @Provides
    fun bindApiMainHeadersProvider(): ApiMainHeadersProvider {
        return ApiMainHeadersProvider()
    }

    /**
     * Provide h t t p logging interceptor
     *
     * @return
     */
    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    /**
     * Provide ok http client
     *
     * @param loggingInterceptor
     * @return
     */
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /**
     * Provide retrofit
     *
     * @param okHttpClient
     * @return
     */
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    /**
     * Provide api service
     *
     * @param retrofit
     * @return
     */
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}

package com.elevenine.artinstitute.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.elevenine.artinstitute.BuildConfig
import com.elevenine.artinstitute.data.api.ArtApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideApi(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): ArtApi {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ArtApi.BASE_URL)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
        return retrofit.create(ArtApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(@AppContext context: Context): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val chuckerCollector = ChuckerCollector(
                context = context,
                // Toggles visibility of the push notification
                showNotification = true,
                // Allows to customize the retention period of collected data
                retentionPeriod = RetentionManager.Period.ONE_DAY
            )
            val chuckerInterceptor = ChuckerInterceptor.Builder(context)
                .collector(chuckerCollector)
                .build()

            okHttpClientBuilder.addInterceptor(chuckerInterceptor)

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(interceptor)
        }

        return okHttpClientBuilder
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .cache(null)
            .build()
    }

    /**
     * Provides custom Kotlinx Serialization Converter Factory that is used by Retrofit2.
     */
    @Provides
    @Singleton
    @OptIn(ExperimentalSerializationApi::class)
    fun provideKotlinxSerializationConverterFactory(): Converter.Factory {
        val contentType = "application/json".toMediaType()

        return Json {
            ignoreUnknownKeys = true
        }.asConverterFactory(contentType)
    }
}
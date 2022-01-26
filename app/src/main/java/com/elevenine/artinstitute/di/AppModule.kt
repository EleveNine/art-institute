package com.elevenine.artinstitute.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.elevenine.artinstitute.BuildConfig
import com.elevenine.artinstitute.data.api.ArtApi
import com.elevenine.artinstitute.data.api.ArtApi.Companion.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Sherzod Nosirov
 * @since 08.12.2021
 */

@Module(
    includes = [
        ApiModule::class,
        ConfigModule::class,
        DatabaseModule::class,
        UseCaseBindModule::class,
        RepositoryBindModule::class
    ]
)
@InstallIn(SingletonComponent::class)
class AppModule {

}

@Module
@InstallIn(SingletonComponent::class)
class ConfigModule {

/*    @Provides
    @Singleton
    fun provideConfigService(@AppContext context: Context): ConfigService =
        ConfigServiceImpl(context, "app_configurations")*/
}

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideApi(moshi: Moshi, okHttpClient: OkHttpClient): ArtApi {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .client(okHttpClient)
            .build()
        return retrofit.create(ArtApi::class.java)
    }

    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
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

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}
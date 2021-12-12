package com.elevenine.artinstitute.di

import android.app.Application
import android.content.Context
import com.elevenine.artinstitute.App
import com.elevenine.artinstitute.BuildConfig
import com.elevenine.artinstitute.data.api.ArtApi
import com.elevenine.artinstitute.data.api.ArtApi.Companion.BASE_URL
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * @author Sherzod Nosirov
 * @since 08.12.2021
 */

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AppContext

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
    fun provideOkHttpClient(@AppContext context: Context): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(ChuckInterceptor(context))

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
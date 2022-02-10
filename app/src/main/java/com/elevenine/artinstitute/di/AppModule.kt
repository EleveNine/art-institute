package com.elevenine.artinstitute.di

import android.app.Application
import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.elevenine.artinstitute.App
import com.elevenine.artinstitute.BuildConfig
import com.elevenine.artinstitute.data.api.ArtApi
import com.elevenine.artinstitute.data.api.ArtApi.Companion.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
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
        InteractorBindModule::class,
        RepositoryBindModule::class,
        DispatcherModule::class,
    ]
)
class AppModule {
    /**
     * Provides the application context.
     */
    @Provides
    @AppContext
    fun provideAppContext(application: Application): Context = application.applicationContext

    /**
     * Singleton annotation isn't necessary since Application instance is unique but is here for
     * convention. In general, providing Activity, Fragment, BroadcastReceiver, etc does not require
     * them to be scoped since they are the components being injected and their instance is unique.
     *
     * However, having a scope annotation makes the module easier to read. We wouldn't have to look
     * at what is being provided in order to understand its scope.
     */
    @Singleton
    @Provides
    fun application(app: App): Application = app
}

@Module
class ConfigModule {

/*    @Provides
    @Singleton
    fun provideConfigService(@AppContext context: Context): ConfigService =
        ConfigServiceImpl(context, "app_configurations")*/
}

@Module
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
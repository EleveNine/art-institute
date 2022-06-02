package com.elevenine.artinstitute.di

import android.app.Application
import android.content.Context
import com.elevenine.artinstitute.App
import dagger.Module
import dagger.Provides
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
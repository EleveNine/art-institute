package com.elevenine.artinstitute.di

import com.elevenine.artinstitute.App
import com.elevenine.artinstitute.ui.categories.CategoriesFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * @author Sherzod Nosirov
 * @since 08.12.2021
 */

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun inject(app: App)

    fun inject(categoriesFragment: CategoriesFragment)


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(app: App): Builder

        fun build(): AppComponent
    }
}
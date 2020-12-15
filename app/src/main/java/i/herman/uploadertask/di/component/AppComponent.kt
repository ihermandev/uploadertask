package i.herman.uploadertask.di.component

import dagger.Component
import dagger.android.AndroidInjectionModule
import i.herman.uploadertask.App
import i.herman.uploadertask.di.module.*
import javax.inject.Singleton

/**
 * Created by Illia Herman on 14.12.2020.
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ActivityBuildersModule::class,
        FragmentBuildersModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class
    ]
)

interface AppComponent {
    fun inject(app: App)
}
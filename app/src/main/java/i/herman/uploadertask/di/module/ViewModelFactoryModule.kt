package i.herman.uploadertask.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import i.herman.uploadertask.presentation.viewmodel.ViewModelFactory

/**
 * Created by Illia Herman on 14.12.2020.
 */
@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
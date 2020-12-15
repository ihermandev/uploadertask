package i.herman.uploadertask.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import i.herman.uploadertask.di.annotation.ViewModelKey
import i.herman.uploadertask.presentation.viewmodel.MainViewModel

/**
 * Created by Illia Herman on 14.12.2020.
 */

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}
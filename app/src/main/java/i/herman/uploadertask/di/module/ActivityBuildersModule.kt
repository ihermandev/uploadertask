package i.herman.uploadertask.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import i.herman.uploadertask.presentation.activity.MainActivity

/**
 * Created by Illia Herman on 14.12.2020.
 */
@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}
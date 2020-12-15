package i.herman.uploadertask.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import i.herman.uploadertask.presentation.fragment.UploaderFragment

/**
 * Created by Illia Herman on 14.12.2020.
 */
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    internal abstract fun contributeUploaderFragment(): UploaderFragment
}
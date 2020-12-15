package i.herman.uploadertask

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import i.herman.uploadertask.di.AppInjector
import javax.inject.Inject

/**
 * Created by Illia Herman on 14.12.2020.
 */
class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        inject()
    }

    private fun inject() {
        AppInjector.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}
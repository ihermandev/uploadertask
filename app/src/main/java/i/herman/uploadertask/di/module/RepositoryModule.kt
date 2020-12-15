package i.herman.uploadertask.di.module

import dagger.Module
import dagger.Provides
import i.herman.uploadertask.data.remote.api.ApiInterface
import i.herman.uploadertask.data.remote.repository.ApiRepositoryImpl
import javax.inject.Singleton

/**
 * Created by Illia Herman on 14.12.2020.
 */

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideApiRepository(
        baseApiInterface: ApiInterface
    ): ApiRepositoryImpl =
        ApiRepositoryImpl(baseApiInterface)
}
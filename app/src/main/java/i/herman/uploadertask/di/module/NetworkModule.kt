package i.herman.uploadertask.di.module

import com.google.gson.GsonBuilder
import com.iherman.util.makeDefaultHeaderInterceptor
import com.iherman.util.makeLoggingInterceptor
import com.iherman.util.trustAllSSLCertificates
import dagger.Module
import dagger.Provides
import i.herman.uploadertask.BuildConfig
import i.herman.uploadertask.data.remote.api.ApiInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Illia Herman on 14.12.2020.
 */
@Module
class NetworkModule(private val baseApiUrl: String) {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(makeLoggingInterceptor(BuildConfig.DEBUG))
            .addInterceptor(makeDefaultHeaderInterceptor())
            .connectTimeout(3, TimeUnit.MINUTES)
            .callTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .readTimeout(3, TimeUnit.MINUTES)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(baseApiUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(okHttpClient)
        .client(trustAllSSLCertificates())
        .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiInterface = retrofit.create(
        ApiInterface::class.java
    )

}
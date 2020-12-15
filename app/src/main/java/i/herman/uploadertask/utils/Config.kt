package i.herman.uploadertask.utils

import i.herman.uploadertask.BuildConfig

/**
 * Created by Illia Herman on 14.12.2020.
 */
object Config {
    const val base_api: String = BuildConfig.BASE_API_URL
    const val testUpload = base_api + "testUpload"
}
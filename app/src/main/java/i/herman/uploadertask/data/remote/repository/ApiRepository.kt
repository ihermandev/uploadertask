package i.herman.uploadertask.data.remote.repository

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created by Illia Herman on 14.12.2020.
 */
interface ApiRepository {
    suspend fun uploadData(file: RequestBody, data: RequestBody): Response<ResponseBody>
}
package i.herman.uploadertask.data.remote.api

import i.herman.uploadertask.utils.Config
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * Created by Illia Herman on 14.12.2020.
 */
interface ApiInterface {
    @Multipart
    @POST(Config.testUpload)
    suspend fun uploadData(
        @Part("upload") file: RequestBody,
        @Part("data") data: RequestBody
    ): Response<ResponseBody>

}
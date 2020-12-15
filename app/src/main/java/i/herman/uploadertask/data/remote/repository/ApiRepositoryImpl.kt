package i.herman.uploadertask.data.remote.repository

import i.herman.uploadertask.data.remote.api.ApiInterface
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Illia Herman on 14.12.2020.
 */
class ApiRepositoryImpl
@Inject constructor(
    private val apiInterface: ApiInterface,
) : ApiRepository {
    override suspend fun uploadData(
        file: RequestBody,
        data: RequestBody
    ): Response<ResponseBody> = apiInterface.uploadData(file = file, data = data)

}
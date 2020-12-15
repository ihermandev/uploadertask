package i.herman.uploadertask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import i.herman.uploadertask.data.remote.api.ApiResponse
import i.herman.uploadertask.data.remote.repository.ApiRepositoryImpl
import i.herman.uploadertask.utils.StatusRequestBody
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

/**
 * Created by Illia Herman on 14.12.2020.
 */
class MainViewModel @Inject constructor(
    private val _apiRepositoryImpl: ApiRepositoryImpl
) : ViewModel() {

    @ExperimentalCoroutinesApi
    fun postFile(file: File) = callbackFlow<ApiResponse<Response<ResponseBody>>> {
        val requestFile = StatusRequestBody(_requestBody = file.asRequestBody("*/*".toMediaType())){
            offer(ApiResponse.loading(data = null, progress = it))
        }

        val jsonData = file.name.toRequestBody(
            contentType = "application/json".toMediaType()
        )

        val data = _apiRepositoryImpl.uploadData(file = requestFile, data = jsonData)

        try {
            send(ApiResponse.success(data = data))
        } catch (e: Exception) {
            send(ApiResponse.error(data = null, message = e.message ?: "Error Occurred"))
        }

        awaitClose { close() }
    }
}
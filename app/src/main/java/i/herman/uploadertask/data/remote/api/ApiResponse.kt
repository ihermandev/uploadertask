package i.herman.uploadertask.data.remote.api

/**
 * Created by Illia Herman on 14.12.2020.
 */
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

data class ApiResponse<out T>(val status: Status, val data: T?, val message: String?, val progress: Int?) {
    companion object {
        fun <T> success(data: T): ApiResponse<T> =
            ApiResponse(status = Status.SUCCESS, data = data, message = null, progress = 100)

        fun <T> error(data: T?, message: String): ApiResponse<T> =
            ApiResponse(status = Status.ERROR, data = data, message = message, progress = 0)

        fun <T> loading(data: T?, progress: Int?): ApiResponse<T> =
            ApiResponse(status = Status.LOADING, data = data, message = null, progress = progress)
    }
}


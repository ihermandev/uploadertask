package i.herman.uploadertask.utils

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.BounceInterpolator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 * Created by Illia Herman on 14.12.2020.
 */
fun doBounceAnimation(targetView: View): ObjectAnimator =
    ObjectAnimator.ofFloat(targetView, "translationY", 0f, -15f, 0f).apply {
        startDelay = 500
        interpolator = BounceInterpolator()
        duration = 2000
        start()
    }

fun prepareFilePart(
    file: File,
    name: String,
    mimeType: String = "*/*",
    progress: (progress: Int) -> Unit
): MultipartBody.Part {
    val requestFile = StatusRequestBody(_requestBody = file.asRequestBody(mimeType.toMediaType()), _progressStatus = progress)
    return MultipartBody.Part.createFormData(name, file.name, requestFile)
}


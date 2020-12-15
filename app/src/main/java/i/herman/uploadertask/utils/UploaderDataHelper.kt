package i.herman.uploadertask.utils

import i.herman.uploadertask.R

/**
 * Created by Illia Herman on 14.12.2020.
 */
object UploaderDataHelper {

    fun setIconByExtension(extension: String): Int {
        return when (extension) {
            "jpg", "jpeg", "webp" -> R.drawable.ic_jpg
            "doc", "docx" -> R.drawable.ic_doc
            "pdf" -> R.drawable.ic_pdf
            "txt" -> R.drawable.ic_txt
            else -> R.drawable.ic_file
        }
    }

    fun setUploadStatus(isSucceed: Boolean): Int {
        return when (isSucceed) {
            true -> R.drawable.ic_upload_done
            false -> R.drawable.ic_upload_error
        }
    }
}
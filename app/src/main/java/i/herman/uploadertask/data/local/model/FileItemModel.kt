package i.herman.uploadertask.data.local.model

import java.io.File

/**
 * Created by Illia Herman on 14.12.2020.
 */
class FileItemModel(val file: File) {
    var progress = 0
    var isSuccess = false
    var responseCode = "0"
    var statusVisible = false
}
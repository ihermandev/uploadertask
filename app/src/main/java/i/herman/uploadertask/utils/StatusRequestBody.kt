package i.herman.uploadertask.utils

import okhttp3.RequestBody
import okio.*
import java.io.IOException

/**
 * Created by Illia Herman on 14.12.2020.
 */
class StatusRequestBody(
    private val _requestBody: RequestBody,
    private val _progressStatus: (progress: Int) -> Unit
) : RequestBody() {

    @Throws(IOException::class)
    override fun contentType() = _requestBody.contentType()

    @Throws(IOException::class)
    override fun contentLength() = _requestBody.contentLength()

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val countingSink = CountingSink(sink = sink,_contentLength = contentLength(), _progressStatus = _progressStatus)
        val bufferedSink = countingSink.buffer()
        _requestBody.writeTo(bufferedSink)
        bufferedSink.flush()
    }
}

class CountingSink(
    sink: Sink,
    private val _contentLength: Long,
    private val _progressStatus: (progress: Int) -> Unit
) : ForwardingSink(sink) {
    private var bytesWritten = 0L

    override fun write(source: Buffer, byteCount: Long) {
        super.write(source, byteCount)
        bytesWritten += byteCount
        _progressStatus((bytesWritten/_contentLength).toInt() * 100)
    }
}


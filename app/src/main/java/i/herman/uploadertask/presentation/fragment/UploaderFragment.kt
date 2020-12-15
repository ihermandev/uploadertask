package i.herman.uploadertask.presentation.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.iherman.util.logError
import com.iherman.util.shortToast
import com.leon.lfilepickerlibrary.LFilePicker
import i.herman.uploadertask.R
import i.herman.uploadertask.data.local.model.FileItemModel
import i.herman.uploadertask.data.remote.api.Status
import i.herman.uploadertask.di.Injectable
import i.herman.uploadertask.presentation.adapter.FileAdapter
import i.herman.uploadertask.presentation.viewmodel.MainViewModel
import i.herman.uploadertask.presentation.viewmodel.ViewModelFactory
import i.herman.uploadertask.utils.FragmentPermissionsDelegate
import i.herman.uploadertask.utils.doBounceAnimation
import kotlinx.android.synthetic.main.fragment_uploader.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject


/**
 * Created by Illia Herman on 14.12.2020.
 */
class UploaderFragment : Fragment(R.layout.fragment_uploader), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val mainViewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    private val permissionsDelegate: FragmentPermissionsDelegate by lazy {
        FragmentPermissionsDelegate(this)
    }

    private val fileAdapter: FileAdapter by lazy {
        FileAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        if (!permissionsDelegate.hasStoragePermission())
            requestPermissions()
        initChildAnimation()
        initViewListeners()
        initRecyclerView()
    }


    private fun requestPermissions() {
        permissionsDelegate.requestAllPermissions()
    }


    private fun initChildAnimation() {
        iv_upload?.let { doBounceAnimation(it) }
    }

    private fun initViewListeners() {
        btn_import_files?.setOnClickListener {
            when {
                permissionsDelegate.hasStoragePermission() -> {
                    LFilePicker()
                        .withSupportFragment(this)
                        .withRequestCode(FILE_REQUEST)
                        .withFileSize(9999 * 1024)
                        .withIsGreater(false)
                        .withTitle("Files To Upload")
                        .withMaxNum(10)
                        .start()
                }
                else -> {
                    shortToast("Permission denied!")
                }
            }
        }

        btn_upload_files?.setOnClickListener {
            shortToast("Uploading")
            fileAdapter.filesData.forEach {
                uploadData(it)
            }
        }
    }

    private fun initRecyclerView() {
        with(rv_files) {
            adapter = fileAdapter
            itemAnimator = null
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun updateRecyclerData(data: List<FileItemModel>) {
        fileAdapter.addItems(data)
        when {
            !data.isNullOrEmpty() -> btn_upload_files.isEnabled = true
            else -> btn_upload_files.isEnabled = false
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == FILE_REQUEST) {
                data?.getStringArrayListExtra("paths").let { list ->
                    val updatedData = mutableListOf<FileItemModel>()
                    list?.forEach { path ->
                        updatedData.add(FileItemModel(file = File(path)))
                    }
                    updateRecyclerData(updatedData)
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun uploadData(file: FileItemModel) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                mainViewModel.postFile(file = file.file).collect {
                    withContext(Dispatchers.Main) {
                        when (it.status) {
                            Status.SUCCESS -> {
                                it.data?.let { data ->
                                    processDataFromResponse(data, file)
                                }
                            }
                            Status.ERROR -> {
                                logError(getString(R.string.log_server_error) + it.message.toString())
                            }
                            Status.LOADING -> {
                                it.progress?.let { progress ->
                                    fileAdapter.setProgress(item = file, progress = progress)
                                }

                            }
                        }
                    }
                }
            } catch (e: Exception) {
                logError(e.message.toString())
                withContext(Dispatchers.Main) {
                   // shortToast("Error occurred during uploading: ${e.message}")
                    fileAdapter.setSuccess(item = file, isSucceed = false, e.message.toString())
                }
            }

        }
    }

    private fun processDataFromResponse(response: Response<ResponseBody>, file: FileItemModel) {
        when (response.code()) {
            204 -> {
                fileAdapter.setSuccess(item = file, isSucceed = true, response.code().toString())
            }

            400, 500 -> {
                fileAdapter.setSuccess(item = file, isSucceed = false, response.code().toString())
            }
            else -> {
                logError("Error Occurred: ${response.code()}")
                fileAdapter.setSuccess(item = file, isSucceed = false, response.code().toString())
            }
        }
    }


    companion object {
        val TAG = UploaderFragment::class.java.simpleName
        val FILE_REQUEST = 1000

        @JvmStatic
        fun newInstance() = UploaderFragment()
    }
}
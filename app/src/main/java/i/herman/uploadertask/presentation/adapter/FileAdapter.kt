package i.herman.uploadertask.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.iherman.util.visible
import i.herman.uploadertask.R
import i.herman.uploadertask.data.local.model.FileItemModel
import i.herman.uploadertask.utils.UploaderDataHelper
import kotlinx.android.synthetic.main.viewholder_file.view.*

/**
 * Created by Illia Herman on 14.12.2020.
 */
class FileAdapter(var filesData: ArrayList<FileItemModel> = arrayListOf()) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_file, parent, false)
        )

    override fun getItemCount(): Int = filesData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = filesData[position]
        with(holder.itemView) {
            if (dataItem.responseCode == "204" || dataItem.responseCode == "0") {
                tv_name?.text = dataItem.file.name
            } else {
                tv_name?.text = "Error: ${dataItem.responseCode}"
            }
            pb_progress?.setProgress(dataItem.progress, true)
            tv_text_progress?.text = "${dataItem.progress} / 100%"
            iv_type_icon?.setImageDrawable(
                ContextCompat.getDrawable(
                    this.context, UploaderDataHelper.setIconByExtension(
                        dataItem.file.extension
                    )
                )
            )
            if (dataItem.statusVisible) {
                iv_status_icon?.setImageDrawable(
                    ContextCompat.getDrawable(
                        this.context, UploaderDataHelper.setUploadStatus(
                            dataItem.isSuccess
                        )
                    )
                )
                iv_status_icon?.visible()
            }

        }
    }

    fun addItems(data: List<FileItemModel>) {
        filesData.clear()
        filesData.addAll(data)
        notifyDataSetChanged()
    }

    fun setProgress(item: FileItemModel, progress: Int) {
        getItem(item)?.progress = progress
        notifyItemChanged(filesData.indexOf(item))
    }

    fun setSuccess(item: FileItemModel, isSucceed: Boolean, responseCode: String) {
        getItem(item)?.statusVisible = true
        getItem(item)?.isSuccess = isSucceed
        getItem(item)?.responseCode = responseCode
        notifyItemChanged(filesData.indexOf(item))
    }

    private fun getItem(item: FileItemModel) = filesData.find { item.file.name == it.file.name }
}


class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
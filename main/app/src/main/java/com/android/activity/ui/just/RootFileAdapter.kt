package com.android.activity.ui.just

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.media.MediaPlayer
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.content.Context
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.sdk.exec.databinding.ItemFileBinding
import com.android.sdk.exec.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.BufferedReader
import java.util.zip.GZIPInputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.io.IOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RootFileAdapter(
    private var files: List<RootFileItem>,
    private val onItemClick: (RootFileItem) -> Unit
) : RecyclerView.Adapter<RootFileAdapter.FileViewHolder>() {

    class FileViewHolder(val binding: ItemFileBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val binding = ItemFileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FileViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val rootfileItem = files[position]
        holder.binding.textViewFileName.text = rootfileItem.name

        // 设置图标
        when {
            rootfileItem.isDirectory -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_folder) // 文件夹图标
            }
            rootfileItem.name.endsWith(".mp4", true) || rootfileItem.name.endsWith(".mov", true) || rootfileItem.name.endsWith(".wmv", true) || rootfileItem.name.endsWith(".avi", true) || rootfileItem.name.endsWith(".flv", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_ship) // 视频文件图标
            }
            rootfileItem.name.endsWith(".jpg", true) || rootfileItem.name.endsWith(".png", true) || rootfileItem.name.endsWith(".webp", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_tup) // 图像文件图标
            }
            rootfileItem.name.endsWith(".txt", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_txt) // 文本文件图标
            }
            rootfileItem.name.endsWith(".sh", true) || rootfileItem.name.endsWith(".rc", true) || rootfileItem.name.endsWith(".bash", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_shell) // sh文件图标
            }
            rootfileItem.name.endsWith(".apk", true) || rootfileItem.name.endsWith(".apks", true) || rootfileItem.name.endsWith(".xapk", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_apk) // apk文件图标
            }
            rootfileItem.name.endsWith(".kts", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_kts) // kts文件图标
            }
            rootfileItem.name.endsWith(".bat", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_dat) // dat文件图标
            }
            rootfileItem.name.endsWith(".java", true) || rootfileItem.name.endsWith(".jar", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_java) // java文件图标
            }
            rootfileItem.name.endsWith(".kt", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_kotlin) // kt文件图标
            }
            rootfileItem.name.endsWith(".properties", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_properties) // pro文件图标
            }
            rootfileItem.name.endsWith(".js", true) || rootfileItem.name.endsWith(".json", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_js) // js文件图标
            }
            rootfileItem.name.endsWith(".gradle", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_gradle) // gralde文件图标
            }
            rootfileItem.name.endsWith(".log", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_log) // log文件图标
            }
            rootfileItem.name.endsWith(".mtz", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_mtz) // mtz文件图标
            }
            rootfileItem.name.endsWith(".cpp", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_cpp) // cpp文件图标
            }
            rootfileItem.name.endsWith(".ttf", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_ttf) // ttf文件图标
            }
            rootfileItem.name.endsWith(".html", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_web) // html文件图标
            }
            rootfileItem.name.endsWith(".xml", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_xml) // xml文件图标
            }
            rootfileItem.name.endsWith(".zip", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_zip) // 压缩文件图标
            }
            rootfileItem.name.endsWith(".7z", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_7zip) // 7z文件图标
            }
            rootfileItem.name.endsWith(".bz2", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_bzip2) // bzip2文件图标
            }
            rootfileItem.name.endsWith(".rar", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_rar) // rar文件图标
            }
            rootfileItem.name.endsWith(".tar", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_tar) // tar文件图标
            }
            rootfileItem.name.endsWith(".gz", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_gzip) // gzip文件图标
            }
            rootfileItem.name.endsWith(".img", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_img) // img文件图标
            }
            rootfileItem.name.endsWith(".mp3", true) || rootfileItem.name.endsWith(".flac", true) || rootfileItem.name.endsWith(".aac", true) || rootfileItem.name.endsWith(".wav", true) || rootfileItem.name.endsWith(".aiff", true) || rootfileItem.name.endsWith(".m4a", true) -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_flac) // 音频文件图标
            }
            
            else -> {
                holder.binding.imageViewIcon.setImageResource(R.drawable.ic_xyn_file) // 通用文件图标
            }
        }

        holder.itemView.setOnClickListener {
                onItemClick(rootfileItem)
                
        }
     holder.itemView.setOnLongClickListener { view ->
          showContextMenu(view, rootfileItem)
         true 
       }
    }

    override fun getItemCount(): Int = files.size

    private fun showContextMenu(view: View, rootfileItem: RootFileItem) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menuInflater.inflate(R.menu.recyclerview1_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_view -> {
          showFileContent(view.context, rootfileItem)
                    true
                }
                
                else -> false
            }
        }

        popupMenu.show()
    }
   
      private fun showFileContent(context: Context, rootfileItem: RootFileItem) {
    val builder = MaterialAlertDialogBuilder(context)
    val fileName = File(rootfileItem.path).name  // 获取文件名
    val fileExtension = rootfileItem.path.substringAfterLast('.', "").lowercase()

    when {
        fileExtension in listOf("jpg", "jpeg", "png", "gif", "webp") -> {
            // 直接读取图片
            val imageView = ImageView(context)
            val bitmap: Bitmap = BitmapFactory.decodeFile(rootfileItem.path)
            imageView.setImageBitmap(bitmap)

            builder.setTitle("$fileName: 图片查看")
                .setView(imageView)
                .setPositiveButton("关闭") { dialog, _ -> dialog.dismiss() }
                .show()
        }
        fileExtension in listOf("txt", "md", "sh", "bash", "kts", "gradle", "properties", "prop", "bat", "log", "html", "kt", "java", "js", "json", "cpp", "yml", "xml", "ini", "css", "pro") -> {
            // 直接读取文本内容
            val content = File(rootfileItem.path).readText()

            builder.setTitle("$fileName: 文本查看")
                .setMessage(content)
                .setPositiveButton("关闭") { dialog, _ -> dialog.dismiss() }
                .show()
        }
        fileExtension in listOf("mp4", "mkv", "avi", "mov") -> {
            // 查看视频内容
            val videoView = VideoView(context)
            videoView.setVideoPath(rootfileItem.path)
            videoView.setOnPreparedListener { it.start() }

            builder.setTitle("$fileName: 视频查看")
                .setView(videoView)
                .setPositiveButton("关闭") { dialog, _ -> dialog.dismiss() }
                .show()
        }
        fileExtension in listOf("zip", "apk", "jar", "mtz") -> {
            // 处理压缩包、APK、JAR 和 MTZ 内容
            val zipFile = File(rootfileItem.path)
            val entryNames = mutableListOf<String>()

            try {
                // 使用 ZipInputStream 逐步读取条目
                ZipInputStream(FileInputStream(zipFile)).use { zip ->
                    var entry: ZipEntry? = zip.nextEntry
                    while (entry != null) {
                        entryNames.add(entry.name)
                        entry = zip.nextEntry
                    }
                }

                builder.setTitle("$fileName: 压缩包查看")
                    .setMessage("压缩包包含以下文件:\n${entryNames.joinToString("\n")}")
                    .setPositiveButton("关闭") { dialog, _ -> dialog.dismiss() }
                    .show()
            } catch (e: Exception) {
                builder.setTitle("错误")
                    .setMessage("无法读取文件: ${e.message}")
                    .setPositiveButton("关闭") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        }
        
        else -> {
            builder.setTitle("错误")
                .setMessage("$fileName: 文件类型不受支持")
                .setPositiveButton("关闭") { dialog, _ -> dialog.dismiss() }
                .show()
        }
     }
  }
}
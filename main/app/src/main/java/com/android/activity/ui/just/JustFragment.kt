package com.android.activity.ui.just

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.sdk.exec.databinding.FragmentJustBinding
import com.android.sdk.exec.R
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.File
import java.util.ArrayDeque
import java.util.Stack
import com.android.activity.ui.just.FileAdapter
import com.android.activity.ui.just.FileItem
import com.android.activity.ui.just.RootFileAdapter
import com.android.activity.ui.just.RootFileItem

public class JustFragment : Fragment() {

companion object {
    init {
        System.loadLibrary("cpu")
    }
    
}
    private var _binding: FragmentJustBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView1: RecyclerView
    private lateinit var currentPathTextView: TextView
    private lateinit var fileAdapter: FileAdapter
    private lateinit var rootFileAdapter: RootFileAdapter
    private var currentPath: String = "/storage/emulated/0/"
    private val historyStack = Stack<String>()
    private var currentPath1: String = "/sdcard/"
    private val historyStack1 = Stack<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val justViewModel =
            ViewModelProvider(this).get(JustViewModel::class.java)
        _binding = FragmentJustBinding.inflate(inflater, container, false)
        val root: View = binding.root
        
        return root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        recyclerView1 = binding.recyclerView1
        currentPathTextView = binding.currentPathTextView
        checkPermissionAndLoadFiles()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    private fun checkPermissionAndLoadFiles() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            
            ActivityCompat.requestPermissions(requireActivity(), 
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 
                1)
        } else {
            loadFiles(currentPath)
            loadFiles1(currentPath1)
        }
    }
    
    private fun loadFiles1(path: String) {
        binding.currentPathTextView.text = path
        val files = getRootFilesInDirectory(path)
        rootFileAdapter = RootFileAdapter(files) { RootFileItem -> 
            if (RootFileItem.isDirectory) {
               historyStack1.push(currentPath1)
                currentPath1 = RootFileItem.path
                loadFiles1(currentPath1) // 递归加载
            }
        }
        binding.recyclerView1.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView1.adapter = rootFileAdapter
    }

    private fun loadFiles(path: String) {
        binding.currentPathTextView.text = path
        val files = getFilesInDirectory(path)
        fileAdapter = FileAdapter(files) { fileItem -> 
            if (fileItem.isDirectory) {
               historyStack.push(currentPath)
                currentPath = fileItem.path
                loadFiles(currentPath) // 递归加载
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = fileAdapter
    }

    private fun getRootFilesInDirectory(path: String): List<RootFileItem> {
        val directory = File(path)
        return if (directory.exists() && directory.isDirectory) {
            val files = directory.listFiles() ?: emptyArray()
            val folders = files.filter { it.isDirectory }.map { RootFileItem(it.name, it.absolutePath, true) }
            val regularFiles = files.filter { !it.isDirectory }.map { RootFileItem(it.name, it.absolutePath, false) }
            (folders + regularFiles).sortedBy { if (it.isDirectory) 0 else 1 } // 将文件夹放置在前面
        } else {
            emptyList()
        }
    }

    private fun getFilesInDirectory(path: String): List<FileItem> {
        val directory = File(path)
        return if (directory.exists() && directory.isDirectory) {
            val files = directory.listFiles() ?: emptyArray()
            val folders = files.filter { it.isDirectory }.map { FileItem(it.name, it.absolutePath, true) }
            val regularFiles = files.filter { !it.isDirectory }.map { FileItem(it.name, it.absolutePath, false) }
            (folders + regularFiles).sortedBy { if (it.isDirectory) 0 else 1 } // 将文件夹放置在前面
        } else {
            emptyList()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, @NonNull permissions: Array<out String>, @NonNull grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadFiles(currentPath)
            loadFiles1(currentPath1)
        }
    }
    
    private fun onBackPressed() {
    if (historyStack.isNotEmpty()) {
        handleFirstStack()
    }
    if (historyStack1.isNotEmpty()) {
          handleSecondStack()
    } 
}

private fun handleFirstStack() {
    if (historyStack.isNotEmpty()) {
        currentPath = historyStack.pop() // 回退到上一级路径
        loadFiles(currentPath) // 加载上一级文件
    }
}

private fun handleSecondStack() {
    if (historyStack1.isNotEmpty()) {
        currentPath1 = historyStack1.pop() // 从第二个栈中获取上一个路径
        loadFiles1(currentPath1) // 加载上一级文件
     }
  }
}
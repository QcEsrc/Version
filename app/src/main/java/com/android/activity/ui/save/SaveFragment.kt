package com.android.activity.ui.save

import android.os.Bundle
import android.os.Build
import android.Manifest
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

import android.content.Intent
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.provider.Settings

import xyn.xyn.xyn.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import xyn.xyn.xyn.databinding.FragmentSaveBinding

import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import androidx.core.widget.NestedScrollView
import java.io.File
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

import com.android.ddmlib.AdbDevice
import com.android.ddmlib.AndroidDebugBridge
import com.android.ddmlib.IShellEnabledDevice
import com.android.ddmlib.AdbHelper
import com.android.ddmlib.AdbVersion
import com.android.ddmlib.AdbInitOptions
import com.android.ddmlib.AdbCommandRejectedException

class SaveFragment : Fragment() {

companion object {
    private const val REQUEST_CODE_MANAGE_EXTERNAL_STORAGE = 123
}
    private var _binding: FragmentSaveBinding? = null
    
    private var nestedScrollView: NestedScrollView? = null
    private var materialCardView1: MaterialCardView? = null
    private var linearLayout1: LinearLayout? = null
    private var linearLayout2: LinearLayout? = null
    private var linearLayout3: LinearLayout? = null
    private var linearLayout4: LinearLayout? = null
    
    private lateinit var executeButton: Button
    private lateinit var commandEditText: EditText
    private lateinit var resultTextView: TextView
    private lateinit var rulsuTextView: TextView
    private val outputBuilder = StringBuilder()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val saveViewModel =
            ViewModelProvider(this).get(SaveViewModel::class.java)
        _binding = FragmentSaveBinding.inflate(inflater, container, false)
        val root: View = binding.root
        
        
        
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        nestedScrollView = null
        materialCardView1 = null
        linearLayout1 = null
        linearLayout2 = null
        linearLayout3 = null
        linearLayout4 = null
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 初始化 MaterialCardView 和 NestedScrollView 引用
        nestedScrollView = binding.nestedScrollView
        materialCardView1 = binding.materialCardView1
        linearLayout1 = binding.linearLayout1
        linearLayout2 = binding.linearLayout2
        linearLayout3 = binding.linearLayout3
        linearLayout4 = binding.linearLayout4
        resultTextView = binding.resultTextView
        commandEditText = binding.commandEditText
        executeButton = binding.executeButton
        rulsuTextView = binding.rulsuTextView
        
        binding.executeButton.setOnClickListener {
            executeShellCommand()
        }
        binding.rulsuTextView.setOnClickListener {
         requestManageExternalStoragePermission()
         lxynpermissions(this)
        }
        
    }
    
    private fun lxynpermissions(fragment: Fragment) {
    val permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val permissionGranted = permissions.all {
        ContextCompat.checkSelfPermission(fragment.requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    if (!permissionGranted) {
        ActivityCompat.requestPermissions(fragment.requireActivity(), permissions, 3)
    }
}
    
    private fun requestManageExternalStoragePermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        if (!Environment.isExternalStorageManager()) {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.data = Uri.parse("package:${requireContext().packageName}")
            startActivity(intent)
        } else {
        
        }
    } 
}
    /*
    private fun lxynsushell() {
     val builder = MaterialAlertDialogBuilder(requireContext())
        .setTitle(R.string.fragment_save_3)
        .setMessage(R.string.fragment_save_1)
        .setPositiveButton("ok") { _, _ ->
            // 在这里添加您需要的代码
        }
    val dialog = builder.create()
    dialog.setCancelable(true)
    dialog.setCanceledOnTouchOutside(false)
    dialog.show()
}

    private fun executeShellCommand() {
    val command = commandEditText.text.toString().trim()
    if (command.isNotEmpty()) {
        try {
            val process = Runtime.getRuntime().exec(command, null, File("/sdcard/"))
            val inputStream = process.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            outputBuilder.clear() // 清空之前的输出

            var line: String?
            while (true) {
                line = bufferedReader.readLine() ?: break
                outputBuilder.append(line).append("\n")
            }

            // 更新 TextView 的内容
            resultTextView.text = outputBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
  } */
    
    private fun executeShellCommand() {
    val command = commandEditText.text.toString().trim()
    if (command.isNotEmpty()) {
        try {
            val process = Runtime.getRuntime().exec(command, null, File("/sdcard/"))
            val inputStream = process.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            outputBuilder.clear() // 清空之前的输出

            var line: String?
            while (true) {
                line = bufferedReader.readLine() ?: break
                outputBuilder.append(line).append("\n")
            }

            // 更新 TextView 的内容
            resultTextView.text = outputBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
  }
}
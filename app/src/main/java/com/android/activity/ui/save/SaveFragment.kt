package com.android.activity.ui.save

import android.Manifest
import android.util.Log
import android.net.Uri
import android.os.Bundle
import android.os.Build
import android.os.Process
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.MenuItem

import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast

import android.content.Intent
import android.content.Context
import android.content.ClipData
import android.content.ClipboardManager
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import android.provider.Settings

import xyn.xyn.xyn.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import xyn.xyn.xyn.databinding.FragmentSaveBinding
import android.app.usage.UsageStatsManager

import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import java.io.File
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.ProcessBuilder

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

public class SaveFragment : Fragment() {

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
    private lateinit var execButton: Button
    private lateinit var commandEditText: EditText
    private lateinit var resultTextView: TextView
    private lateinit var rulsuTextView: TextView
    private val outputBuilder = StringBuilder()

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

        nestedScrollView = binding.nestedScrollView
        materialCardView1 = binding.materialCardView1
        linearLayout1 = binding.linearLayout1
        linearLayout2 = binding.linearLayout2
        linearLayout3 = binding.linearLayout3
        linearLayout4 = binding.linearLayout4
        resultTextView = binding.resultTextView
        commandEditText = binding.commandEditText
        executeButton = binding.executeButton
        execButton = binding.execButton
        rulsuTextView = binding.rulsuTextView
        
        binding.executeButton.setOnClickListener {
            lifecycleScope.launch {
                executeShellCommand()
            }
        }
        binding.execButton.setOnClickListener {
            lifecycleScope.launch {
                executeShellCommands()
            }
        }
        binding.rulsuTextView.setOnClickListener {
         requestPermission1()
         requestPermission2()
         requestPermission3()
         lxynpermissions(this)
        }
    }
    
    private fun lxynpermissions(fragment: Fragment) {
    val permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_AUDIO,
        Manifest.permission.READ_MEDIA_VIDEO,
        Manifest.permission.GET_ACCOUNTS,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.BODY_SENSORS,
        Manifest.permission.USE_SIP
    )

    val permissionGranted = permissions.all {
        ContextCompat.checkSelfPermission(fragment.requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    if (!permissionGranted) {
        ActivityCompat.requestPermissions(fragment.requireActivity(), permissions, 3)
    }
}
    
     private fun requestPermission1() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
         if (!Environment.isExternalStorageManager()) {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.data = Uri.parse("package:${requireContext().packageName}")
            startActivity(intent)
           } else {
        
             }
         } 
     }

     private fun requestPermission2() {
         if (canAccessWriteSettings()) {
        
                return
            } else {
              val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
              intent.data = Uri.parse("package:${requireContext().packageName}")
              startActivity(intent)
           }
       }

    private fun canAccessWriteSettings(): Boolean {
      return Settings.System.canWrite(requireContext())
    }

    private fun requestPermission3() {
          val context = requireContext()
          val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
           val appOpsManager = context.getSystemService(Context.APP_OPS_SERVICE) as android.app.AppOpsManager
           val mode = appOpsManager.checkOpNoThrow(android.app.AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.packageName)
    
           if (mode == android.app.AppOpsManager.MODE_ALLOWED) {
               return
         } else {
            val packageName = context.packageName
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            intent.data = Uri.fromParts("package", packageName, null)
            startActivity(intent)
           }
       }

    /*
    private fun lxynsushell() {
     val builder = MaterialAlertDialogBuilder(requireContext())
        .setTitle(R.string.fragment_save_3)
        .setMessage(R.string.fragment_save_1)
        .setPositiveButton("ok") { _, _ ->
            
        }
    val dialog = builder.create()
    dialog.setCancelable(true)
    dialog.setCanceledOnTouchOutside(false)
    dialog.show()
} */
   
     private suspend fun executeShellCommand() {
        val command = commandEditText.text.toString().trim()
        val mainfile = File("/data/data/xyn.xyn.xyn/")
        if (command.isNotEmpty()) {
            try {
            withContext(Dispatchers.IO.limitedParallelism(8)) {
               // withContext(Dispatchers.IO) {
                    val Process = ProcessBuilder(command.split(" "))
                        .directory(mainfile)
                        .redirectErrorStream(true)
                        .apply {
               val homeDirectory = "/system/bin/sh"
                val currentEnv = System.getenv()
                val envList = mutableListOf<String>()

                for ((key, value) in currentEnv) {
                    envList.add("$key=$value")
                }
                envList.add("HOME=$homeDirectory")
             val envVariables = envList.toTypedArray()
                        }
                        .start()

                    val output = Process.inputStream.bufferedReader().use { it.readText() }

                 withContext(Dispatchers.Main) {
                    if (output.length > 1000000) {
                        resultTextView.text = "${output.take(1000000)}..."
                    } else {
                        resultTextView.text = output
                       }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
   
     private suspend fun executeShellCommands() {
         val workingDirectory = File("/data/data/xyn.xyn.xyn/")
         val command = commandEditText.text.toString().trim()

    if (command.isNotEmpty()) {
      withContext(Dispatchers.IO.limitedParallelism(8)) {
            try {
                val homeDirectory = "/system/bin/sh"
                val currentEnv = System.getenv()
                val envList = mutableListOf<String>()

                for ((key, value) in currentEnv) {
                    envList.add("$key=$value")
                }

                envList.add("HOME=$homeDirectory")

             val envVariables = envList.toTypedArray()

               val process = Runtime.getRuntime().exec(command, envVariables, workingDirectory)
                process.waitFor()

                val exitCode = process.exitValue()
                val outputStream = process.inputStream.bufferedReader().use { it.readText() }
                val errorStream = process.errorStream.bufferedReader().use { it.readText() }

                withContext(Dispatchers.Main) {
                    if (exitCode == 0) {
                        resultTextView.text = outputStream
                    } else {
                        resultTextView.text = "Command failed with exit code: $exitCode\nError output: $errorStream"
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    resultTextView.text = "Command execution failed: ${e.message}"
                }
            }
         }
      }
   }
}
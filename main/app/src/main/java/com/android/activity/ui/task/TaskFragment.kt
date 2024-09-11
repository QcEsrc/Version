package com.android.activity.ui.task

import android.os.Bundle
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.graphics.Color
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log

import android.widget.TextView
import android.widget.EditText
import android.widget.Button
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.JavascriptInterface

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Context
import android.app.AlertDialog

import com.android.sdk.exec.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.core.widget.NestedScrollView
import androidx.cardview.widget.CardView
import com.android.sdk.exec.databinding.FragmentTaskBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import java.io.IOException
import java.io.InputStreamReader
import com.android.sdk.NotificationService

public class TaskFragment : Fragment() {

companion object {
    init {
        System.loadLibrary("cpu")
    }
    
}
    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    
    private var androidxNestedScrollView: NestedScrollView? = null
    private var androidxCardView1: CardView? = null
    private var androidxCardView2: CardView? = null
    private var androidxCardView3: CardView? = null
    private var androidxCardView4: CardView? = null
    private var androidxCardView5: CardView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val taskViewModel =
            ViewModelProvider(this).get(TaskViewModel::class.java)
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        val root: View = binding.root
        
        return root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        androidxNestedScrollView = binding.androidxNestedScrollView
        androidxCardView1 = binding.androidxCardView1
        androidxCardView2 = binding.androidxCardView2
        androidxCardView3 = binding.androidxCardView3
        androidxCardView4 = binding.androidxCardView4
        androidxCardView5 = binding.androidxCardView5
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        androidxNestedScrollView = null
        androidxCardView1 = null
        androidxCardView2 = null
        androidxCardView3 = null
        androidxCardView4 = null
        androidxCardView5 = null
    }
    
  /*  private fun sendBroadcast() {
        val intent = Intent(requireContext(), NotificationReceiver::class.java).apply {
            putExtra("title", "😂😂😂")
            putExtra("message", "😂😂😂😂😂😂😂")
            putExtra("personName", "😂")
        }
        requireContext().sendBroadcast(intent)
    }
    
    private fun startNotificationService() {
        val intent = Intent(requireContext(), NotificationService::class.java)
        // 可以在这里传递需要的参数,如标题、消息、发送者等
        requireContext().startService(intent)
    }  */
    
}
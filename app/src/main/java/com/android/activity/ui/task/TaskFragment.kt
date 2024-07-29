package com.android.activity.ui.task

import android.os.Bundle
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.graphics.Color
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
import android.content.Context
import android.app.AlertDialog

import xyn.xyn.xyn.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import xyn.xyn.xyn.databinding.FragmentTaskBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import java.io.IOException
import java.io.InputStreamReader

class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    
    private val binding get() = _binding!!
    
    private lateinit var javalogTextView: TextView

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

        javalogTextView = binding.javalogTextView
        
        
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
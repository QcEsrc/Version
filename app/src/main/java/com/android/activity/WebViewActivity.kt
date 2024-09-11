package com.android.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.sdk.exec.databinding.ActivityWebBinding
import com.android.sdk.exec.R

public class WebViewActivity : AppCompatActivity() {

companion object {
    init {
        System.loadLibrary("cpu")
    }
    
}
    private var _binding: ActivityWebBinding? = null
    
    private val binding: ActivityWebBinding
      get() = checkNotNull(_binding) { "WebViewActivity null" }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
    }
    
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

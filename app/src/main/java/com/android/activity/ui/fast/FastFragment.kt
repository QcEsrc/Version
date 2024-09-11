package com.android.activity.ui.fast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.sdk.exec.databinding.FragmentFastBinding
import com.android.sdk.exec.R
import androidx.core.widget.NestedScrollView

public class FastFragment : Fragment() {

companion object {
    init {
        System.loadLibrary("cpu")
    }
    
}
    private var _binding: FragmentFastBinding? = null
    private val binding get() = _binding!!
    
    private var androidxNestedScrollView: NestedScrollView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fastViewModel =
            ViewModelProvider(this).get(FastViewModel::class.java)
        _binding = FragmentFastBinding.inflate(inflater, container, false)
        val root: View = binding.root

        
        return root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        androidxNestedScrollView = binding.androidxNestedScrollView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        androidxNestedScrollView = null
        _binding = null
    }
}
package com.android.activity.ui.valo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import xyn.xyn.xyn.databinding.FragmentValoBinding

class ValoFragment : Fragment() {

    private var _binding: FragmentValoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val ValoViewModel =
            ViewModelProvider(this).get(ValoViewModel::class.java)
        _binding = FragmentValoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
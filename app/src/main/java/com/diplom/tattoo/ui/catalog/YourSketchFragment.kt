package com.diplom.tattoo.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diplom.tattoo.R
import com.diplom.tattoo.databinding.FragmentYourSketchBinding

class YourSketchFragment : Fragment() {

    private var _binding: FragmentYourSketchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentYourSketchBinding.inflate(inflater, container, false)
        btnListeners()
        return binding.root
    }

    private fun btnListeners() {
        binding.buttonRecord.setOnClickListener {
            findNavController().navigate(R.id.action_nav_your_sketch_to_nav_record)
        }
    }
}
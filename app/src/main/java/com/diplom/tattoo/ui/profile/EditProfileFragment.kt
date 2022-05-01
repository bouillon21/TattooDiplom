package com.diplom.tattoo.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diplom.tattoo.R
import com.diplom.tattoo.databinding.FragmentEditProfileBinding
import com.diplom.tattoo.databinding.FragmentRegBinding

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        btnListeners()

        return binding.root
    }

    private fun btnListeners() {

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
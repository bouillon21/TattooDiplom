package com.diplom.tattoo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diplom.tattoo.adapter.MasterInfoAdapter
import com.diplom.tattoo.adapter.TatuInfoAdapter
import com.diplom.tattoo.data.Master
import com.diplom.tattoo.data.Tattoo
import com.diplom.tattoo.databinding.FragmentMasterBinding

class MasterFragment : Fragment() {
    private var _binding: FragmentMasterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMasterBinding.inflate(inflater, container, false)
        btnListeners()
        initInfo()

        return binding.root
    }

    private fun initInfo() {
        val cMaster = arguments?.getParcelable<Master>("current_master")

        binding.labelMaster.text = cMaster?.firstName
        binding.meaningMaster.text = cMaster?.des

        binding.listPhoto.adapter = MasterInfoAdapter(requireContext(), listOf<Master>(cMaster!!))
    }

    private fun btnListeners() {
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
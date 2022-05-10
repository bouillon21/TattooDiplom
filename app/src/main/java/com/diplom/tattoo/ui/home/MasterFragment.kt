package com.diplom.tattoo.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.diplom.tattoo.R
import com.diplom.tattoo.adapter.DescriptionTatuInfoAdapter
import com.diplom.tattoo.adapter.MasterInfoAdapter
import com.diplom.tattoo.adapter.TatuInfoAdapter
import com.diplom.tattoo.data.Master
import com.diplom.tattoo.data.Tatu
import com.diplom.tattoo.data.TemporarilyDataStorage
import com.diplom.tattoo.databinding.FragmentHomeBinding
import com.diplom.tattoo.databinding.FragmentMasterBinding
import java.util.*

class MasterFragment : Fragment() {
    private var _binding: FragmentMasterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentMasterBinding.inflate(inflater, container, false)
        btnListeners()
        init_info()

        return binding.root
    }

    private fun init_info(){
        val cMaster = arguments?.getParcelable<Master>("current_master")

        binding.labelMaster.text = cMaster?.firstName
        binding.meaningMaster.text = cMaster?.des

        val adapterTatu = MasterInfoAdapter(requireContext(), listOf<Master>(cMaster!!))
        binding.listPhoto.adapter = adapterTatu
    }

    private fun btnListeners() {
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
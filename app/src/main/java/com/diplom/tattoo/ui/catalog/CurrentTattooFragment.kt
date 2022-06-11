package com.diplom.tattoo.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diplom.tattoo.R
import com.diplom.tattoo.adapter.DescriptionTatuInfoAdapter
import com.diplom.tattoo.adapter.TatuInfoAdapter
import com.diplom.tattoo.data.Tattoo
import com.diplom.tattoo.databinding.FragmentCurrentTattooBinding

class CurrentTattooFragment : Fragment() {

    private var _binding: FragmentCurrentTattooBinding? = null
    private val binding get() = _binding!!

    private var cTattoo: Tattoo? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrentTattooBinding.inflate(inflater, container, false)

        initInfo()
        btnListeners()
        initResView()

        return binding.root
    }

    private fun initInfo() {
        cTattoo = arguments?.getParcelable<Tattoo>("current_tattoo")
        binding.labelTattoo.text = cTattoo?.title
        binding.meaningTatu.text = cTattoo?.des

    }

    private fun btnListeners() {
        binding.buttonRecord.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("current_tattoo", cTattoo)
            findNavController().navigate(R.id.action_nav_current_tattoo_to_nav_record, bundle)
        }
    }

    private fun initResView() {
        binding.listPhoto.adapter = TatuInfoAdapter(requireContext(), cTattoo!!.photoUrl)
        binding.listRecommended.adapter =
            DescriptionTatuInfoAdapter(requireContext(), cTattoo!!.recommended)

        binding.listColor.adapter = DescriptionTatuInfoAdapter(requireContext(), cTattoo!!.color)
    }
}
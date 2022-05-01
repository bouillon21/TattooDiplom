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
import com.diplom.tattoo.data.Tatu
import com.diplom.tattoo.data.TemporarilyDataStorage
import com.diplom.tattoo.databinding.FragmentCurrentTattooBinding

class CurrentTattooFragment : Fragment() {

    private var _binding: FragmentCurrentTattooBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrentTattooBinding.inflate(inflater, container, false)

        val tattoo = arguments?.getParcelable<Tatu>("current_tattoo")

        binding.labelTattoo.text = tattoo?.title
        val tatu = listOf<Tatu>(tattoo!!)

        val adapterTatu = TatuInfoAdapter(requireContext(), tatu)
        binding.listPhoto.adapter = adapterTatu

        val recommended = TemporarilyDataStorage.getDescriptionList()
        val adapterTatuInfo = DescriptionTatuInfoAdapter(requireContext(), recommended)
        binding.listRecommended.adapter = adapterTatuInfo

        binding.listColor.adapter = adapterTatuInfo

        binding.buttonRecord.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("current_tattoo", tattoo)
            findNavController().navigate(R.id.action_nav_current_tattoo_to_nav_record, bundle)
        }

        return binding.root
    }
}
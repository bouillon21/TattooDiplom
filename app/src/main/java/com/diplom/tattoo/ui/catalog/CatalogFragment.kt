package com.diplom.tattoo.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.diplom.tattoo.R
import com.diplom.tattoo.adapter.TatuAdapter
import com.diplom.tattoo.databinding.FragmentCatalogBinding
import com.diplom.tattoo.model.SharedDatabaseViewModel

class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private lateinit var model: SharedDatabaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        model = ViewModelProvider(requireActivity())[SharedDatabaseViewModel::class.java]

        btnListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.tattoo.observe(viewLifecycleOwner) {
            val adapterMaster = TatuAdapter(requireContext(), it!!) { position ->
                val cTattoo = it[position]
                val bundle = Bundle()
                bundle.putParcelable("current_tattoo", cTattoo)
                findNavController().navigate(R.id.action_nav_catalog_to_nav_current_tattoo, bundle)
            }
            val RVMaster = binding.rvCatalog
            RVMaster.adapter = adapterMaster
        }
    }

    private fun btnListeners() {
        binding.buttonEdit.setOnClickListener {
            findNavController().navigate(R.id.action_nav_catalog_to_nav_your_sketch)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
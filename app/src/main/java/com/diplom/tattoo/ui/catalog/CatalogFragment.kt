package com.diplom.tattoo.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.diplom.tattoo.R
import com.diplom.tattoo.adapter.TatuAdapter
import com.diplom.tattoo.data.TemporarilyDataStorage
import com.diplom.tattoo.databinding.FragmentCatalogBinding

class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)

        initRV()
        btnListeners()
        return binding.root
    }


    private fun initRV(){
        val recyclerView = binding.rvCatalog
        val tatu = TemporarilyDataStorage.getTatuList()
        val adapterTatu = TatuAdapter(requireContext(), tatu) { position ->
            val tattoo = tatu[position]
            val bundle = Bundle()
            bundle.putParcelable("current_tattoo", tattoo)
            findNavController().navigate(R.id.action_nav_catalog_to_nav_current_tattoo, bundle)
        }
        recyclerView.adapter = adapterTatu
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
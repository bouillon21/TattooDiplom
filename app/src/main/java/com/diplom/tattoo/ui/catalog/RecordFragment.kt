package com.diplom.tattoo.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.diplom.tattoo.data.Tatu
import com.diplom.tattoo.databinding.FragmentRecordBinding

private var _binding: FragmentRecordBinding? = null
private val binding get() = _binding!!

class RecordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecordBinding.inflate(inflater, container, false)

        val tattoo = arguments?.getParcelable<Tatu>("current_tattoo")
        binding.nameTattoo.text = tattoo?.title

        return binding.root
    }

    companion object
}
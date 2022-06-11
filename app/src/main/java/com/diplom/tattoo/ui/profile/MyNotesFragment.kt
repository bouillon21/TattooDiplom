package com.diplom.tattoo.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diplom.tattoo.adapter.NotesAdapter
import com.diplom.tattoo.databinding.FragmentMyNotesBinding
import com.diplom.tattoo.model.SharedDatabaseViewModel
import com.google.firebase.auth.FirebaseAuth

class MyNotesFragment : Fragment() {

    private var _binding: FragmentMyNotesBinding? = null
    private val binding get() = _binding!!

    private var mAuth = FirebaseAuth.getInstance()

    private lateinit var model: SharedDatabaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyNotesBinding.inflate(inflater, container, false)
        model = ViewModelProvider(requireActivity())[SharedDatabaseViewModel::class.java]
        btnListeners()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.records.observe(viewLifecycleOwner) { cRecord ->
            binding.rvRecords.adapter =
                NotesAdapter(requireContext(), cRecord.filter { it.UID == mAuth.currentUser!!.uid })

        }
    }

    private fun btnListeners() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
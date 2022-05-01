package com.diplom.tattoo.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diplom.tattoo.MainActivity
import com.diplom.tattoo.R
import com.diplom.tattoo.authorization.AuthorizationActivity
import com.diplom.tattoo.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private var mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        btnListeners()
        return binding.root
    }

    private fun btnListeners() {

        binding.buttonEdit.setOnClickListener {
            findNavController().navigate(R.id.action_nav_profile_to_nav_edit_profile)
        }
        binding.myNotes.setOnClickListener {
            findNavController().navigate(R.id.action_nav_profile_to_nav_my_notes)
        }
        binding.buttonExit.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(requireActivity(), AuthorizationActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
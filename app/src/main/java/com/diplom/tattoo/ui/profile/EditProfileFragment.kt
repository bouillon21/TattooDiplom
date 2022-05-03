package com.diplom.tattoo.ui.profile

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.diplom.tattoo.data.User
import com.diplom.tattoo.databinding.FragmentEditProfileBinding
import com.diplom.tattoo.model.SharedDatabaseViewModel

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var model: SharedDatabaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        model = ViewModelProvider(requireActivity())[SharedDatabaseViewModel::class.java]

        btnListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.user.observe(viewLifecycleOwner) {
            binding.editName.setText(it.firstName)
            binding.editLastName.setText(it.lastName)
            binding.editEmail.setText(it.email)
        }
    }

    private fun saveData(){
        val name = binding.editName.text.toString()
        val lastName = binding.editLastName.text.toString()
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lastName)){
            model.updateDataDB(User(name, lastName, ""))
            Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()
        }
        else
            Toast.makeText(context, "Enter data", Toast.LENGTH_SHORT).show()
    }

    private fun changePhotoUser(){

    }

    private fun btnListeners() {
        binding.saveButton.setOnClickListener { saveData() }
        binding.changePhoto.setOnClickListener {changePhotoUser()}
    }
}
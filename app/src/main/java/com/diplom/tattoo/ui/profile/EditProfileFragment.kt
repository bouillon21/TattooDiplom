package com.diplom.tattoo.ui.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.diplom.tattoo.R
import com.diplom.tattoo.data.User
import com.diplom.tattoo.databinding.FragmentEditProfileBinding
import com.diplom.tattoo.model.SharedDatabaseViewModel
import com.squareup.picasso.Picasso
import java.io.FileNotFoundException
import java.io.InputStream


class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var model: SharedDatabaseViewModel

    private var imageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1


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
            Picasso.get().load(it.photoUrl).placeholder(R.drawable.default_avatar).into(binding.avatar)
        }
    }

    private fun saveData() {
        val name = binding.editName.text.toString()
        val lastName = binding.editLastName.text.toString()
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lastName)) {
            model.updateUserDB(User(name, lastName), imageUri)
            Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()
        } else
            Toast.makeText(context, "Enter data", Toast.LENGTH_SHORT).show()
    }

    private fun changePhotoUser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_IMAGE_REQUEST -> if (resultCode == RESULT_OK) {
                try {
                    imageUri = data?.data
                    binding.avatar.setImageURI(imageUri)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun btnListeners() {
        binding.saveButton.setOnClickListener { saveData() }
        binding.changePhoto.setOnClickListener { changePhotoUser() }
    }
}
package com.diplom.tattoo.ui.catalog

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.diplom.tattoo.R
import com.diplom.tattoo.databinding.FragmentYourSketchBinding
import com.diplom.tattoo.model.SharedDatabaseViewModel
import java.io.FileNotFoundException

class YourSketchFragment : Fragment() {

    private var _binding: FragmentYourSketchBinding? = null
    private val binding get() = _binding!!

    private lateinit var model: SharedDatabaseViewModel
    private var imageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentYourSketchBinding.inflate(inflater, container, false)
        model = ViewModelProvider(requireActivity())[SharedDatabaseViewModel::class.java]
        btnListeners()
        return binding.root
    }

    private fun changePhotoSketch() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_IMAGE_REQUEST -> if (resultCode == Activity.RESULT_OK) {
                try {
                    imageUri = data?.data
                    binding.imageSketch.setImageURI(imageUri)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun btnListeners() {
        binding.buttonImage.setOnClickListener {
            changePhotoSketch()
        }
        binding.buttonRecord.setOnClickListener {
            if (imageUri != null) {
                val bundle = Bundle()
                bundle.putParcelable("image_sketch", imageUri)
                findNavController().navigate(R.id.action_nav_your_sketch_to_nav_record, bundle)
            } else
                Toast.makeText(context, "Enter photo", Toast.LENGTH_SHORT).show()
        }
    }
}
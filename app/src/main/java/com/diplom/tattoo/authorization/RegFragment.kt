package com.diplom.tattoo.authorization

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.diplom.tattoo.R
import com.diplom.tattoo.data.User
import com.diplom.tattoo.databinding.FragmentRegBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegFragment : Fragment() {

    private var _binding: FragmentRegBinding? = null
    private val binding get() = _binding!!

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private lateinit var mAuth: FirebaseAuth
    private val TAG = "CreateAccountActivity"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegBinding.inflate(inflater, container, false)
        initFB()
        btnListeners()

        return binding.root
    }

    private fun createUser() {

        val login = binding.emailEdit.text.toString()
        val pass = binding.passEdit.text.toString()
        val name = binding.nameEdit.text.toString()
        val lastName = binding.lastNameEdit.text.toString()

        if (!TextUtils.isEmpty(login) &&
            !TextUtils.isEmpty(pass) &&
            !TextUtils.isEmpty(name) &&
            !TextUtils.isEmpty(lastName)
        ) {
            mAuth.createUserWithEmailAndPassword(login, pass)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        verifyEmail()

                        val cUserDB = mDatabaseReference!!.child(mAuth.currentUser!!.uid)
                        cUserDB.child("firstName").setValue(name)
                        cUserDB.child("lastName").setValue(lastName)

                        Toast.makeText(context, "yes", Toast.LENGTH_SHORT).show()
                    } else
                        Toast.makeText(context, "no", Toast.LENGTH_SHORT).show()
                }
        } else
            Toast.makeText(context, "Enter all details", Toast.LENGTH_SHORT).show()
    }

    private fun verifyEmail() {
        val mUser = mAuth.currentUser
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        context,
                        "Verification email sent to " + mUser.email,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(
                        context,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun initFB() {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("users")
    }

    private fun btnListeners() {
        binding.regBtn.setOnClickListener {
            createUser()
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
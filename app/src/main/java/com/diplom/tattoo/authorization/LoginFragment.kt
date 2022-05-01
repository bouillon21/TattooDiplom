package com.diplom.tattoo.authorization

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diplom.tattoo.MainActivity
import com.diplom.tattoo.R
import com.diplom.tattoo.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        btnListeners()
        return binding.root
    }

    private fun loginUser() {
        val login = binding.loginEdit.text.toString()
        val pass = binding.passEdit.text.toString()

        if (!TextUtils.isEmpty(login) &&
            !TextUtils.isEmpty(pass)
        ) {
            mAuth.signInWithEmailAndPassword(login, pass)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        updateUI()
                        Toast.makeText(context, "Authorization successful", Toast.LENGTH_SHORT)
                            .show()
                    } else
                        Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
        } else
            Toast.makeText(context, "Enter all details", Toast.LENGTH_SHORT).show()
    }

    private fun updateUI() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun btnListeners() {

        binding.authBth.setOnClickListener {
            loginUser()
        }
        binding.regBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_regFragment)
        }
    }
}
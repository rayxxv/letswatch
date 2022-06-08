package com.rayxxv.letswatch.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rayxxv.letswatch.R
import com.rayxxv.letswatch.data.local.DataStoreManager.Companion.DEFAULT_IMAGE
import com.rayxxv.letswatch.data.local.User
import com.rayxxv.letswatch.databinding.FragmentRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    )
            : View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnDaftar.setOnClickListener {
            when {
                binding.etUsername.text.toString().isEmpty() || binding.etPassword.text.toString()
                    .isEmpty() || binding.etUsername.text.toString()
                    .isEmpty() || binding.etEmail.text.toString()
                    .isEmpty() || binding.etConfirmPassword.text.toString().isEmpty() -> {
                    Toast.makeText(requireContext(), "Form tidak boleh kosong!", Toast.LENGTH_SHORT)
                        .show()
                }
                binding.etPassword.text.toString() != binding.etConfirmPassword.text.toString() -> {
                    Toast.makeText(requireContext(), "Password tidak sama!", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    val nama = binding.etUsername.text.toString()
                    val email = binding.etEmail.text.toString()
                    val username = binding.etUsername.text.toString()
                    val password = binding.etPassword.text.toString()
                    val user = User(
                        null,
                        email,
                        username,
                        password,
                        DEFAULT_IMAGE
                    )

                    var isUsed = false
                    viewModel.userData.observe(viewLifecycleOwner) {
                        if (it != null) {
                            if(it.username == username){
                                isUsed = true
                            }
                        }
                    }

                    if(isUsed){
                        binding.wrapUsername.error = "Username sudah digunakan!"
                    } else {
                        binding.wrapUsername.error = ""
                        viewModel.insertUser(user)
                        Toast.makeText(
                            requireContext(),
                            "Register Success!",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                }
            }
        }
    }
}
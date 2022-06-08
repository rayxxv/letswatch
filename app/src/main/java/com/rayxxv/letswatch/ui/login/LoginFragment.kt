package com.rayxxv.letswatch.ui.login

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rayxxv.letswatch.R
import com.rayxxv.letswatch.data.local.DataStoreManager.Companion.DEFAULT_ID
import com.rayxxv.letswatch.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding?= null
    private val binding get() = _binding!!
    private val viewModel : LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    )
            : View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserPref()
        viewModel.userPref.observe(viewLifecycleOwner){
            if (it.id != DEFAULT_ID){
                Toast.makeText(requireContext(), "Selamat Datang, ${it.username}", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_menuFragment)
            }
        }
        binding.btnMasuk.setOnClickListener {
            when{
                binding.etUsername.text.toString().isEmpty() || binding.etPassword.text.toString().isEmpty() -> {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Alert")
                        .setMessage("Username/Password tidak boleh kosong!")
                        .setPositiveButton("OK"){ dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
                else -> {
                    binding.wrapPassword.error = ""
                    val username = binding.etUsername.text.toString()
                    val password = binding.etPassword.text.toString()
                    viewModel.loginUser(username, password)
                    viewModel.loginData.observe(viewLifecycleOwner){
                        if (it == null){
                            binding.wrapPassword.error = "Username/password salah!"

                        } else {
                            viewModel.setUserPref(it)
                            if (findNavController().currentDestination?.id == R.id.loginFragment){
                                findNavController().navigate(R.id.action_loginFragment_to_menuFragment)
                            }
                        }
                    }
                }
            }
        }
        binding.btnDaftar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
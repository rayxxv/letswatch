package com.rayxxv.letswatch.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rayxxv.letswatch.R
import com.rayxxv.letswatch.data.local.DataStoreManager.Companion.DEFAULT_IMAGE
import com.rayxxv.letswatch.data.local.User
import com.rayxxv.letswatch.data.screen.components.RoundedButton
import com.rayxxv.letswatch.data.screen.theme.LetsWatchTheme
import com.rayxxv.letswatch.databinding.FragmentRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    //    private var _binding: FragmentRegisterBinding? = null
//    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setContent {
                LetsWatchTheme {
                    Surface(
                        color = MaterialTheme.colors.surface,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        RegisterForm()
                    }
                }
            }
        }
    }

    @Composable
    fun RegisterForm() {
        val username = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val confirmPassword = remember { mutableStateOf("") }
        val passwordVisibility = remember { mutableStateOf(false) }
        val confirmPasswordVisibility = remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current

        Box(
            modifier = Modifier.fillMaxWidth()
        ){

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    IconButton(
                        onClick = {
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Icon",
                            tint = MaterialTheme.colors.secondary
                        )
                    }

                    Text(
                        text = "Create An Account",
                        style = MaterialTheme.typography.h5.copy(
                            color = MaterialTheme.colors.secondary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = username.value,
                        onValueChange = { username.value = it },
                        label = { Text(text = "Username") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        label = { Text(text = "Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        label = { Text(text = "Password") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility.value = !passwordVisibility.value
                            }) {
                                Icon(
                                    imageVector = if (passwordVisibility.value)
                                        Icons.Filled.Visibility
                                    else
                                        Icons.Filled.VisibilityOff, ""
                                )
                            }
                        },
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = confirmPassword.value,
                        onValueChange = { confirmPassword.value = it },
                        label = { Text(text = "Confirm Password") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        visualTransformation = if (confirmPasswordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {
                                confirmPasswordVisibility.value = !confirmPasswordVisibility.value
                            }) {
                                Icon(
                                    imageVector = if (confirmPasswordVisibility.value)
                                        Icons.Filled.Visibility
                                    else
                                        Icons.Filled.VisibilityOff, ""
                                )
                            }
                        },
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    RoundedButton(
                        text = "Sign Up",
                        onClick = {
                            when {
                                username.value == ""
                                        || email.value == ""
                                        || password.value == ""
                                        || confirmPassword.value == ""
                                -> {
                                    Toast.makeText(
                                        requireContext(),
                                        "Form tidak boleh kosong!",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                                password.value != confirmPassword.value -> {
                                    Toast.makeText(
                                        requireContext(),
                                        "Password tidak sama!",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                                else -> {
                                    val user = User(
                                        null,
                                        username.value,
                                        email.value,
                                        password.value,
                                        DEFAULT_IMAGE
                                    )
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
                    )

                    ClickableText(
                        text = buildAnnotatedString {
                            append("Already have an account?")

                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colors.secondary,
                                    fontWeight = FontWeight.Bold
                                )
                            ){
                                append("Log in")
                            }
                        },
                        onClick = {
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }
                    )
                }

            }

        }

    }

}
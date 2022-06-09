package com.rayxxv.letswatch.ui.login

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rayxxv.letswatch.R
import com.rayxxv.letswatch.data.local.DataStoreManager.Companion.DEFAULT_ID
import com.rayxxv.letswatch.data.screen.components.RoundedButton
import com.rayxxv.letswatch.data.screen.theme.LetsWatchTheme
import com.rayxxv.letswatch.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            viewModel.getUserPref()
            viewModel.userPref.observe(viewLifecycleOwner) {
                if (it.id != DEFAULT_ID) {
                    Toast.makeText(requireContext(), "Selamat Datang, ${it.username}", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_menuFragment)
                } else {
                    setContent {
                        LetsWatchTheme {
                            Surface(
                                color = MaterialTheme.colors.background,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Login()
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun Login() {
        val username = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val passwordVisibility = remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ){
            Image(
                painter = painterResource(
                    id = R.drawable.mockup1
                ),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ){
                ConstraintLayout {

                    val (surface, fab) = createRefs()

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .constrainAs(surface) {
                                bottom.linkTo(parent.bottom)
                            },
                        color = Color.White,
                        shape = RoundedCornerShape(
                            topStartPercent = 8,
                            topEndPercent = 8
                        )
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ){
                            Text(
                                text = "Welcome Back!",
                                style = MaterialTheme.typography.h4.copy(
                                    fontWeight = FontWeight.Medium
                                )
                            )

                            Text(
                                text = "Login to your Account",
                                style = MaterialTheme.typography.h5.copy(
                                    color = MaterialTheme.colors.secondary
                                )
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ){
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
                            }
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                RoundedButton(
                                    text = "Login",
                                    onClick = {
                                        if (username.value == "" || password.value == "") {
                                            android.app.AlertDialog.Builder(requireContext())
                                                .setTitle("")
                                                .setMessage("Username/Password Kosong!")
                                                .setPositiveButton("Iya") { dialog, _ ->
                                                    dialog.dismiss()
                                                }
                                                .show()
                                        } else {
                                            viewModel.loginUser(username.value, password.value)
                                            viewModel.loginData.observe(viewLifecycleOwner) {
                                                if (it == null) {
                                                    android.app.AlertDialog.Builder(requireContext())
                                                        .setTitle("")
                                                        .setMessage("Username/Password Salah!")
                                                        .setPositiveButton("Iya") { dialog, _ ->
                                                            dialog.dismiss()
                                                        }
                                                        .show()
                                                } else {
                                                    if (findNavController().currentDestination?.id == R.id.loginFragment) {
                                                        val toHomepage =
                                                            LoginFragmentDirections.actionLoginFragmentToMenuFragment()
                                                        findNavController().navigate(toHomepage)
                                                    }
                                                }
                                                viewModel.loginData.removeObservers(viewLifecycleOwner)
                                            }
                                        }
                                    }

                                )
                            }


                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                                ClickableText(
                                    text = buildAnnotatedString {
                                        append("Do not have an Account?")

                                        withStyle(
                                            style = SpanStyle(
                                                color = MaterialTheme.colors.secondary,
                                                fontWeight = FontWeight.Bold
                                            )
                                        ){
                                            append("Sign up")
                                        }
                                    }
                                ){
                                    findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                                }
                            }
                        }
                    }
                }
            }

        }
    }

}
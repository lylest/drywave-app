package com.example.funs.screens.signup

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.funs.components.*
import com.example.funs.navigation.Screen
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Signup(navController: NavController, signUpViewModel: SignupViewModel = viewModel()) {

    Scaffold() {
        Surface(modifier = Modifier.fillMaxSize()) {
            val scrollState = rememberScrollState()
            val context = LocalContext.current

            if (signUpViewModel.showToast.value) {
                 LaunchedEffect(key1 = signUpViewModel.showToast.value ) {
                     Toast.makeText(context, signUpViewModel.signupResponse.value, Toast.LENGTH_SHORT).show()
                 }
            }

            if(signUpViewModel.signupSuccess.value) {
                 LaunchedEffect(key1 = signUpViewModel.signupSuccess.value) {
                      navController.navigate("login_screen")
                 }
            }
            Column {
                NormalText(
                    "Welcome back",
                    FontWeight.Normal,
                    16,
                    MaterialTheme.colorScheme.onSurface,
                    TextAlign.Center, 12
                )

                Heading(
                    "Create an Account",
                    22,
                    MaterialTheme.colorScheme.primary,
                    TextAlign.Center
                )

                Column(
                    modifier = Modifier.fillMaxWidth().verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedInput(
                        "First name",
                        Icons.Outlined.Person,
                        signUpViewModel.SignupUIState.value.fullNameError,
                        onTextSelected = {
                            signUpViewModel.onEvent(SignupEvent.fullNameChanged(it))
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedInput(
                        "Phone",
                        Icons.Outlined.Phone,
                        signUpViewModel.SignupUIState.value.phoneError,
                        onTextSelected = {
                            signUpViewModel.onEvent(SignupEvent.phoneChanged(it))
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedInput(
                        "Email",
                        Icons.Outlined.MailOutline,
                        signUpViewModel.SignupUIState.value.emailError,
                        onTextSelected = {
                            signUpViewModel.onEvent(SignupEvent.emailChanged(it))
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedInput(
                        "Address",
                        Icons.Outlined.LocationOn,
                        signUpViewModel.SignupUIState.value.addressError,
                        onTextSelected = {
                            signUpViewModel.onEvent(SignupEvent.addressChanged(it))
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedPasswordInput(
                        "Password",
                        Icons.Outlined.Lock,
                        signUpViewModel.SignupUIState.value.passwordError,
                        onTextSelected = {
                            signUpViewModel.onEvent(SignupEvent.passwordChanged(it))
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))


                    RadioCheck()
                    Spacer(modifier = Modifier.height(30.dp))

                    ButtonComponent(
                        "Signup", onButtonClicked = {
                            signUpViewModel.onEvent(SignupEvent.SignupButtonClicked)
                        },
                        isEnabled = signUpViewModel.disabled.value
                    )


                    Spacer(modifier = Modifier.height(20.dp))

                    val annotatedText = buildAnnotatedString {
                        append("Already have an account?, Login here")
                    }

                    ClickableText(
                        text = annotatedText, onClick = {
                            navController.navigate(Screen.LoginScreen.route)
                        }, style = TextStyle(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )

                }

            }
        }
    }
}



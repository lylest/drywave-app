package com.example.funs.screens.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.funs.components.*
import com.example.funs.navigation.Screen


@Composable
fun Signup(navController: NavController, signUpViewModel: SignupViewModel = viewModel()) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val scrollState = rememberScrollState()
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
                    onTextSelected = {
                        signUpViewModel.onEvent(SignupEvent.fullNameChanged(it))
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedInput(
                    "Phone",
                    Icons.Outlined.Phone,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignupEvent.phoneChanged(it))
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedInput(
                    "Email",
                    Icons.Outlined.MailOutline,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignupEvent.emailChanged(it))
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedInput(
                    "Address",
                    Icons.Outlined.LocationOn,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignupEvent.addressChanged(it))
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedPasswordInput(
                    "Password",
                    Icons.Outlined.Lock,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignupEvent.passwordChanged(it))
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))


                RadioCheck()
                Spacer(modifier = Modifier.height(30.dp))

                Spacer(modifier = Modifier.height(50.dp))
                ButtonComponent("Signup")


                Spacer(modifier = Modifier.height(100.dp))

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


@Preview
@Composable

fun DefaultSignupPreview() {
    val navController = rememberNavController()
    Signup(navController = navController)
}
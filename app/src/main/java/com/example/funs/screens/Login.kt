package com.example.funs.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.funs.components.*
import com.example.funs.navigation.Screen


@Composable
fun Login(navController: NavController) {
    val scrollState = rememberScrollState()
    Surface(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column {
            NormalText(
                "Welcome back",
                FontWeight.Normal,
                16,
                MaterialTheme.colorScheme.onSurface,
                TextAlign.Center, 12
            )

            Heading(
                "Login to continue",
                22,
                MaterialTheme.colorScheme.primary,
                TextAlign.Center
            )


            Column(
                modifier = Modifier.fillMaxWidth().verticalScroll(scrollState),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedInput("Email", Icons.Outlined.MailOutline)
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedPasswordInput("Password", Icons.Outlined.Lock)
                Spacer(modifier = Modifier.height(50.dp))

                ButtonComponent("Login")


                Spacer(modifier = Modifier.height(50.dp))
                val annotatedText = buildAnnotatedString {
                    append("Don't have an account?, Register here")
                }
                ClickableText(
                    text = annotatedText, onClick = {
                        navController.navigate(Screen.SignupScreen.route)
                    }, modifier = Modifier.height(60.dp),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }

        }
    }
}



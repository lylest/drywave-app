package com.example.funs.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.funs.screens.signup.SignupEvent
import com.example.funs.screens.signup.SignupViewModel

@Composable
fun RadioCheck(signupViewModel: SignupViewModel = viewModel()) {
    var state by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxWidth(0.9f).padding(start = 15.dp)) {
        Text(
            text = "Sex",
            modifier = Modifier.fillMaxWidth().heightIn().padding(top = 0.dp, start = 15.dp),
            style = TextStyle(
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
                fontSize = 17.sp
            ),
            color = MaterialTheme.colorScheme.onSurface
        )

        Row(Modifier.selectableGroup()) {

            RadioButton(
                colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.primary),
                selected = state,
                onClick = {
                    state = true
                    signupViewModel.onEvent(SignupEvent.sexChanged("Male"))
                },
                modifier = Modifier.semantics { contentDescription = "Localized Description" }
            )
            Text(
                text = "Male",
                modifier = Modifier.padding(top = 10.dp, start = 5.dp),
                style = TextStyle(
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )


            RadioButton(
                colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.primary),
                selected = !state,
                onClick = {
                    state = false
                    signupViewModel.onEvent(SignupEvent.sexChanged("Female"))
                },
                modifier = Modifier.semantics { contentDescription = "Localized Description" }
            )
            Text(
                text = "Female",
                modifier = Modifier.padding(top = 10.dp, start = 5.dp),
                style = TextStyle(
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
package com.example.funs.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.material3.SearchBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedInput(label: String, icon: ImageVector, onTextSelected: (String) -> Unit) {

    var textValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    OutlinedTextField(
        value = textValue,
        onValueChange = {
            textValue = it
            onTextSelected(it.toString())
        },
        singleLine = true,
        label = { Text(label) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            // textColor = MaterialTheme.colorScheme.onSurface,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.onSurface
        ),
        leadingIcon = {
            Icon(icon, contentDescription = "user icon")
        },
        modifier = Modifier
            .height(70.dp)
            .padding(top = 10.dp)
            .fillMaxWidth(0.8f)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedPasswordInput(label: String, icon: ImageVector, onTextSelected: (String) -> Unit) {

    var password by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    var passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = password,
        onValueChange = {
            password = it
            onTextSelected(it.toString())
        },
        singleLine = true,
        label = { Text(label) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            //textColor = MaterialTheme.colorScheme.onSurface,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.onSurface
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        leadingIcon = {
            Icon(icon, contentDescription = "user icon")
        },
        trailingIcon = {
            val IconImage = if (passwordVisible.value) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(imageVector = IconImage, contentDescription = "info")
            }
        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier
            .height(70.dp)
            .padding(top = 10.dp)
            .fillMaxWidth(0.8f)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInput() {
    var text by remember { mutableStateOf("") } // Query for SearchBar
    var active by remember { mutableStateOf(false) } // Active state for SearchBar
    val items = remember {
        mutableStateListOf(
            "Alice",
            "James",
            "Cat",
            "Sean"
        )
    }


    Row(
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .padding(end = 12.dp)
            .padding(start = 14.dp)
    ) {

        DockedSearchBar(
            query = text,
            onQueryChange = {
                text = it
            },
            onSearch = {
                active = false
            },
            active = active,
            onActiveChange = {
                active = it
            },
            placeholder = {
                Text(text = "Search orders")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = "SearchIcon")
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (!text.isEmpty()) {
                                text = ""
                                println("text is empty")
                            } else {
                                active = false
                                println("text is not empty $text")
                            }
                        },
                        imageVector = Icons.Outlined.Close, contentDescription = "CloseIcon"
                    )
                }
            },
            colors = SearchBarDefaults.colors(MaterialTheme.colorScheme.surface)
        ) {
            items.forEach {
                Row(modifier = Modifier.padding(all = 14.dp)) {
                    Icon(
                        modifier = Modifier.padding(end = 12.dp),
                        imageVector = Icons.Outlined.History, contentDescription = null
                    )
                    Text(text = it)
                }
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchShop() {
    var text by remember { mutableStateOf("") } // Query for SearchBar
    var active by remember { mutableStateOf(false) } // Active state for SearchBar
    val items = remember {
        mutableStateListOf(
            "Alice",
            "James",
            "Cat",
            "Sean"
        )
    }

    SearchBar(
        query = text,
        onQueryChange = {
            text = it
        },
        onSearch = {
            active = false
        },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = {
            Text(text = "Search orders")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Outlined.Search, contentDescription = "SearchIcon")
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = Modifier.clickable {
                        if (!text.isEmpty()) {
                            text = ""
                            println("text is empty")
                        } else {
                            active = false
                            println("text is not empty $text")
                        }
                    },
                    imageVector = Icons.Outlined.Close, contentDescription = "CloseIcon"
                )
            }
        },
        colors = SearchBarDefaults.colors(MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth().padding(top = 0.dp),
        shape = RoundedCornerShape(9.dp)
    ) {
        items.forEach {
            Row(modifier = Modifier.padding(all = 14.dp)) {
                Icon(
                    modifier = Modifier.padding(end = 12.dp),
                    imageVector = Icons.Outlined.History, contentDescription = null
                )
                Text(text = it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchItems() {
    var text by remember { mutableStateOf("") } // Query for SearchBar
    var active by remember { mutableStateOf(false) } // Active state for SearchBar
    val items = remember {
        mutableStateListOf(
            "Alice",
            "James",
            "Cat",
            "Sean"
        )
    }

    DockedSearchBar(
        query = text,
        onQueryChange = {
            text = it
        },
        onSearch = {
            active = false
        },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = {
            Text(text = "Search items")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Outlined.Search, contentDescription = "SearchIcon")
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = Modifier.clickable {
                        if (!text.isEmpty()) {
                            text = ""
                            println("text is empty")
                        } else {
                            active = false
                            println("text is not empty $text")
                        }
                    },
                    imageVector = Icons.Outlined.Close, contentDescription = "CloseIcon"
                )
            }
        },
        colors = SearchBarDefaults.colors(MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp),
        shape = RoundedCornerShape(9.dp)
    ) {
        items.forEach {
            Row(modifier = Modifier.padding(all = 14.dp)) {
                Icon(
                    modifier = Modifier.padding(end = 12.dp),
                    imageVector = Icons.Outlined.History, contentDescription = null
                )
                Text(text = it)
            }
        }
    }
}












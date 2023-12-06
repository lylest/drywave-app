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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.funs.navigation.Screen
import com.example.funs.screens.home.HomeViewModel
import com.example.funs.screens.home.OrderItem
import com.example.funs.screens.neworder.NewOrderViewModel
import com.example.funs.screens.profile.ProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedInput(
    label: String,
    icon: ImageVector,
    errorStatus: Boolean = true,
    onTextSelected: (String) -> Unit
) {

    val textValue = remember { mutableStateOf("") }

    OutlinedTextField(
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        singleLine = true,
        label = { Text(label) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.onSurface
        ),
        leadingIcon = {
            Icon(icon, contentDescription = "user icon")
        },
        //isError = !errorStatus,
        modifier = Modifier
            .height(70.dp)
            .padding(top = 10.dp)
            .fillMaxWidth(0.8f)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedPasswordInput(
    label: String,
    icon: ImageVector,
    errorStatus: Boolean = true,
    onTextSelected: (String) -> Unit
) {

    var password = remember { mutableStateOf("") }
    var passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = password.value,
        onValueChange = {
            password.value = it
            onTextSelected(it)
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
        //isError = !errorStatus,
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier
            .height(70.dp)
            .padding(top = 10.dp)
            .fillMaxWidth(0.8f)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInput(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel()
) {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val userId by profileViewModel.userId.observeAsState()
    val currentUserToken by profileViewModel.currentUserToken.observeAsState()
    val tokenWithBearer = "Bearer $currentUserToken"
    val items  by homeViewModel.customerOrders.observeAsState()

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
                homeViewModel.searchOrders(tokenWithBearer, userId!!, it)
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
                    Icon( modifier = Modifier.clickable {
                            if (!text.isEmpty()) {
                                active = false
                            } else {
                                active = false
                            }
                        },
                        imageVector = Icons.Outlined.Close, contentDescription = "CloseIcon"
                    )
                }
            },
            colors = SearchBarDefaults.colors(MaterialTheme.colorScheme.surface)
        ) {
            items?.forEach { order ->
                Row(modifier = Modifier
                    .padding(all = 14.dp)
                    .clickable {  navController.navigate(Screen.ViewOrder.withArgs(order._id)) }
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 12.dp),
                        imageVector = Icons.Outlined.DryCleaning, contentDescription = null
                    )
                    Text(text = order.trackingId)
                }
            }
        }

    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchShop(
    newOrderViewModel: NewOrderViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel()
) {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val sampleShopsList = newOrderViewModel.sampleShops
    val userId by profileViewModel.userId.observeAsState()
    val currentUserToken by profileViewModel.currentUserToken.observeAsState()


    SearchBar(
        query = text,
        onQueryChange = {
            text = it
            if (userId != null && currentUserToken != null) {
                val tokenWithBearer = "Bearer $currentUserToken"
                if (it.isNotEmpty()) {
                    newOrderViewModel.searchShops(tokenWithBearer, it)
                } else {
                    newOrderViewModel.getSampleShops(tokenWithBearer)
                }
            }
        },
        onSearch = {
            active = false
        },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = {
            Text(text = "Search shop")
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
        sampleShopsList.forEach {
            Row(
                modifier = Modifier
                    .padding(all = 14.dp)
                    .clickable {
                        newOrderViewModel.selectShop(it._id)
                    }
            ) {
                Icon(
                    modifier = Modifier.padding(end = 12.dp),
                    imageVector = Icons.Outlined.Storefront, contentDescription = null
                )
                Text(text = it.name)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchItems(
    newOrderViewModel: NewOrderViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel()
) {
    var text by remember { mutableStateOf("") } // Query for SearchBar
    var active by remember { mutableStateOf(false) } // Active state for SearchBar

    val userId by profileViewModel.userId.observeAsState()
    val currentUserToken by profileViewModel.currentUserToken.observeAsState()
    val itemsList by newOrderViewModel.itemsList.observeAsState()

    DockedSearchBar(
        query = text,
        onQueryChange = {
            text = it
            if (userId != null && currentUserToken != null) {
                val tokenWithBearer = "Bearer $currentUserToken"
                if (it.isNotEmpty()) {
                    newOrderViewModel.searchItems(tokenWithBearer, it)
                } else {

                }
            }
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
        itemsList?.forEach { item ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, end = 14.dp, top = 7.dp)
                .clickable {
                    active = false
                    newOrderViewModel.addDescription(item)
                }
            ) {
                Icon(
                    modifier = Modifier.padding(end = 12.dp, top = 12.dp, bottom = 12.dp),
                    imageVector = Icons.Outlined.DryCleaning, contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
                    text = item.item
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionInput(
    label: String,
    onTextSelected: (String) -> Unit
) {

    val textValue = remember { mutableStateOf("") }

    OutlinedTextField(
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        singleLine = true,
        label = { Text(label) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier
            .height(120.dp)
            .padding(top = 1.dp, start = 25.dp)
            .fillMaxWidth(0.9f)
    )
}













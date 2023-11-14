package com.example.funs.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.funs.R
import com.example.funs.components.MenuIcon
import kotlinx.coroutines.Dispatchers

@Composable
fun Profile(navController: NavController, profileViewModel: ProfileViewModel = viewModel()) {
    val scrollStateOrder = rememberScrollState()
    val userId by profileViewModel.userId.observeAsState()
    val currentUserToken by profileViewModel.currentUserToken.observeAsState()
    var customer = profileViewModel.customer.value

    LaunchedEffect(key1 = userId) {
        if (userId != null && currentUserToken != null) {
            val tokenWithBearer = "Bearer $currentUserToken"
            profileViewModel.getCustomerFunction(userId, tokenWithBearer)
        }
    }

    if(customer._id.isEmpty()) { IndeterminateCircularIndicator() } else {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Text(
                    text = "Profile",
                    modifier = Modifier.fillMaxWidth().heightIn().padding(top = 15.dp, start = 24.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(scrollStateOrder)
            ) {


                UserBar(customer.name, customer.phoneNumber)
                MenuIcon(
                    Icons.Outlined.Person,
                    customer.name,
                    "",
                    MaterialTheme.colorScheme.outline,
                    false
                )

                MenuIcon(
                    Icons.Outlined.Phone,
                    customer.phoneNumber,
                    "",
                    MaterialTheme.colorScheme.outline,
                    false
                )

                MenuIcon(
                    Icons.Outlined.AlternateEmail,
                    customer.email,
                    "",
                    MaterialTheme.colorScheme.outline,
                    false
                )

                MenuIcon(
                    Icons.Outlined.LocationOn,
                    customer.address,
                    "",
                    MaterialTheme.colorScheme.outline,
                    false
                )

                MenuIcon(
                    Icons.Outlined.Transgender,
                    customer.sex,
                    "",
                    MaterialTheme.colorScheme.outline,
                    false
                )

                Spacer(modifier = Modifier.height(80.dp))
                MenuIcon(
                    Icons.Outlined.Info,
                    "About app",
                    "",
                    MaterialTheme.colorScheme.outline,
                    false
                )
                MenuIcon(
                    Icons.Outlined.Info,
                    "Version 1.0.0",
                    "",
                    MaterialTheme.colorScheme.outline,
                    false
                )
                MenuIcon(
                    Icons.Outlined.Gavel,
                    "Terms and conditions",
                    "",
                    MaterialTheme.colorScheme.outline,
                    false
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.94f)
                        .padding(start = 24.dp, top = 4.dp)
                ) {

                    FilledTonalButton(
                        onClick = { profileViewModel.logOutFunction() },
                        modifier = Modifier.padding(10.dp),
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(MaterialTheme.colorScheme.error)
                    ) {
                        Text(
                            "Logout",
                            style = TextStyle(
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp
                            ),
                            color = MaterialTheme.colorScheme.onError
                        )
                    }

                }
                Spacer(modifier = Modifier.height(80.dp))
            }

        }
    }

}

@Composable
fun IndeterminateCircularIndicator() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}


@Composable
fun UserBar(customerName:String, customerPhone: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 30.dp, start = 15.dp)
                    .width(80.dp)
                    .height(80.dp)
                    .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(40.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentScale = ContentScale.Crop,
                    contentDescription = "order icon",
                    modifier = Modifier.width(80.dp).height(80.dp)
                )
            }
            Column(modifier = Modifier.padding(start = 110.dp)) {
                Text(
                    text = customerName,
                    modifier = Modifier.padding(top = 40.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "Tell: $customerPhone",
                    modifier = Modifier.fillMaxWidth().heightIn().padding(top = 5.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    ),
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}
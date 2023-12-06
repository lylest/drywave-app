package com.example.funs.screens.home


import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.funs.R
import com.example.funs.components.SearchInput
import com.example.funs.navigation.Screen
import com.example.funs.screens.neworder.NewOrderViewModel
import com.example.funs.screens.profile.IndeterminateCircularIndicator
import com.example.funs.screens.profile.ProfileViewModel
import com.example.funs.screens.vieworder.ShopLogo
import com.example.funs.utils.Utils
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun Home(
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel()
) {
    val scrollStateOrder = rememberScrollState()
    var isVisible by remember { mutableStateOf(false) }
    var customer = profileViewModel.customer.value
    val sampleServices by homeViewModel.sampleServices.observeAsState()
    val userId by profileViewModel.userId.observeAsState()
    val currentUserToken by profileViewModel.currentUserToken.observeAsState()
    val customerOrders by homeViewModel.customerOrders.observeAsState()

    LaunchedEffect(key1 = userId) {
        if (userId != null && currentUserToken != null) {
            val tokenWithBearer = "Bearer $currentUserToken"
            profileViewModel.getCustomerFunction(userId, tokenWithBearer)
            homeViewModel.getSampleServices(tokenWithBearer)
        }
    }

    LaunchedEffect(key1 = userId) {
        if (userId != null && currentUserToken != null) {
            val tokenWithBearer = "Bearer $currentUserToken"
            homeViewModel.getCustomerOrders(tokenWithBearer, userId!!)
        }
    }


    val context = LocalContext.current
    if (homeViewModel.showToast.value) {
        LaunchedEffect(key1 = 1) {
            Toast.makeText(context, homeViewModel.message.value, Toast.LENGTH_SHORT).show()
        }
    }


    fun openDialog() {
        isVisible = true
    }


    if (customer._id.isEmpty()) {
        IndeterminateCircularIndicator()
    } else {
        val refreshScope = rememberCoroutineScope()
        var refreshing = homeViewModel.refreshing.value

        fun refresh() = refreshScope.launch {
            if (userId != null && currentUserToken != null) {
                val tokenWithBearer = "Bearer $currentUserToken"
                homeViewModel.getCustomerOrders(tokenWithBearer, userId!!)
            }
        }

        val state = rememberPullRefreshState(refreshing, ::refresh)
        Box(Modifier.pullRefresh(state)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(scrollStateOrder)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Welcome back",
                        modifier = Modifier.fillMaxWidth().heightIn().padding(top = 30.dp, start = 24.dp),
                        style = TextStyle(
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Light,
                            fontSize = 20.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = customer.name,
                        modifier = Modifier.fillMaxWidth().heightIn().padding(top = 0.dp, start = 24.dp),
                        style = TextStyle(
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.heightIn(30.dp))
                    Row {
                        SearchInput (navController)

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .width(60.dp)
                                .height(60.dp)
                                .padding(0.dp)
                                .background(MaterialTheme.colorScheme.surface, shape = CircleShape)
                                .clickable {
                                    openDialog()
                                }
                        ) {

                            Icon(
                                imageVector = Icons.Outlined.Tune,
                                tint = MaterialTheme.colorScheme.onBackground,
                                contentDescription = null
                            )
                        }
                    }

                    Text(
                        text = "Services",
                        modifier = Modifier.fillMaxWidth().heightIn().padding(top = 17.dp, start = 24.dp),
                        style = TextStyle(
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.padding(top = 20.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.94f).padding(start = 24.dp)
                ) {
                    val scrollState = rememberScrollState()
                    Row(
                        modifier = Modifier
                            .horizontalScroll(scrollState)
                    ) {
                        sampleServices?.forEach { service ->
                            ServiceType(service.icon.path, service.name, {}, false)
                        }
                    }

                    Row() {
                        Text(
                            text = "Active orders",
                            modifier = Modifier.fillMaxWidth().heightIn().padding(top = 17.dp, bottom = 20.dp),
                            style = TextStyle(
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Medium,
                                fontSize = 20.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Column() {
                        customerOrders?.forEach { order ->
                            OrderCard(navController, order)
                        }
                    }
                    Spacer(modifier = Modifier.height(80.dp))

                }

                if (isVisible) {
                    MinimalDialog(onDismissRequest = { isVisible = false }, handleDateSelect = { datePickerState ->
                        datePickerState.selectedDateMillis?.let {
                            val tokenWithBearer = "Bearer $currentUserToken"
                            homeViewModel.filterOrders(tokenWithBearer, userId!!, it)
                            isVisible = false
                        }
                    })
                }

            }
            PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinimalDialog(
    onDismissRequest: () -> Unit,
    handleDateSelect: (DatePickerState) -> Unit) {
    val datePickerState = rememberDatePickerState()
    val confirmEnabled = derivedStateOf<Boolean> { datePickerState.selectedDateMillis != null }

    DatePickerDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = {
                    handleDateSelect(datePickerState)
                },
                enabled = confirmEnabled.value
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() }
            ) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderCard(
    navController: NavController,
    order: OrderItem,
    homeViewModel: HomeViewModel = viewModel(),
    newOrderViewModel: NewOrderViewModel = viewModel()
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        onClick = {
            newOrderViewModel.orderId.value = order._id
            navController.navigate(Screen.ViewOrder.withArgs(order._id))
        }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CircularProgress(order.percentage, order.orderStatus)
            Column(modifier = Modifier.padding(start = 110.dp)) {
                Text(
                    text = order.trackingId,
                    modifier = Modifier.padding(top = 20.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "Date: ${homeViewModel.formatDateString(order.dateReceived)}",
                    modifier = Modifier.padding(top = 5.dp, bottom = 6.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    color = MaterialTheme.colorScheme.outline
                )

                Row {
                    OrderStatusTag(order.orderStatus)
                    Text(
                        text = NumberFormat.getNumberInstance(Locale.US).format(order.totalCost),
                        modifier = Modifier.padding(top = 4.dp, start = 8.dp),
                        style = TextStyle(
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                }
            }
        }
        Divider(modifier = Modifier.fillMaxWidth().height(0.5.dp).background(MaterialTheme.colorScheme.surface))
    }
}


@Composable
fun CircularProgress(
    percentage: Float,
    orderStatus: String,
    imageUrl: String = "empty",
    radius: Dp = 60.dp,
    strokeWidth: Dp = 2.dp,
    animationDuration: Int = 1000,
    animationDelay: Int = 2,
) {
    val colors = listOf(
        "#FFBD49", "#FD6822", "#DF2D4F", "#DF2D4F", "#1E8D5F", "#276BC0"
    )

    val index = when (orderStatus.lowercase()) {
        "placed" -> 0
        "waiting-confirmation" -> 1
        "canceled" -> 2
        "deleted" -> 3
        "checked-out" -> 4
        "ready" -> 5
        else -> 0 // Default to Placed for unknown status
    }

    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f, label = "",
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        modifier = Modifier.size(radius * 2f),
        contentAlignment = Alignment.Center
    ) {
        if (imageUrl == "empty") {
            ImageCard()
        } else {
            ShopLogo(imageUrl)
        }
        Canvas(modifier = Modifier.size(radius * 2f).padding(24.dp)) {
            drawArc(
                color = Color(android.graphics.Color.parseColor(colors[index])),
                -90f,
                360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
            )

        }
    }

}


@Composable
fun ImageCard() {
    Image(
        painter = painterResource(id = R.drawable.order),
        contentScale = ContentScale.Crop,
        contentDescription = "order icon",
        modifier = Modifier.width(50.dp).height(50.dp)
    )
}


@Composable
fun ServiceType(imageUrl: String, name: String, onSelect: () -> Unit, isSelected: Boolean) {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .widthIn(120.dp)
            .heightIn(100.dp)
            .padding(top = 5.dp, start = 0.dp, end = 5.dp)
            .border(
                width = 2.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                onSelect()
            },
        shape = RoundedCornerShape(12.dp)
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("${Utils.baseUrl}${imageUrl}")
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.wash_and_dry),
            contentDescription = stringResource(R.string.app_name),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .padding(15.dp),
        )
        Text(
            text = name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
                .width(95.dp),
            style = TextStyle(
                fontWeight = FontWeight.Medium,

                )
        )
    }
}

@Composable
fun OrderStatusTag(orderStatus: String) {
    val colors = listOf(
        "#FFBD49", "#FD6822", "#DF2D4F", "#DF2D4F", "#1E8D5F", "#276BC0"
    )

    val index = when (orderStatus.lowercase()) {
        "placed" -> 0
        "waiting-confirmation" -> 1
        "canceled" -> 2
        "deleted" -> 3
        "checked-out" -> 4
        "ready" -> 5
        else -> 0 // Default to Placed for unknown status
    }

    val textColor = Color(android.graphics.Color.parseColor(colors[index]))
    val backgroundColor = textColor.copy(alpha = 0.2f)


    Box(
        modifier = Modifier
            .background(backgroundColor, shape = CircleShape)
            .padding(4.dp)
            .clip(CircleShape)
    ) {
        Text(
            text = orderStatus.replaceFirstChar { it.uppercase() },
            color = textColor,
            modifier = Modifier.padding(horizontal = 4.dp),
            style = TextStyle(
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
        )
    }
}


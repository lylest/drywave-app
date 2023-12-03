package com.example.funs.screens.vieworder


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.funs.R
import com.example.funs.components.MenuItem
import com.example.funs.screens.home.CircularProgress
import com.example.funs.screens.home.HomeViewModel
import com.example.funs.screens.neworder.DashedDivider
import com.example.funs.screens.neworder.PieceBar
import com.example.funs.screens.profile.IndeterminateCircularIndicator
import com.example.funs.screens.profile.ProfileViewModel
import com.example.funs.utils.Utils
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ViewOrder(
    orderId:String?,
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel(),
    orderViewModel: OrderViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel()
) {
    val scrollStateOrder = rememberScrollState()
    var order = orderViewModel.order.value
    val userId by profileViewModel.userId.observeAsState()
    val currentUserToken by profileViewModel.currentUserToken.observeAsState()

    LaunchedEffect(key1 = userId, key2 = orderId) {
        if (userId != null && currentUserToken != null) {
            val tokenWithBearer = "Bearer $currentUserToken"

            if(orderId !== null) {
                orderViewModel.getOrder(orderId!!, tokenWithBearer)
            }
        }
    }


    val context = LocalContext.current
    if (orderViewModel.showToast.value) {
        LaunchedEffect(key1 = 1) {
            Toast.makeText(context, orderViewModel.message.value, Toast.LENGTH_SHORT).show()
        }
    }

   if(order !== null) {
       val refreshScope = rememberCoroutineScope()
       var refreshing = orderViewModel.refreshing.value

       fun refresh() = refreshScope.launch {
           if (userId != null && currentUserToken != null) {
               val tokenWithBearer = "Bearer $currentUserToken"
               if(orderId !== null) {
                   orderViewModel.getOrder(orderId, tokenWithBearer)
               }
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
               Text(
                   text = "Order details",
                   modifier = Modifier.fillMaxWidth().heightIn().padding(top = 30.dp, start = 24.dp),
                   style = TextStyle(
                       textAlign = TextAlign.Left,
                       fontWeight = FontWeight.Light,
                       fontSize = 20.sp
                   ),
                   color = MaterialTheme.colorScheme.onSurface
               )
               Text(
                   text = order.trackingId,
                   modifier = Modifier.fillMaxWidth().heightIn().padding(top = 0.dp, start = 24.dp),
                   style = TextStyle(
                       textAlign = TextAlign.Left,
                       fontWeight = FontWeight.Medium,
                       fontSize = 20.sp
                   ),
                   color = MaterialTheme.colorScheme.onSurface
               )

               Text(
                   text = "Shop",
                   modifier = Modifier.fillMaxWidth().heightIn().padding(top = 17.dp, start = 24.dp),
                   style = TextStyle(
                       textAlign = TextAlign.Left,
                       fontWeight = FontWeight.Bold,
                       fontSize = 20.sp
                   ),
                   color = MaterialTheme.colorScheme.onSurface
               )

               ShopBar(order)

               Text(
                   text = "Service",
                   modifier = Modifier.fillMaxWidth().heightIn().padding(top = 17.dp, start = 24.dp),
                   style = TextStyle(
                       textAlign = TextAlign.Left,
                       fontWeight = FontWeight.Bold,
                       fontSize = 20.sp
                   ),
                   color = MaterialTheme.colorScheme.onSurface
               )

               ServiceSelected(order.service.name)
               MenuItem(
                   Icons.Outlined.Loyalty,
                   "Tag No.",
                   order.tagId.toString(),
                   MaterialTheme.colorScheme.outline
               )

               MenuItem(
                   Icons.Outlined.QrCode,
                   "Tracking Id",
                   order.trackingId,
                   MaterialTheme.colorScheme.outline
               )

              if(order.paymentStatus == "full-paid") {
                  MenuItem(
                      Icons.Outlined.ReceiptLong,
                      "Checkout Code",
                      order.checkoutCode,
                      MaterialTheme.colorScheme.outline
                  )
              }

               MenuItem(
                   Icons.Outlined.CalendarMonth,
                   "Date placed",
                   homeViewModel.formatDateString(order.dateReceived),
                   MaterialTheme.colorScheme.outline
               )

               MenuItem(
                   Icons.Outlined.CalendarMonth,
                   "Ready date",
                   homeViewModel.formatDateString(order.readyDate),
                   MaterialTheme.colorScheme.outline
               )

               MenuItem(
                   Icons.Outlined.DryCleaning,
                   "Service",
                   order.service.name,
                   MaterialTheme.colorScheme.outline
               )

               MenuItem(
                   Icons.Outlined.DonutLarge,
                   "Progress",
                   order.progress,
                   MaterialTheme.colorScheme.outline
               )
               MenuItem(
                   Icons.Outlined.Circle,
                   "Status",
                   order.orderStatus.replaceFirstChar { it.uppercase() },
                   MaterialTheme.colorScheme.outline
               )

               Text(
                   text = "Payment details",
                   modifier = Modifier.fillMaxWidth().heightIn().padding(top = 17.dp, start = 24.dp),
                   style = TextStyle(
                       textAlign = TextAlign.Left,
                       fontWeight = FontWeight.Bold,
                       fontSize = 20.sp
                   ),
                   color = MaterialTheme.colorScheme.onSurface
               )

               MenuItem(
                   Icons.Outlined.CreditCard,
                   "Payment method",
                   order.paymentMethod.replaceFirstChar { it.uppercase() },
                   MaterialTheme.colorScheme.outline
               )

               MenuItem(
                   Icons.Outlined.LocalAtm,
                   "Payment status",
                   order.paymentStatus.replaceFirstChar { it.uppercase() },
                   MaterialTheme.colorScheme.outline
               )

               MenuItem(
                   Icons.Outlined.LocalAtm,
                   "Amount paid",
                   NumberFormat.getNumberInstance(Locale.US).format(order.amountPaid),
                   MaterialTheme.colorScheme.outline
               )

               MenuItem(
                   Icons.Outlined.LocalAtm,
                   "Amount remaining",
                   NumberFormat.getNumberInstance(Locale.US).format(order.totalCost - order.amountPaid),
                   MaterialTheme.colorScheme.outline
               )

               MenuItem(
                   Icons.Outlined.LocalAtm,
                   "Discount",
                   NumberFormat.getNumberInstance(Locale.US).format(order.discount),
                   MaterialTheme.colorScheme.outline
               )

               Text(
                   text = "Items",
                   modifier = Modifier.fillMaxWidth().heightIn().padding(top = 17.dp, start = 24.dp),
                   style = TextStyle(
                       textAlign = TextAlign.Left,
                       fontWeight = FontWeight.Bold,
                       fontSize = 20.sp
                   ),
                   color = MaterialTheme.colorScheme.onSurface
               )
               Column(
                   modifier = Modifier
                       .fillMaxWidth(0.94f)
                       .padding(start = 24.dp, top = 4.dp)
               ) {
                 order.pieces.forEach{ piece ->
                     PieceBar(piece, false)
                 }

                   Spacer(modifier = Modifier.height(30.dp))
                   DashedDivider(
                       color = MaterialTheme.colorScheme.outline,
                       thickness = 1.dp,
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(16.dp)
                   )

                   TotalPiecesBar(order)
                   Spacer(modifier = Modifier.height(30.dp))
               }

               Text(
                   text = "Provisional inspections",
                   modifier = Modifier.fillMaxWidth().heightIn().padding(top = 17.dp, start = 24.dp),
                   style = TextStyle(
                       textAlign = TextAlign.Left,
                       fontWeight = FontWeight.Bold,
                       fontSize = 20.sp
                   ),
                   color = MaterialTheme.colorScheme.onSurface
               )

               order.defects.forEach { defect ->
                   MenuItem(
                       Icons.Outlined.CheckBox,
                       defect.name,
                       "",
                       MaterialTheme.colorScheme.primary
                   )
               }

               MenuItem(
                   Icons.Outlined.CheckBox,
                   "Tears evide",
                   "",
                   MaterialTheme.colorScheme.primary
               )


               Text(
                   text = "Menu",
                   modifier = Modifier.fillMaxWidth().heightIn().padding(top = 17.dp, start = 24.dp),
                   style = TextStyle(
                       textAlign = TextAlign.Left,
                       fontWeight = FontWeight.Bold,
                       fontSize = 20.sp
                   ),
                   color = MaterialTheme.colorScheme.onSurface
               )

               Row(
                   modifier = Modifier
                       .fillMaxWidth(0.94f)
                       .padding(start = 24.dp, top = 4.dp)
               ) {

                   FilledTonalButton(
                       onClick = { },
                       modifier = Modifier.padding(10.dp),
                       shape = RoundedCornerShape(6.dp),
                       colors = ButtonDefaults.filledTonalButtonColors(MaterialTheme.colorScheme.errorContainer)
                   ) {
                       Text("Cancel order",
                           style = TextStyle(
                               textAlign = TextAlign.Left,
                               fontWeight = FontWeight.Normal,
                               fontSize = 15.sp
                           ),
                           color = MaterialTheme.colorScheme.onErrorContainer
                       )
                   }


                   Button(
                       onClick = { },
                       shape = RoundedCornerShape(6.dp),
                       modifier = Modifier.padding(10.dp),
                       // colors = ButtonDefaults.FilledButtonColors(MaterialTheme.colorScheme.primaryContainer)
                   ) {
                       Text(
                           "Edit order",
                           style = TextStyle(
                               textAlign = TextAlign.Left,
                               fontWeight = FontWeight.Normal,
                               fontSize = 15.sp
                           ),
                           color = MaterialTheme.colorScheme.onPrimary
                       )
                   }

               }

               Spacer(modifier = Modifier.height(70.dp))

           }
           PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
       }

   } else {
       IndeterminateCircularIndicator()
   }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceSelected(service:String) {
    var selected by remember { mutableStateOf(true) }

    FilterChip(
        onClick = { selected = !selected },
        label = {
            Text(service)
        },
        selected = selected,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = "Done icon",
                modifier = Modifier.size(FilterChipDefaults.IconSize)
            )
        },
        modifier = Modifier.padding(start = 20.dp)
    )
}

@Composable
fun ShopBar(order:OrderDetails) {

    fun calculateProgressPercentage(progress: String): Float {
        return when (progress.lowercase()) {
            "pre-inspection" -> 0.1F
            "tagging" -> 0.16F
            "dry-cleaning-machine" -> 0.32F
            "drying" -> 0.48F
            "pressing" -> 0.64F
            "packing" -> 0.8F
            "ready for pickup" -> 1.0F
            else -> -1.0F
        }
    }



    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CircularProgress(calculateProgressPercentage(order.progress), order.orderStatus, order.shop.logo.path)
            Column(modifier = Modifier.padding(start = 110.dp)) {
                Text(
                    text = order.shop.name,
                    modifier = Modifier.padding(top = 24.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = order.shop.address,
                    modifier = Modifier.padding(top = 5.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    color = MaterialTheme.colorScheme.outline
                )


                Text(
                    text = "Tell: ${order.shop.contacts[0]}",
                    modifier = Modifier.fillMaxWidth().heightIn().padding(top = 5.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
fun ShopLogo(imageUrl:String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("${Utils.baseUrl}${imageUrl}")
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.order),
        contentDescription = stringResource(R.string.app_name),
        contentScale = ContentScale.Crop,
        modifier = Modifier.width(50.dp).height(50.dp)
    )
}


@Composable
fun TotalPiecesBar(
    order:OrderDetails
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
        ) {
            Text(
                text = "QUANTITY",
                modifier = Modifier.padding(top = 4.dp, start = 10.dp, bottom = 0.dp),
                style = TextStyle(
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp
                ),
                color = MaterialTheme.colorScheme.outline
            )
            Text(
                text = "${order.totalPieces} Pieces",
                modifier = Modifier
                    .padding(top = 30.dp, start = 10.dp, bottom = 5.dp),
                style = TextStyle(
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f),
        ) {
            Text(
                text = "TOTAL",
                modifier = Modifier.padding(top = 4.dp, start = 10.dp, bottom = 0.dp),
                style = TextStyle(
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp
                ),
                color = MaterialTheme.colorScheme.outline
            )
            Text(
                text = NumberFormat.getNumberInstance(Locale.US).format(order.totalCost),
                modifier = Modifier
                    .padding(top = 30.dp, start = 10.dp, bottom = 5.dp),
                style = TextStyle(
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
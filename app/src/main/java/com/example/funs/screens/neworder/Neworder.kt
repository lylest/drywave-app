package com.example.funs.screens.neworder

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.funs.R
import com.example.funs.components.SearchItems
import com.example.funs.components.SearchShop
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.funs.components.ButtonAdd
import com.example.funs.components.DescriptionInput
import com.example.funs.screens.home.ServiceType
import com.example.funs.screens.profile.ProfileViewModel
import com.example.funs.utils.Utils
import java.text.NumberFormat
import java.util.*


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun NewOrder(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel(),
    newOrderViewModel: NewOrderViewModel = viewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val sheetState = rememberModalBottomSheetState()
    val sampleShopsList = newOrderViewModel.sampleShops
    var showBottomSheet by remember { mutableStateOf(false) }
    val userId by profileViewModel.userId.observeAsState()
    val currentUserToken by profileViewModel.currentUserToken.observeAsState()
    val servicesList by newOrderViewModel.servicesList.observeAsState()
    val piecesList by newOrderViewModel.pieces.observeAsState()


    LaunchedEffect(key1 = userId) {
        if (userId != null && currentUserToken != null) {
            val tokenWithBearer = "Bearer $currentUserToken"
            newOrderViewModel.getSampleShops(tokenWithBearer)
        }
    }

    LaunchedEffect(key1 = newOrderViewModel.selectedShop.value) {
        if (userId != null && currentUserToken != null) {
            val tokenWithBearer = "Bearer $currentUserToken"

            if (newOrderViewModel.selectedShop.value !== "empty") {
                newOrderViewModel.getShopServices(tokenWithBearer, newOrderViewModel.selectedShop.value)
            }
        }
    }

    if(newOrderViewModel.orderId.value !== "empty") {
        LaunchedEffect(key1 = 1) {
            navController.navigate("view_order")
        }
    }

    val context = LocalContext.current
    if (newOrderViewModel.showToast.value) {
        LaunchedEffect(key1 = 1) {
            Toast.makeText(context, newOrderViewModel.message.value, Toast.LENGTH_SHORT).show()
        }
    }


    fun toggleBottomSheet() {
        showBottomSheet = true
    }


    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                ) {
                    SearchShop()
                }
            }

        }
    }

    val scrollStateOrder = rememberScrollState()
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Text(
                text = "New order",
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
                .fillMaxWidth(0.94f)
                .fillMaxHeight()
                .verticalScroll(scrollStateOrder)
                .padding(start = 24.dp, top = 20.dp)
        ) {
            Text(
                text = "Select shop",
                modifier = Modifier.fillMaxWidth().heightIn().padding(top = 0.dp, start = 0.dp),
                style = TextStyle(
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Row {
                SearchCard { toggleBottomSheet() }
                LazyRow() {
                    items(sampleShopsList) { shop ->
                        ShopCard(
                            shop.logo.path,
                            shop.name, shop._id,
                            shop._id == newOrderViewModel.selectedShop.value,
                            onSelect = {
                                newOrderViewModel.selectedShop.value = shop._id
                            }
                        )
                    }
                }
            }


            if (newOrderViewModel.selectedShop.value != "empty") {
                Text(
                    text = "Pick a service",
                    modifier = Modifier.fillMaxWidth().heightIn().padding(top = 10.dp, start = 0.dp, bottom = 5.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            val scrollState = rememberScrollState()
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
            ) {
                servicesList?.forEach { service ->
                    ServiceType(
                        service.icon.path,
                        service.name,
                        { newOrderViewModel.selectService(service._id, service.code) },
                        newOrderViewModel.selectedService.value == service._id
                    )
                }
            }


            if (newOrderViewModel.selectedService.value !== "empty") {
                Text(
                    text = "Add items",
                    modifier = Modifier.fillMaxWidth().heightIn().padding(top = 25.dp, start = 0.dp, bottom = 14.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                SearchItems()
            }

            if (newOrderViewModel.isVisible.value) {
                DescriptionDialog ({ newOrderViewModel.isVisible.value = false })
            }

            Spacer(modifier = Modifier.height(15.dp))
            piecesList?.forEach { piece ->
                PieceBar(piece)
            }


            Spacer(modifier = Modifier.height(30.dp))
            DashedDivider(
                color = MaterialTheme.colorScheme.outline,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            TotalPieces()
            Spacer(modifier = Modifier.height(30.dp))

            ButtonAdd(
                "Place order",
                newOrderViewModel.total.value <= 0
            )
            Spacer(modifier = Modifier.height(70.dp))
        }
    }

}

@Composable
fun DashedDivider(
    thickness: Dp,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    phase: Float = 10f,
    intervals: FloatArray = floatArrayOf(10f, 10f),
    modifier: Modifier
) {
    Canvas(
        modifier = modifier
    ) {
        val dividerHeight = thickness.toPx()
        drawRoundRect(
            color = color,
            style = Stroke(
                width = dividerHeight,
                pathEffect = PathEffect.dashPathEffect(
                    intervals = intervals,
                    phase = phase
                )
            )
        )
    }
}

@Composable
fun TotalPieces(
    newOrderViewModel: NewOrderViewModel = viewModel()
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
                text = "${newOrderViewModel.quantity.value} Pieces",
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
                text = NumberFormat.getNumberInstance(Locale.US).format(newOrderViewModel.total.value),
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

@Composable
fun PieceBar(
    piece:Piece,
    adjustable:Boolean = true,
    newOrderViewModel: NewOrderViewModel = viewModel()
 ) {
    Row(
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .widthIn(80.dp)
                .heightIn(80.dp)
                .padding(top = 5.dp, start = 0.dp, end = 5.dp)
                .background(
                    MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(6.dp)
                    //.clickable()
                ),
        ) {
            Icon(
                imageVector = Icons.Outlined.DryCleaning,
                tint = Color(android.graphics.Color.parseColor(piece.color.code)),
                contentDescription = null,
                modifier = Modifier.width(50.dp).height(50.dp)
            )
        }

        Column {
            Text(
                text = piece.name,
                modifier = Modifier
                    .padding(top = 10.dp, start = 10.dp, bottom = 0.dp),
                style = TextStyle(
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "${NumberFormat.getNumberInstance(Locale.US).format(piece.pieces)} pieces",
                modifier = Modifier.padding(top = 4.dp, start = 10.dp, bottom = 0.dp),
                style = TextStyle(
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                ),
                color = MaterialTheme.colorScheme.outline
            )

            Row {
                Text(
                    text =  NumberFormat.getNumberInstance(Locale.US).format(piece.unitPrice),
                    modifier = Modifier
                        .padding(top = 4.dp, start = 10.dp, bottom = 5.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "per item",
                    modifier = Modifier.padding(top = 4.dp, start = 10.dp, bottom = 5.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

       if(adjustable) {
           Row {
               Box(
                   contentAlignment = Alignment.Center,
                   modifier = Modifier
                       .width(55.dp)
                       .height(55.dp)
                       .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 0.dp)
                       .background(MaterialTheme.colorScheme.primaryContainer, shape = CircleShape)
                       .clickable {  newOrderViewModel.decrementQuantity(piece) }
               ) {
                   Icon(
                       imageVector = Icons.Outlined.Remove,
                       tint = MaterialTheme.colorScheme.onPrimaryContainer,
                       contentDescription = null
                   )
               }

               Text(
                   text =  NumberFormat.getNumberInstance(Locale.US).format(piece.quantity),
                   modifier = Modifier.padding(top = 22.dp, start = 0.dp, bottom = 5.dp),
                   style = TextStyle(
                       textAlign = TextAlign.Left,
                       fontWeight = FontWeight.Normal,
                       fontSize = 18.sp
                   ),
                   color = MaterialTheme.colorScheme.onBackground
               )

               Box(
                   contentAlignment = Alignment.Center,
                   modifier = Modifier
                       .width(55.dp)
                       .height(55.dp)
                       .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 0.dp)
                       .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                       .clickable { newOrderViewModel.incrementQuantity(piece) }
               ) {
                   Icon(
                       imageVector = Icons.Outlined.Add,
                       tint = MaterialTheme.colorScheme.onPrimary,
                       contentDescription = null
                   )
               }
           }
       }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCard(toogleBottomSheet: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.widthIn(100.dp).heightIn(100.dp).padding(top = 5.dp, start = 0.dp, end = 5.dp),
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier.widthIn(50.dp).heightIn(25.dp).padding(top = 5.dp, start = 0.dp, end = 5.dp),
            shape = RoundedCornerShape(25.dp),
            onClick = {
                toogleBottomSheet()
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search shop",
                modifier = Modifier.padding(12.dp)
            )
        }

        Text(
            text = "Search shop",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
                .width(90.dp),
            style = TextStyle(fontWeight = FontWeight.Normal)
        )
    }


}

@Composable
fun ShopCard(imageUrl: String, name: String, shopId: String, isSelected: Boolean, onSelect: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .widthIn(100.dp)
            .heightIn(100.dp)
            .padding(top = 5.dp, start = 0.dp, end = 5.dp)
            .clickable {
                onSelect()
            },
    ) {
        val opacity = if (isSelected) 1f else 1f
        Card(
            modifier = Modifier.width(50.dp).height(50.dp),
            shape = RoundedCornerShape(25.dp),
        ) {

            if (isSelected) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 5.dp, start = 6.dp, end = 0.dp)
                )
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${Utils.baseUrl}${imageUrl}")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.wash_and_dry),
                contentDescription = stringResource(R.string.app_name),
                contentScale = ContentScale.Crop,
                modifier = Modifier.width(60.dp).height(60.dp).padding(10.dp).graphicsLayer(alpha = opacity)
            )
        }

        Text(
            text = name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
                .width(70.dp),
            style = TextStyle(
                fontWeight = FontWeight.Normal,

                )
        )
    }


}

@Composable
fun DescriptionDialog(
    onDismissRequest: () -> Unit,
    newOrderViewModel: NewOrderViewModel = viewModel()
) {
    Dialog( onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                text = "Description",
                modifier = Modifier.fillMaxWidth().heightIn().padding(top = 20.dp, start = 23.dp, bottom = 0.dp),
                style = TextStyle(
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            DescriptionInput("description") { newOrderViewModel.description.value = it }

            Text(
                text = "Pick a color",
                modifier = Modifier.fillMaxWidth().heightIn().padding(top = 20.dp, start = 20.dp, bottom = 0.dp),
                style = TextStyle(
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            val scrollState = rememberScrollState()
            Row(modifier = Modifier
                .padding(start = 20.dp)
                .horizontalScroll(scrollState)
            ) {

                for (i in 1..50) {
                    val color = Colors.values()[i - 1]
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .padding(5.dp)
                            .clickable {
                                newOrderViewModel.selectedColor.value = Color(
                                    id = color.id,
                                    colorName = color.colorName,
                                    code = color.code,
                                    kisw = color.kisw
                                )
                            }
                            .background(Color(android.graphics.Color.parseColor(color.code)), shape = CircleShape),
                    ) {
                        val isColorSelected = newOrderViewModel.selectedColor.value?.code == color.code
                        if (isColorSelected) {
                            Icon(
                                imageVector = Icons.Outlined.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.outline,
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(top = 5.dp, start = 6.dp, end = 0.dp)
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                TextButton(
                    onClick = { onDismissRequest() },
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("Dismiss")
                }
                TextButton(
                    onClick = {  newOrderViewModel.addPiece() },
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}


@Composable
fun CircularIndicator() {
    Row(
        modifier = Modifier
            .width(40.dp)
            .height(40.dp)
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(20.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}
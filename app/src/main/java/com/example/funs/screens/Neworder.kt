package com.example.funs.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.unit.Dp
import com.example.funs.components.ButtonAdd


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun NewOrder(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

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
        Box(modifier = Modifier
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

            val scrollState = rememberScrollState()
            Row(modifier = Modifier.horizontalScroll(scrollState)) {
                SearchCard { toggleBottomSheet() }
                ShopCard()
                ShopCard()
                ShopCard()
            }

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

            val scrollStateCategories = rememberScrollState()
            Row(modifier = Modifier.horizontalScroll(scrollStateCategories)) {
                ServiceType(painterResource(id = R.drawable.wash), "Wash only")
                ServiceType(painterResource(id = R.drawable.wash_and_dry), "Wash & Dry")
                ServiceType(painterResource(id = R.drawable.dry), "Drying")
                ServiceType(painterResource(id = R.drawable.press), "Pressing")
            }

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
            Spacer(modifier = Modifier.height(15.dp))
            PieceBar()
            PieceBar()
            PieceBar()

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

            ButtonAdd("Place order")

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
fun TotalPieces () {
    Row (modifier = Modifier.fillMaxWidth()){
        Box(modifier = Modifier
            .fillMaxWidth(0.5f)) {
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
                text = "12 Pieces",
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
        Box ( modifier = Modifier
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
                text = "145,000",
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
fun PieceBar() {
    Row(
        modifier = Modifier.padding(top= 20.dp)) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .widthIn(80.dp)
                .heightIn(80.dp)
                .padding(top = 5.dp, start = 0.dp, end = 5.dp)
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(6.dp)
                    //.clickable()
                ),
        ) {
            Icon(
                imageVector = Icons.Outlined.DryCleaning,
                tint = MaterialTheme.colorScheme.outline,
                contentDescription = null,
                modifier = Modifier.width(50.dp).height(50.dp)
            )
        }

        Column {
            Text(
                text = "Suit 3PCS",
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
                text = "3 Pieces",
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
                    text = "10,000",
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

        Row {
           Box(
               contentAlignment = Alignment.Center,
               modifier = Modifier
                   .width(55.dp)
                   .height(55.dp)
                   .padding(top = 20.dp, start = 10.dp, end= 10.dp, bottom = 0.dp)
                   .background(MaterialTheme.colorScheme.primaryContainer, shape = CircleShape)
           ) {
               Icon(
                   imageVector = Icons.Outlined.Remove,
                   tint = MaterialTheme.colorScheme.onPrimaryContainer,
                   contentDescription = null
               )
           }

            Text(
                text = "1",
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
                    .padding(top = 20.dp, start = 10.dp, end= 10.dp, bottom = 0.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
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
            style = TextStyle(
                fontWeight = FontWeight.Normal,

                )
        )
    }


}

@Composable
fun ShopCard() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.widthIn(100.dp).heightIn(100.dp).padding(top = 5.dp, start = 0.dp, end = 5.dp),
    ) {

        Card(
            modifier = Modifier.width(50.dp).height(50.dp),
            shape = RoundedCornerShape(25.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.order),
                contentScale = ContentScale.Crop,
                contentDescription = "order icon",
                modifier = Modifier.width(60.dp).height(60.dp).padding(10.dp)
            )
        }

        Text(
            text = "AndMore Dry cleaner",
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
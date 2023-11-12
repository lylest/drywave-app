package com.example.funs.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.funs.components.SearchShop
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun NewOrder(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    fun toggleBottomSheet () {
        showBottomSheet = true
    }


        Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                    Text(
                        text = "New order",
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 22.sp
                        )
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        },
        ) {
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    // Sheet content
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.8f)
                        ){
                           SearchShop()
                        }
                }

            }
        }

    Column(
        modifier = Modifier
            .fillMaxWidth(0.94f).padding(start = 24.dp, top = 80.dp)
    ) {
        Text(
            text = "Select shop",
            modifier = Modifier.fillMaxWidth().heightIn().padding(top = 0.dp, start = 0.dp),
            style = TextStyle(
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Light,
                fontSize = 20.sp
            ),
            color = MaterialTheme.colorScheme.onSurface
        )

        val scrollState = rememberScrollState()
        Row(modifier = Modifier.horizontalScroll(scrollState)) {
            SearchCard {toggleBottomSheet()}
            ShopCard()
            ShopCard()
            ShopCard()
        }

        Text(
            text = "Pick a service",
            modifier = Modifier.fillMaxWidth().heightIn().padding(top = 10.dp, start = 0.dp, bottom = 5.dp),
            style = TextStyle(
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Light,
                fontSize = 20.sp
            ),
            color = MaterialTheme.colorScheme.onSurface
        )

        val scrollStateCategories = rememberScrollState()
        Row(modifier = Modifier.horizontalScroll(scrollStateCategories)) {
            ClothCategoryCard()
            ClothCategoryCard()
            ClothCategoryCard()
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
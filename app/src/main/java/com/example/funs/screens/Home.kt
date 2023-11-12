package com.example.funs.screens


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.funs.R
import com.example.funs.components.SearchInput


@Composable
fun Home(navController: NavController) {
    val scrollStateOrder = rememberScrollState()
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
                text = "John Doe",
                modifier = Modifier.fillMaxWidth().heightIn().padding(top = 0.dp, start = 24.dp),
                style = TextStyle(
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.heightIn(30.dp))
            SearchInput()

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
            Row(modifier = Modifier.horizontalScroll(scrollState)) {
                ClothCategoryCard()
                ClothCategoryCard()
                ClothCategoryCard()
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

            Column () {
                OrderCard()
                OrderCard()
                OrderCard()
                OrderCard()
                OrderCard()
                OrderCard()
                OrderCard()
                OrderCard()
                OrderCard()
            }
            Spacer(modifier = Modifier.height(60.dp))

        }

    }
}


@Composable
fun OrderCard() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CircularProgress(0.8f)
            Column(modifier = Modifier.padding(start = 110.dp)) {
                Text(
                    text = "5PC-4809-Mon-WDY",
                    modifier = Modifier.padding(top = 24.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "Ready date: 11/13/2023",
                    modifier = Modifier.padding(top = 5.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    color = MaterialTheme.colorScheme.outline
                )


                Text(
                    text = "Time: 4:00 PM",
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
        Divider(modifier = Modifier.fillMaxWidth().height(0.5.dp).background(MaterialTheme.colorScheme.surface))
    }
}


@Composable
fun CircularProgress(
    percentage: Float,
    radius: Dp = 60.dp,
    strokeWidth: Dp = 2.dp,
    strokeColor: Color = MaterialTheme.colorScheme.primary,
    animationDuration: Int = 1000,
    animationDelay: Int = 2,
) {

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
        ImageCard()
        Canvas(modifier = Modifier.size(radius * 2f).padding(24.dp)) {
            drawArc(
                color = strokeColor,
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
fun ClothCategoryCard() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        //border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier.widthIn(100.dp).heightIn(50.dp).padding(top = 5.dp, start = 0.dp, end = 5.dp),
        shape = RoundedCornerShape(50.dp)
    ) {
        Text(
            text = "Washing & Drying",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(12.dp),
            style = TextStyle(
                fontWeight = FontWeight.Normal
            )
        )
    }
}


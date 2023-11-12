package com.example.funs.screens


import android.media.Image
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.funs.R
import com.example.funs.components.SearchInput
import com.example.funs.navigation.Screen


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
                ServiceType(painterResource(id = R.drawable.wash), "Wash only")
                ServiceType(painterResource(id = R.drawable.wash_and_dry), "Wash & Dry")
                ServiceType(painterResource(id = R.drawable.dry), "Drying")
                ServiceType(painterResource(id = R.drawable.press), "Pressing")
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
                OrderCard(navController)

            }
            Spacer(modifier = Modifier.height(60.dp))

        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderCard(navController:NavController) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        onClick = {
        navController.navigate(Screen.ViewOrder.route)
    }) {
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
fun ServiceType(imageRef: Painter, name:String ) {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .widthIn(120.dp)
            .heightIn(100.dp)
            .padding(top = 5.dp, start = 0.dp, end = 5.dp),
            shape = RoundedCornerShape(12.dp)
    ) {
        Image(
            painter = imageRef,
            contentScale = ContentScale.Crop,
            contentDescription = "order icon",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .padding(15.dp)
               // .padding(top= 10.dp, start = 14.dp, end = 1.dp, bottom = 0.dp)
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


package com.example.funs.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.funs.components.MenuItem


@Composable
fun ViewOrder(navController: NavController) {
    val scrollStateOrder = rememberScrollState()
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
            text = "5PC-5973-Mon-WDY",
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

        ShopBar()

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

        ServiceSelected()
        MenuItem(
            Icons.Outlined.Loyalty,
            "Tag No.",
            "047",
            MaterialTheme.colorScheme.outline
        )

        MenuItem(
            Icons.Outlined.QrCode,
            "Tracking Id",
            "5PC-5973-Mon-WDY",
            MaterialTheme.colorScheme.outline
        )

        MenuItem(
            Icons.Outlined.ReceiptLong,
            "Checkout Code",
            "5PC-1687-Mon-WDY",
            MaterialTheme.colorScheme.outline
        )

        MenuItem(
            Icons.Outlined.CalendarMonth,
            "Date placed",
            "12/11/2023",
            MaterialTheme.colorScheme.outline
        )

        MenuItem(
            Icons.Outlined.CalendarMonth,
            "Ready date",
            "14/11/2023",
            MaterialTheme.colorScheme.outline
        )

        MenuItem(
            Icons.Outlined.DryCleaning,
            "Service",
            "Wash & Dry",
            MaterialTheme.colorScheme.outline
        )

        MenuItem(
            Icons.Outlined.DonutLarge,
            "Progress",
            "pre-inspection",
            MaterialTheme.colorScheme.outline
        )
        MenuItem(
            Icons.Outlined.Circle,
            "Status",
            "Placed",
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
            "Cash",
            MaterialTheme.colorScheme.outline
        )

        MenuItem(
            Icons.Outlined.LocalAtm,
            "Payment status",
            "full-paid",
            MaterialTheme.colorScheme.outline
        )

        MenuItem(
            Icons.Outlined.LocalAtm,
            "Amount paid",
            "14,000",
            MaterialTheme.colorScheme.outline
        )

        MenuItem(
            Icons.Outlined.LocalAtm,
            "Amount remaining",
            "0",
            MaterialTheme.colorScheme.outline
        )

        MenuItem(
            Icons.Outlined.LocalAtm,
            "Discount",
            "0",
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

        MenuItem(
            Icons.Outlined.CheckBox,
            "Discoloration visible",
            "",
            MaterialTheme.colorScheme.primary
        )

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
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceSelected() {
    var selected by remember { mutableStateOf(true) }

    FilterChip(
        onClick = { selected = !selected },
        label = {
            Text("Wash & Dry")
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
fun ShopBar() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CircularProgress(1f, 60.dp, 1.dp, MaterialTheme.colorScheme.background)
            Column(modifier = Modifier.padding(start = 110.dp)) {
                Text(
                    text = "AndMore Dry Cleaning",
                    modifier = Modifier.padding(top = 24.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "P.0 Box 2015,Arusha",
                    modifier = Modifier.padding(top = 5.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    color = MaterialTheme.colorScheme.outline
                )


                Text(
                    text = "Tell: 255755298542",
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
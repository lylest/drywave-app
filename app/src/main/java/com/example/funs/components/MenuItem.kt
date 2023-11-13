package com.example.funs.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuItem (icon: ImageVector, title:String, value:String, tint: Color, underline:Boolean = true) {
 Column (modifier = Modifier
     .fillMaxWidth(0.98f)
     .padding(start = 24.dp, top = 12.dp, end =  24.dp, bottom =  0.dp)) {
     Row (){
         Icon(
             imageVector =icon,
             tint = tint,
             contentDescription = null,
             modifier = Modifier.padding(top = 12.dp, end = 0.dp, bottom = 12.dp, start = 0.dp)
         )
         Text(
             text = title,
             modifier = Modifier.padding( top = 12.dp, bottom = 12.dp, start = 12.dp, end = 12.dp).fillMaxWidth(0.5f),
             style = TextStyle(
                 textAlign = TextAlign.Left,
                 fontWeight = FontWeight.Normal,
                 fontSize = 15.sp
             ),
             color = MaterialTheme.colorScheme.onBackground
         )

         Text(
             text = value,
             modifier = Modifier.padding(top = 12.dp, bottom = 12.dp, start = 12.dp, end = 12.dp),
             style = TextStyle(
                 textAlign = TextAlign.Right,
                 fontWeight = FontWeight.Normal,
                 fontSize = 15.sp
             ),
             color = MaterialTheme.colorScheme.onBackground
         )
     }
     if(underline) {
         Divider(modifier = Modifier.fillMaxWidth().height(0.5.dp).background(MaterialTheme.colorScheme.surface))
     }
 }
}

@Composable
fun MenuIcon (icon: ImageVector, title:String, value:String, tint: Color, underline:Boolean = true) {
    Column (modifier = Modifier
        .fillMaxWidth(0.98f)
        .padding(start = 24.dp, top = 12.dp, end =  24.dp, bottom =  0.dp)) {
        Row (){
            Icon(
                imageVector =icon,
                tint = tint,
                contentDescription = null,
                modifier = Modifier.padding(top = 12.dp, end = 0.dp, bottom = 12.dp, start = 0.dp)
            )
            Text(
                text = title,
                modifier = Modifier.padding( top = 12.dp, bottom = 12.dp, start = 12.dp, end = 12.dp).fillMaxWidth(),
                style = TextStyle(
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )

        }
        if(underline) {
            Divider(modifier = Modifier.fillMaxWidth().height(0.5.dp).background(MaterialTheme.colorScheme.surface))
        }
    }
}

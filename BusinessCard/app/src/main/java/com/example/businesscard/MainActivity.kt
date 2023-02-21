package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BusinessCard()
                }
            }
        }
    }
}



@Composable
fun ContactInfo(phone: String, socialmedia: String, email: String) {
    Column {
        Divider()
        ContactRow(painterResource(id = R.drawable.ic_phone), phone)
        Divider()
        ContactRow(painterResource(id = R.drawable.ic_share), socialmedia)
        Divider()
        ContactRow(painterResource(id = R.drawable.ic_email), email)
    }
}

@Composable
fun ContactRow(icon: Painter, text: String) {
    Row(
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = colorResource(id = R.color.android_green),
            modifier = Modifier.padding(start = 32.dp)
        )
        Text(
            text = text,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun Header(name: String, title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(32.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.android_logo),
            contentDescription = null,
            modifier = Modifier
                .height(125.dp)
                .width(125.dp)
        )
        Text(
            text = name,
            fontSize = 48.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = title,
            fontSize = 18.sp,
            color = colorResource(id = R.color.android_green)
        )
    }
}

@Composable
fun BusinessCard() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Header(
            name = "First Last",
            title = "Android Developer"
        )
        ContactInfo(
            phone = "(555) 555 - 5555",
            socialmedia = "@androiddeveloper",
            email = "example@gmail.com"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BusinessCardPreview() {
    BusinessCard()
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    Header("First Last", "Android Developer")
}

@Preview(showBackground = true)
@Composable
fun ContactPreview() {
    BusinessCardTheme {
        ContactInfo(
            phone = "(555) 555 - 5555",
            socialmedia = "@androiddeveloper",
            email = "example@gmail.com"
        )
    }
}
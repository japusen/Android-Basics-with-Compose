package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LemonadeApp()
                }
            }
        }
    }
}

@Composable
fun LemonadeApp(modifier: Modifier = Modifier) {
    var currentStep by remember { mutableStateOf(1) }
    var squeezeCount by remember{ mutableStateOf(0) }

    when (currentStep) {
        1 -> {
            LemonTextAndImage(
                text = R.string.first_instruction,
                image = R.drawable.lemon_tree,
                description = R.string.tree,
                onImageClick = {
                    currentStep = 2
                    squeezeCount = (2..4).random()
                },
                modifier = modifier
            )
        }
        2 -> {
            LemonTextAndImage(
                text = R.string.second_instruction,
                image = R.drawable.lemon_squeeze,
                description = R.string.lemon,
                onImageClick = {
                    squeezeCount -= 1
                    if (squeezeCount == 0) {
                        currentStep = 3
                    }
                },
                modifier = modifier
            )
        }
        3 -> {
            LemonTextAndImage(
                text = R.string.third_instruction,
                image = R.drawable.lemon_drink,
                description = R.string.glass,
                onImageClick = { currentStep = 4 },
                modifier = modifier
            )
        }
        4 -> {
            LemonTextAndImage(
                text = R.string.fourth_instruction,
                image = R.drawable.lemon_restart,
                description = R.string.empty,
                onImageClick = { currentStep = 1 },
                modifier = modifier
            )
        }
    }
}

@Composable
fun LemonTextAndImage(
    text: Int,
    image: Int,
    description: Int,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(text),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(image),
            contentDescription = stringResource(description),
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(105, 205, 216),
                    shape = RoundedCornerShape(5.dp)
                )
                .clickable(onClick = onImageClick)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    LemonadeTheme {
        LemonadeApp()
    }
}
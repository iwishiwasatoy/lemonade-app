package com.cristopher.lemonade

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cristopher.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    LemonadeApp()
                }
            }
        }
    }
}

data class ImageProps(val painterRes: Int, val contentDescriptionRes: Int)

@Composable
fun LemonadeApp(modifier: Modifier = Modifier) {
    var stage by remember {
        mutableStateOf(1)
    }
    var numberOftaps by remember {
        mutableStateOf((1..4).random())
    }
    val imageProps: ImageProps = when (stage) {
        1 -> ImageProps(
            painterRes = R.drawable.lemon_tree,
            contentDescriptionRes = R.string.lemon_tree
        )

        2 -> ImageProps(
            painterRes = R.drawable.lemon_squeeze,
            contentDescriptionRes = R.string.lemon
        )

        3 -> ImageProps(
            painterRes = R.drawable.lemon_drink,
            contentDescriptionRes = R.string.glass_of_lemonade
        )

        else -> ImageProps(
            painterRes = R.drawable.lemon_restart,
            contentDescriptionRes = R.string.empty_glass
        )
    }
    val stringResource = when (stage) {
        1 -> R.string.select_lemon
        2 -> R.string.squeeze_lemon
        3 -> R.string.drink_lemonade
        else -> R.string.tap_to_start_again
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFFFF063))
                .height(64.dp)
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LemonadeStage(
                onClick = {
                    if (stage == 2 && numberOftaps != 0) {
                        numberOftaps--
                    } else if (stage == 4) {
                        stage = 1
                        numberOftaps = (1..4).random()
                    } else {
                        stage++
                    }

                },
                title = stringResource(id = stringResource),
                imageProps = imageProps
            )
        }
    }
}

@Composable
fun LemonadeStage(
    title: String,
    imageProps: ImageProps,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC3ECD2))
    ) {
        Image(
            painter = painterResource(id = imageProps.painterRes),
            contentDescription = stringResource(id = imageProps.contentDescriptionRes),
        )
    }
    Spacer(modifier = Modifier.height(18.dp))
    Text(text = title, fontSize = 18.sp)
}

//Color(105, 205, 216)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LemonadeTheme {
        LemonadeApp()
    }
}
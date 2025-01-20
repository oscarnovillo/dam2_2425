package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miprimercompose.ui.theme.MiPrimerComposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    MiPrimerComposeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            var texto by rememberSaveable { mutableStateOf("Hello asd asd ")}
            if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                MainScreenLandscape(texto = texto,
                    innerPadding = PaddingValues(0.dp))
            } else {
                MainScreenPortrait(texto = texto,
                    onTextoChange = { texto = it },
                    innerPadding = PaddingValues(0.dp))
            }
        }

    }
}


@Composable
fun MainScreenPortrait(
    texto :String ,
    onTextoChange : (String) -> Unit = {},
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier) {

    Icon(
        imageVector = Icons.Default.Home,
        contentDescription = "Add",
        modifier = Modifier
            .padding(1.dp)
            .clickable {onTextoChange("click icon")}
            .alpha(0.5f)
            .size(48.dp)

    )
    Column ( modifier = Modifier.padding(innerPadding)) {

        ButtonAdap( onClick = { onTextoChange("${texto}H") },
            modifier = Modifier.weight(0.25f))
        Box ( modifier = Modifier
            .fillMaxSize()
            .weight(0.5f)
            .border(width = 1.dp, color = Color.Black)
            .padding(10.dp)
            .border(width = 1.dp, color = Color.Black)){
            Text (text = texto)

            TextField(modifier = Modifier.align(Alignment.Center),
                value = texto,
                onValueChange = onTextoChange,
                label = { Text("Name") }
            )
        }
        Row ( verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Red)
                .height(100.dp)
                .weight(0.25f) ) {
            Box( modifier = Modifier
                .background(Color.Blue)
                .width(200.dp)
                .height(200.dp)) {
                Greeting(
                    name = "Andid",


                    modifier = Modifier
                        .align(Alignment.TopStart)


                )
                Greeting(
                    name = "Android",
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        }
    }


}


@Composable
fun ButtonAdap(onClick : () -> Unit,
               modifier: Modifier = Modifier) {
    Button( onClick = onClick,
        modifier = Modifier) {
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Row {
                Text("Hello")
                Text("Hello")
            }
        } else {
            Column {
                Text("Hello")
                Text("Hello")
            }
        }
    }

}
@Composable
fun MainScreenLandscape(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    texto: String,
    onTextoChange: (String) -> Unit = {},
) {

    Column ( modifier = Modifier.padding(innerPadding)) {
        Row (modifier= Modifier.weight(0.5f)

        ) {
            ButtonAdap( onClick = { onTextoChange("${texto}H") },
                modifier = Modifier.weight(0.25f))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f)
                    .border(width = 1.dp, color = Color.Black)
                    .padding(10.dp)
                    .border(width = 1.dp, color = Color.Black)
            ) {
                TextField(modifier = Modifier.align(Alignment.Center),
                    value = "",
                    onValueChange = {},
                    label = { Text("Name") }
                )
            }
        }
        Row ( verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Red)
                .height(100.dp)
                .weight(0.25f) ) {
            Box( modifier = Modifier
                .background(Color.Blue)
                .width(200.dp)
                .height(200.dp)) {
                Greeting(
                    name = "Andid",


                    modifier = Modifier
                        .align(Alignment.TopStart)


                )
                Greeting(
                    name = "Android",
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        }
    }


}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true,
    showSystemUi = true,
    device = Devices.PHONE)
@Preview(showBackground = true,
    showSystemUi = true,

    device = Devices.TABLET)

@Composable
fun GreetingPreview() {
    MainScreen()
}



package com.example.miprimercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.miprimercompose.ui.theme.MiPrimerComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiPrimerComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var texto by remember { mutableStateOf("") }
    Column(modifier=modifier) {
    Text(
        text = "Hello $name!",
        modifier = Modifier.background(Color.Red)
    )}
}

@Preview(showBackground = true,device= Devices.PHONE)
@Composable
fun GreetingPreview() {
    MiPrimerComposeTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true,device= Devices.PHONE)
@Composable
fun GreetingPreview2() {
    MiPrimerComposeTheme {
        Greeting("Android")
    }
}
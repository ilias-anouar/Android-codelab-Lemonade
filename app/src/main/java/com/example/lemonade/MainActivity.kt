package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeApp() {
    LemonadeMaking()
}

@Composable
fun LemonadeMaking(modifier: Modifier = Modifier) {

    // defining the current state bu remember
    var currentState by remember { mutableStateOf("lemon tree") }

    // getting the current image resource depending on the current state
    val imageResource = when (currentState){
        "lemon tree" -> R.drawable.lemon_tree
        "lemon squeeze" -> R.drawable.lemon_squeeze
        "lemon drink" -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    // getting the current string instructing resource depending on the current state
    val textResource = when (currentState){
        "lemon tree" -> R.string.tree
        "lemon squeeze" -> R.string.squeeze
        "lemon drink" -> R.string.drink
        else -> R.string.again
    }

    // generation a random number of squeeze for lemon if state is lemon squeeze
    var randomSqueeze = if (currentState=="lemon squeeze") { (2..4).random() } else {0}

    // arrow function to return and change state to the next depending on the current state
    val changeState: ()-> String={
        when (currentState){
            "lemon tree" -> "lemon squeeze"
            "lemon squeeze" -> if(randomSqueeze==0) {"lemon drink"} else { randomSqueeze--;"lemon squeeze" }
            "lemon drink" -> "empty"
            else -> "lemon tree"
        }
    }

/**
 * Interface :
 * 1 - layout :
 * -- surface max size.
 * --- column to align element vertically.
 * ---- surface -> text : with yellow background color and max width.
 * ---- spacer -> between header and button element size of 100.dp.
 * ---- button -> image : with onclick to define the current state & Rounded corner shape of 20.dp & Cyan color.
 * ---- spacer -> between button and instruction string size of 30.dp.
 * ---- text -> instruction string font size of 20.sp.
 **/
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column (modifier=modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Surface (color = Color.Yellow, modifier = modifier.fillMaxWidth()) {
                Text(text = "Lemonade", fontWeight = FontWeight.Bold, fontSize = 25.sp,textAlign = TextAlign.Center,modifier=modifier.padding(vertical = 40.dp))
            }
            Spacer(Modifier.size(100.dp))
            Button(onClick = { currentState=changeState() },shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(
                Color.Cyan) ) {
                Image(painter = painterResource(imageResource), contentDescription = currentState,Modifier.padding(20.dp))
            }
            Spacer(Modifier.size(30.dp))
            Text(text = stringResource(textResource), fontSize = 20.sp)
        }
    }

}


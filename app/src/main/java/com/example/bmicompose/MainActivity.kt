package com.example.bmicompose

import android.app.Person
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bmicompose.ui.theme.BMIComposeTheme
import com.example.bmicompose.util.bmiCalculate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMIComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BmiCalculator()
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun BmiCalculator() {

    var heightState by rememberSaveable() {
        mutableStateOf("")
    }

    var weightState by rememberSaveable() {
        mutableStateOf("")
    }

    var expandState by remember {
        mutableStateOf(false)
    }

    var bmiScoreState by remember {
        mutableStateOf(0.0)
    }

    var weightError by remember {
        mutableStateOf(false)
    }

    var heightError by remember {
        mutableStateOf(false)
    }

    var bmiResult by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),

        ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.bminew),
                modifier = Modifier.size(100.dp),
                contentDescription = "Icone da aplicacao"
            )

            Text(
                text = stringResource(id = R.string.app_title),
                color = Color.Black,
                fontSize = 30.sp,
                letterSpacing = 2.sp
            )
        }

        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 16.dp),

            )
        {
            Text(
                text = stringResource(id = R.string.weight_description),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(


                value = weightState,
                onValueChange = { newWeight ->
                    Log.i("xxx", newWeight)
                    var lastChar = if (newWeight.length == 0)
                        newWeight
                    else
                        newWeight.get(newWeight.length - 1)
                    var newValue =
                        if (lastChar == '.' || lastChar == ',') newWeight.dropLast(1) else newWeight
                    Log.i("xxx", lastChar.toString())
                    Log.i("xxx", newValue.toString())
                    weightState = newValue

                },




                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),

                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            if (weightError){
                Text(
                    text = stringResource(id = R.string.Weight_error),
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Red,
                    TextAlign = TextAlign.End
                )
            }



            Spacer(
                Modifier.height(16.dp)
            )



            Text(
                text = stringResource(id = R.string.height_description),
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = heightState,
                onValueChange = { newHeight ->
                    Log.i("xxx", newHeight)
                    var lastChar = if (newHeight.length == 0)
                        newHeight
                    else
                        newHeight.get(newHeight.length - 1)
                    var newValue =
                        if (lastChar == '.' || lastChar == ',') newHeight.dropLast(1) else newHeight
                    Log.i("xxx", lastChar.toString())
                    heightState = newValue
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .focusRequester(focusRequester = FocusRequester()),

                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(16.dp)
            )
            Button(
                onClick = {
                    bmiScoreState = bmiCalculate(
                        weightState.toInt(),
                        heightState.toDouble()
                    )
                    expandState = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Calculate")
            }

        }

        Column() {


            AnimatedVisibility(
                visible = expandState,
                enter = slideIn(tween(durationMillis = 500)) {
                    IntOffset(it.width / 2, 100)

                }
            ) {

                Card(
                    modifier = Modifier
                        .fillMaxSize(),
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Your Score",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                        Text(
                            text = "${String.format("%.2f", bmiScoreState)}",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Congratulations! Your weight is ideal",
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center

                        )

                        Row() {
                            Button(onClick = { expandState = false }) {
                                Text(text = "Reset")
                            }


                            Button(onClick = { /*TODO*/ }) {
                                Text(text = "Share")
                            }
                        }


                    }

                }
            }

        }
    }

}

//@Composable
//fun Teste () {
//    Row() {
//        for (x in 1 .. 3) {
//            Button(onClick = { /*TODO*/ }) {
//                Text(text = "Botao $x")
//            }
//        }
//    }
//}



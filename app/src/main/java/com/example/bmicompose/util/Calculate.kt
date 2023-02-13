package com.example.bmicompose.util

import java.text.DecimalFormat
import kotlin.math.pow

fun bmiCalculate (weight :Int, height:Double ): Double{
 return weight / (height /100).pow(2)


}
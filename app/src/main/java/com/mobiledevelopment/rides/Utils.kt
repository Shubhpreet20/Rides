package com.mobiledevelopment.rides

import androidx.core.text.isDigitsOnly

object Utils {

    /*
    * Provides estimated carbon emission
    * */
     fun getEstCarbonEmission(kilometrage: Int?): Double {
        kilometrage?.let {
            if (it > 5000) {//Check if kilometrage is > 5000
                return (5000 * 1) + (it - 5000) * 1.5
            } else {
                return (it * 1).toDouble()
            }
        }
        return 0.0
    }

    /*
    * Based on entered size it returns either success or a message if not successful.
    * */
    fun isValidInput(size: String): Pair<Boolean,String> {
        return if(size.isNotEmpty()) {
            if (isDigitsOnly(size)){
                val input = size.toInt()
                if (input in 1..100){
                    Pair(true,"Success")
                }else{
                    Pair(false,"Enter a valid size from 1 to 100 to proceed further.")
                }
            }else{
                Pair(false,"Enter a valid number to proceed further.")
            }
        }else{
            Pair(false,"Enter a valid list size to proceed further.")
        }
    }

    /*
    * Checks if string is having only digits
    * */
    fun isDigitsOnly(input:String):Boolean {
        for (i in input) {
            if (!i.isDigit()) {
                return false
            }
        }
        return true
    }
}
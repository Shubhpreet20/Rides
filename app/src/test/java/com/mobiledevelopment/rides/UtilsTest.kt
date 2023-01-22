package com.mobiledevelopment.rides


import org.junit.Assert.assertEquals
import org.junit.Test


internal class UtilsTest {

    @Test
    fun getEstCarbonEmission() {
        assertEquals(Utils.getEstCarbonEmission(0), 0.0,0.0)
        assertEquals(Utils.getEstCarbonEmission(1), 1.0,0.0)
        assertEquals(Utils.getEstCarbonEmission(4000), 4000.0,0.0)
        assertEquals(Utils.getEstCarbonEmission(5000), 5000.0,0.0)
        assertEquals(Utils.getEstCarbonEmission(5001), 5001.5,0.0)
        assertEquals(Utils.getEstCarbonEmission(6000), 6500.0,0.0)
    }


    @Test
    fun isValidInput() {
        assertEquals(Utils.isValidInput("1"), Pair(true,"Success"))
        assertEquals(Utils.isValidInput("100"), Pair(true,"Success"))
        assertEquals(Utils.isValidInput("60"), Pair(true,"Success"))
        assertEquals(Utils.isValidInput("0"), Pair(false,"Enter a valid size from 1 to 100 to proceed further."))
        assertEquals(Utils.isValidInput("101"), Pair(false,"Enter a valid size from 1 to 100 to proceed further."))
        assertEquals(Utils.isValidInput("1000"), Pair(false,"Enter a valid size from 1 to 100 to proceed further."))
        assertEquals(Utils.isValidInput("ab"), Pair(false,"Enter a valid number to proceed further."))
        assertEquals(Utils.isValidInput("x"), Pair(false,"Enter a valid number to proceed further."))
        assertEquals(Utils.isValidInput("x"), Pair(false,"Enter a valid number to proceed further."))
        assertEquals(Utils.isValidInput(""), Pair(false,"Enter a valid list size to proceed further."))
    }

    @Test
    fun isDigitsOnly(){
        assertEquals(Utils.isDigitsOnly("1"), true);
        assertEquals(Utils.isDigitsOnly("x"), false);
        assertEquals(Utils.isDigitsOnly("xn*p"), false);
        assertEquals(Utils.isDigitsOnly("23"), true);
    }
}
package com.example.ticTacToe.Games

import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.storybook.R
import java.util.*
import kotlin.properties.Delegates

class GuessTheGame {


    private var maxCount by Delegates.notNull<Int>()
    private var minCount by Delegates.notNull<Int>()
    private var midCount by Delegates.notNull<Int>()

    private var number by Delegates.notNull<Int>()

    constructor(max: Int, min: Int){
        maxCount = max
        minCount = min
    }

    constructor(max: Int, min: Int, boolean: Boolean){
        maxCount = max
        minCount = min
    }

    fun generateNumber(){
        if(maxCount == minCount +1){
            number = minCount
        }
        else {
            number = (minCount..maxCount).random()
            println(number)
        }

    }

    fun checkNumber(userNumber: Int): Int{
        if (number > userNumber)
            return 1
        else if(number < userNumber)
            return 2
        else
            return 0
    }

    fun checkNumberBigger(): Boolean{
        midCount = (maxCount + minCount) / 2
        val res: String
        when(midCount){
            maxCount ->{
                midCount - 1
                res = R.string.win.toString() + midCount
                return true
            }
            minCount ->{
                midCount + 1
                res = R.string.win.toString() + midCount
                return true
            }
            else ->{
                res = R.string.your_num.toString() + midCount + "?"
                return false
            }
        }

        Log.d("check", "min = " + minCount + " max = " + maxCount + " mid " + midCount);
    }

    @JvmName("getMidCount1")
    fun getMidCount(): Int{
        return midCount
    }

    fun biggerNum(){
        minCount = midCount
    }

    fun lessNum(){
        maxCount = midCount
    }
}
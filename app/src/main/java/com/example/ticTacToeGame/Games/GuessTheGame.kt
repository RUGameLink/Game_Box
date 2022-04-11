package com.example.ticTacToeGame.Games

import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.storybook.R
import kotlin.properties.Delegates

class GuessTheGame {
    private lateinit var minCountText: String
    private lateinit var maxCountText: String

    private var maxCount by Delegates.notNull<Int>()
    private var minCount by Delegates.notNull<Int>()
    private var midCount by Delegates.notNull<Int>()

    constructor(max: Int, min: Int){
        maxCount = max
        minCount = min
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
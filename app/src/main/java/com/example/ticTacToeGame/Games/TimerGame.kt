package com.example.ticTacToeGame.Games

import java.util.*

class TimerGame {
    private lateinit var gameTime: String
    private lateinit var random: Random

    constructor(){
        generateGameTime()
    }

    fun generateGameTime() {
        random = Random()
        var secondCount = random.nextInt(((3 - 1) - 1) + 1)
        var thirdCount = (19 until 59).random()

        gameTime = makeTimeString( secondCount, thirdCount)
    }

    fun getGameTime(): String{
        return gameTime
    }

    fun checkResult(userTime: String): Boolean{
        println(userTime)
        println(gameTime)
        return userTime == gameTime
    }

    private fun makeTimeString(minute: Int, second: Int): String = String.format("00:%02d:%02d", minute, second)
}
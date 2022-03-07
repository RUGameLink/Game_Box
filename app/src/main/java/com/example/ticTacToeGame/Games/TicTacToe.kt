package com.example.ticTacToeGame.Games

import com.example.storybook.R

class TicTacToe {
    private var playerOne: Int
    private var playerTwo: Int

    private var activePlayer: Int


    private var gameActive: Boolean

    lateinit var filledPosition: IntArray

    constructor(){
        playerOne = 0
        playerTwo = 1
        filledPosition = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1)
        activePlayer = playerOne
        gameActive = true
    }

    fun getPlayer(): Int{
        return activePlayer
    }

    fun setPlayerOneActive(){
        activePlayer = playerOne
    }

    fun setPlayerTwoActive(){
        activePlayer = playerTwo
    }

    fun checkForWins(): Int{
        var res = -1

        var count = 0

        for(i in filledPosition.indices){//Обработка ничьи
            if(filledPosition[i] == -1){
                count++
            }
        }
        if(count == 0){
            res = 0 //ничья
        }

        var winPosition = arrayOf(intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)) //Инициализация массива выигрышных комбинаций

        for (i in winPosition.indices){ //Обработка побед
            var val0 = winPosition[i][0]
            var val1 = winPosition[i][1]
            var val2 = winPosition[i][2]

            if(filledPosition[val0] == filledPosition[val1] && filledPosition[val1] == filledPosition[val2]){
                if(filledPosition[val0] != -1) {
                    gameActive = false
                    if (filledPosition[val0] == playerOne) {
                        res = 1 //pl one
                    } else {
                        res = 2 //pl two
                    }
                }
            }

        }


        return res
    }

    fun get(): Boolean {
        return gameActive
    }

    fun checkFilledPosition(clickTagDetector: Int): Int{
        var res: Int = 0
        if(filledPosition[clickTagDetector] != -1){
            return -1
        }

            filledPosition[clickTagDetector] = activePlayer
            when(activePlayer){ //Смена хода
                playerOne -> {
                    res = 1
                    activePlayer = playerTwo
                }
                playerTwo -> {
                    res = 2
                    activePlayer = playerOne
                }
            }

        return res
    }

    fun paintLine(): Int{
        var res = -1
        if(filledPosition[0] != -1 && filledPosition[0] == filledPosition[1] && filledPosition[1] == filledPosition[2]){
            res = 1
        }
        else if(filledPosition[3] != -1 && filledPosition[3] == filledPosition[4] && filledPosition[4] == filledPosition[5]){
            res = 2
        }
        else if(filledPosition[6] != -1 && filledPosition[6] == filledPosition[7] && filledPosition[7] == filledPosition[8]){
            res = 3
        }
        else if(filledPosition[0] != -1 && filledPosition[0] == filledPosition[3] && filledPosition[3] == filledPosition[6]){
            res = 4
        }
        else if(filledPosition[1] != -1 && filledPosition[1] == filledPosition[4] && filledPosition[4] == filledPosition[7]){
            res = 5
        }
        else if(filledPosition[2] != -1 && filledPosition[2] == filledPosition[5] && filledPosition[5] == filledPosition[8]){
            res = 6
        }
        else if(filledPosition[0] != -1 && filledPosition[0] == filledPosition[4] && filledPosition[4] == filledPosition[8]){
            res = 7
        }
        else if(filledPosition[2] != -1 && filledPosition[2] == filledPosition[4] && filledPosition[4] == filledPosition[6]){
            res = 8
        }
        return res
    }

}
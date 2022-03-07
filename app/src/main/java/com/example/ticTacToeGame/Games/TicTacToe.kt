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

    fun setPlayerOneActive(){
        activePlayer = playerOne
    }

    fun setPlayerTwoActive(){
        activePlayer = playerTwo
    }

    fun checkForWins(): Int{
        var res = -1
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

        var count = 0

        for(i in filledPosition.indices){//Обработка ничьи
            if(filledPosition[i] == -1){
                count++
            }
        }
        if(count == 0){
            res = 0 //ничья
        }
        return res
    }

}
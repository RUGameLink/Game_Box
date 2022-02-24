package com.example.ticTacToeGame

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.storybook.R

import android.media.MediaPlayer
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi


class TicTacToeActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var b0: Button
    lateinit var b1: Button
    lateinit var b2: Button
    lateinit var b3: Button
    lateinit var b4: Button
    lateinit var b5: Button
    lateinit var b6: Button
    lateinit var b7: Button
    lateinit var b8: Button

    lateinit var restartButton: Button

    lateinit var turnView: TextView

    lateinit var mp: MediaPlayer

    var playerOne = 0
    var playerTwo = 1
    var activePlayer = playerOne


    var gameActive = true

    lateinit var filledPosition: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tictactoe)
        init()
        restartButton.setOnClickListener(restartGameListener)
        filledPosition = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1) //Массив отслеживания активированных кнопок
        initListener()
    }

    private fun init(){

        b0 = findViewById(R.id.b0)
        b1 = findViewById(R.id.b1)
        b2 = findViewById(R.id.b2)
        b3 = findViewById(R.id.b3)
        b4 = findViewById(R.id.b4)
        b5 = findViewById(R.id.b5)
        b6 = findViewById(R.id.b6)
        b7 = findViewById(R.id.b7)
        b8 = findViewById(R.id.b8)

        restartButton = findViewById(R.id.restartButton)

        mp = MediaPlayer.create(this, R.raw.pencil);

        turnView = findViewById(R.id.turnView)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    var restartGameListener: View.OnClickListener = View.OnClickListener {
        restartingGame()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(p0: View?) {
        if(!gameActive)
            return

        var btnClicked = findViewById<Button>(p0!!.id)
        var clickTagDetector = Integer.parseInt(btnClicked.tag.toString())

        if(filledPosition[clickTagDetector] != -1)
            return

        filledPosition[clickTagDetector] = activePlayer

        mp.start() //Звуковое сопровождение

        when(activePlayer){ //Смена хода
            playerOne -> {btnClicked.setText("X")
                activePlayer = playerTwo
                turnView.setText(R.string.turnTwo)
                btnClicked.backgroundTintList=getColorStateList(R.color.crimson) //Перекраска кнопки в цвет игрока
            }
            playerTwo -> {btnClicked.setText("O")
                activePlayer = playerOne
                turnView.setText(R.string.turnOne)
                btnClicked.backgroundTintList=getColorStateList(R.color.lime_green)
            }
        }
        println(filledPosition[clickTagDetector])

        paintingLine()
        checkForWins()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkForWins(){
        var winPosition = arrayOf(intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)) //Инициализация массива выигрышных комбинаций

        for (i in winPosition.indices){ //Обработка побед
            var val0 = winPosition[i][0]
            var val1 = winPosition[i][1]
            var val2 = winPosition[i][2]
        //    println(winPosition[i][0])
       //     println(val1)
        //    println(val2)

            if(filledPosition[val0] == filledPosition[val1] && filledPosition[val1] == filledPosition[val2]){
                if(filledPosition[val0] != -1) {
                    gameActive = false
                    if (filledPosition[val0] == playerOne) {
                        turnView.setText(R.string.playerOneWin)
                    } else {
                        turnView.setText(R.string.playerTwoWin)
                    }
                    return
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
            turnView.setText(R.string.drawGame)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun restartingGame(){
        filledPosition = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1) //Массив отслеживания активированных кнопок
        gameActive = true

        when(activePlayer){ //Смена хода
            playerOne -> turnView.setText(R.string.turnOne)
            playerTwo -> turnView.setText(R.string.turnTwo)
        }

        b0.text = ""
        b1.text = ""
        b2.text = ""
        b3.text = ""
        b4.text = ""
        b5.text = ""
        b6.text = ""
        b7.text = ""
        b8.text = ""

        b0.backgroundTintList = getColorStateList(R.color.nightfall)
        b1.backgroundTintList = getColorStateList(R.color.nightfall)
        b2.backgroundTintList = getColorStateList(R.color.nightfall)
        b3.backgroundTintList = getColorStateList(R.color.nightfall)
        b4.backgroundTintList = getColorStateList(R.color.nightfall)
        b5.backgroundTintList = getColorStateList(R.color.nightfall)
        b6.backgroundTintList = getColorStateList(R.color.nightfall)
        b7.backgroundTintList = getColorStateList(R.color.nightfall)
        b8.backgroundTintList = getColorStateList(R.color.nightfall)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun paintingLine(){
        if(filledPosition[0] != -1 && filledPosition[0] == filledPosition[1] && filledPosition[1] == filledPosition[2]){
            b0.backgroundTintList=getColorStateList(R.color.orange2)
            b1.backgroundTintList=getColorStateList(R.color.orange2)
            b2.backgroundTintList=getColorStateList(R.color.orange2)
        }
        else if(filledPosition[3] != -1 && filledPosition[3] == filledPosition[4] && filledPosition[4] == filledPosition[5]){
            b3.backgroundTintList=getColorStateList(R.color.orange2)
            b4.backgroundTintList=getColorStateList(R.color.orange2)
            b5.backgroundTintList=getColorStateList(R.color.orange2)
        }
        else if(filledPosition[6] != -1 && filledPosition[6] == filledPosition[7] && filledPosition[7] == filledPosition[8]){
            b6.backgroundTintList=getColorStateList(R.color.orange2)
            b7.backgroundTintList=getColorStateList(R.color.orange2)
            b8.backgroundTintList=getColorStateList(R.color.orange2)
        }
        else if(filledPosition[0] != -1 && filledPosition[0] == filledPosition[3] && filledPosition[3] == filledPosition[6]){
            b0.backgroundTintList=getColorStateList(R.color.orange2)
            b3.backgroundTintList=getColorStateList(R.color.orange2)
            b6.backgroundTintList=getColorStateList(R.color.orange2)
        }
        else if(filledPosition[1] != -1 && filledPosition[1] == filledPosition[4] && filledPosition[4] == filledPosition[7]){
            b1.backgroundTintList=getColorStateList(R.color.orange2)
            b4.backgroundTintList=getColorStateList(R.color.orange2)
            b7.backgroundTintList=getColorStateList(R.color.orange2)
        }
        else if(filledPosition[2] != -1 && filledPosition[2] == filledPosition[5] && filledPosition[5] == filledPosition[8]){
            b2.backgroundTintList=getColorStateList(R.color.orange2)
            b5.backgroundTintList=getColorStateList(R.color.orange2)
            b8.backgroundTintList=getColorStateList(R.color.orange2)
        }
        else if(filledPosition[0] != -1 && filledPosition[0] == filledPosition[4] && filledPosition[4] == filledPosition[8]){
            b0.backgroundTintList=getColorStateList(R.color.orange2)
            b4.backgroundTintList=getColorStateList(R.color.orange2)
            b8.backgroundTintList=getColorStateList(R.color.orange2)
        }
        else if(filledPosition[2] != -1 && filledPosition[2] == filledPosition[4] && filledPosition[4] == filledPosition[6]){
            b2.backgroundTintList=getColorStateList(R.color.orange2)
            b4.backgroundTintList=getColorStateList(R.color.orange2)
            b6.backgroundTintList=getColorStateList(R.color.orange2)
        }
    }

    private fun initListener(){ //Инициализация слушателей
        b0.setOnClickListener(this)
        b1.setOnClickListener(this)
        b2.setOnClickListener(this)
        b3.setOnClickListener(this)
        b4.setOnClickListener(this)
        b5.setOnClickListener(this)
        b6.setOnClickListener(this)
        b7.setOnClickListener(this)
        b8.setOnClickListener(this)
    }
}
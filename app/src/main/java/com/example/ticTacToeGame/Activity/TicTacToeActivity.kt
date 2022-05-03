package com.example.ticTacToeGame.Activity

import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.storybook.R
import com.example.ticTacToeGame.Games.Presets
import com.example.ticTacToeGame.Games.TicTacToe
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.xml.KonfettiView
import java.util.concurrent.TimeUnit


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
    lateinit var backButton: Button
    lateinit var viewKonfetti: KonfettiView

    lateinit var turnView: TextView

    lateinit var mp: MediaPlayer






    lateinit var ticTacToe: TicTacToe



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tictactoe)
        init()
        restartButton.setOnClickListener(restartGameListener)
        backButton.setOnClickListener(backListener)
        ticTacToe = TicTacToe()

        initListener()

        hideBars()
    }

    private fun hideBars() {
        supportActionBar?.hide()
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
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

        viewKonfetti = findViewById(R.id.konfettiView)
        restartButton = findViewById(R.id.restartButton)
        backButton = findViewById(R.id.backButton)

        mp = MediaPlayer.create(this, R.raw.pencil);

        turnView = findViewById(R.id.turnView)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    var restartGameListener: View.OnClickListener = View.OnClickListener {
        restartingGame()
    }

    var backListener: View.OnClickListener = View.OnClickListener {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(p0: View?) {
        if(!ticTacToe.get())
            return

        var btnClicked = findViewById<Button>(p0!!.id)
        var clickTagDetector = Integer.parseInt(btnClicked.tag.toString())

        var check = ticTacToe.checkFilledPosition(clickTagDetector)

    //    if(check != -1)
    //        return

            mp.start()

            when(check){ //Смена хода
                1 -> {btnClicked.setText("X")
                    turnView.setText(R.string.turnTwo)
                    btnClicked.backgroundTintList=getColorStateList(R.color.crimson) //Перекраска кнопки в цвет игрока
                }
                2 -> {btnClicked.setText("O")
                    turnView.setText(R.string.turnOne)
                    btnClicked.backgroundTintList=getColorStateList(R.color.lime_green)
                }
            }

        paintingLine()
        checkForWins()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkForWins(){
        var result = ticTacToe.checkForWins()

        when(result){
            1 -> {viewKonfetti.start(Presets.parade())
                turnView.setText(R.string.playerOneWin)}
            2 -> {viewKonfetti.start(Presets.parade())
                turnView.setText(R.string.playerTwoWin)}
            0 -> {viewKonfetti.start(Presets.rain())
                turnView.setText(R.string.drawGame)}
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun restartingGame(){
        ticTacToe = TicTacToe()

        turnView.setText(R.string.turnOne)



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
        var result = ticTacToe.paintLine()
        when(result){
            1 -> {
                b0.backgroundTintList=getColorStateList(R.color.orange2)
                b1.backgroundTintList=getColorStateList(R.color.orange2)
                b2.backgroundTintList=getColorStateList(R.color.orange2)
            }
            2 -> {
                b3.backgroundTintList=getColorStateList(R.color.orange2)
                b4.backgroundTintList=getColorStateList(R.color.orange2)
                b5.backgroundTintList=getColorStateList(R.color.orange2)
            }
            3 -> {
                b6.backgroundTintList=getColorStateList(R.color.orange2)
                b7.backgroundTintList=getColorStateList(R.color.orange2)
                b8.backgroundTintList=getColorStateList(R.color.orange2)
            }
            4 -> {
                b0.backgroundTintList=getColorStateList(R.color.orange2)
                b3.backgroundTintList=getColorStateList(R.color.orange2)
                b6.backgroundTintList=getColorStateList(R.color.orange2)
            }
            5 -> {
                b1.backgroundTintList=getColorStateList(R.color.orange2)
                b4.backgroundTintList=getColorStateList(R.color.orange2)
                b7.backgroundTintList=getColorStateList(R.color.orange2)
            }
            6 -> {
                b2.backgroundTintList=getColorStateList(R.color.orange2)
                b5.backgroundTintList=getColorStateList(R.color.orange2)
                b8.backgroundTintList=getColorStateList(R.color.orange2)
            }
            7 -> {
                b0.backgroundTintList=getColorStateList(R.color.orange2)
                b4.backgroundTintList=getColorStateList(R.color.orange2)
                b8.backgroundTintList=getColorStateList(R.color.orange2)
            }
            8 -> {
                b2.backgroundTintList=getColorStateList(R.color.orange2)
                b4.backgroundTintList=getColorStateList(R.color.orange2)
                b6.backgroundTintList=getColorStateList(R.color.orange2)
            }
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            true
        } else super.onKeyDown(keyCode, event)
    }
}
package com.example.ticTacToeGame.Activity

import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.storybook.R
import com.example.ticTacToeGame.Services.Presets
import com.example.ticTacToeGame.Games.TicTacToe
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import nl.dionsegijn.konfetti.xml.KonfettiView


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


    private val database = Firebase.database("https://gameboxapp-42309-default-rtdb.europe-west1.firebasedatabase.app")
    private lateinit var auth: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tictactoe)
        init()
        restartButton.setOnClickListener(restartGameListener)
        backButton.setOnClickListener(backListener)
        ticTacToe = TicTacToe()

        initListener()

        hideBars()
        auth = intent.getStringExtra("uid").toString()
        println("Auth Tic Tac ${auth}")
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
                turnView.setText(R.string.playerOneWin)

                setTotalGameDB()
                setCrossGameDB()
            }
            2 -> {viewKonfetti.start(Presets.parade())
                turnView.setText(R.string.playerTwoWin)

                setTotalGameDB()
                setZeroGameDB()
            }
            0 -> {viewKonfetti.start(Presets.rain())
                turnView.setText(R.string.drawGame)

                setTotalGameDB()
                setDrawGameDB()
            }
        }
    }

    private fun setTotalGameDB(){
        database.getReference(auth).child("ticTacToeAllGamesCount").get().addOnSuccessListener {
            var toTalGameCount = it.value
            if(toTalGameCount == null){
                ticTacToe.setTotalGamesCount(0)
                database.getReference(auth).child("ticTacToeAllGamesCount").setValue(ticTacToe.getTotalGamesCount())
            }
            else{
                var res = toTalGameCount.toString().toInt()
                ticTacToe.setTotalGamesCount(res)
                database.getReference(auth).child("ticTacToeAllGamesCount").setValue(ticTacToe.getTotalGamesCount())
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    private fun setCrossGameDB(){
        database.getReference(auth).child("ticTacToeCrossWinsCount").get().addOnSuccessListener {
            var crossWinsCount = it.value
            if(crossWinsCount == null){
                ticTacToe.setCrossWinsCount(0)
                database.getReference(auth).child("ticTacToeCrossWinsCount").setValue(ticTacToe.getCrossWinsCount())
            }
            else{
                var res = crossWinsCount.toString().toInt()
                ticTacToe.setCrossWinsCount(res)
                database.getReference(auth).child("ticTacToeCrossWinsCount").setValue(ticTacToe.getCrossWinsCount())
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    private fun setZeroGameDB(){
        database.getReference(auth).child("ticTacToeZeroWinsCount").get().addOnSuccessListener {
            var zeroWinsCount = it.value
            if(zeroWinsCount == null){
                ticTacToe.setZeroWinsCount(0)
                database.getReference(auth).child("ticTacToeZeroWinsCount").setValue(ticTacToe.getZeroWinsCount())
            }
            else{
                var res = zeroWinsCount.toString().toInt()
                ticTacToe.setZeroWinsCount(res)
                database.getReference(auth).child("ticTacToeZeroWinsCount").setValue(ticTacToe.getZeroWinsCount())
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    private fun setDrawGameDB(){
        database.getReference(auth).child("ticTacToeDrawCount").get().addOnSuccessListener {
            var drawCount = it.value
            if(drawCount == null){
                ticTacToe.setDrawWinsCount(0)
                database.getReference(auth).child("ticTacToeDrawCount").setValue(ticTacToe.getDrawCount())
            }
            else{
                var res = drawCount.toString().toInt()
                ticTacToe.setDrawWinsCount(res)
                database.getReference(auth).child("ticTacToeDrawCount").setValue(ticTacToe.getDrawCount())
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
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
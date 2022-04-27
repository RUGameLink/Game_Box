package com.example.ticTacToeGame.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.storybook.R
import com.example.ticTacToe.Games.GuessTheGame
import kotlin.properties.Delegates

class GuessTheNumberActivity: AppCompatActivity() {
    private lateinit var moreButton: Button
    private lateinit var lessButton: Button
    private lateinit var equalButton: Button
    private lateinit var backButton: Button
    private lateinit var numText: TextView
    private lateinit var gameText: TextView

    private lateinit var guessTheGame: GuessTheGame

    private lateinit var minCountText: String
    private lateinit var maxCountText: String

    private var maxCount by Delegates.notNull<Int>()
    private var minCount by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guessthenumber)

        init()

        maxCount = maxCountText.toInt()
        minCount = minCountText.toInt()
        guessTheGame = GuessTheGame(maxCount, minCount)
        guessTheGame.checkNumberBigger()
        gameText.text = "${guessTheGame.getMidCount().toString()}?"

        moreButton.setOnClickListener(moreButtonListener)
        lessButton.setOnClickListener(lessButtonListener)
        equalButton.setOnClickListener(equalButtonListener)
        backButton.setOnClickListener(backListener)
        hideBars()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            true
        } else super.onKeyDown(keyCode, event)
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

    private var moreButtonListener: View.OnClickListener = View.OnClickListener {
        guessTheGame.biggerNum()
        var res = guessTheGame.checkNumberBigger()
        checkToWin(res)
    }

    private var lessButtonListener: View.OnClickListener = View.OnClickListener {
        guessTheGame.lessNum()
        var res = guessTheGame.checkNumberBigger()
        checkToWin(res)
    }

    private var equalButtonListener: View.OnClickListener = View.OnClickListener {
        endGame()
    }

    var backListener: View.OnClickListener = View.OnClickListener {
        toMain()
    }

    private fun checkToWin(res: Boolean){
        var partOne = getString(R.string.end_guess1)
        var partTwo = getString(R.string.end_guess2)
        if (res){
            numText.setText(partOne + "\n" + " ${guessTheGame.getMidCount()} " +  partTwo  + " ${guessTheGame.getMidCount() + 1}") //+ guessTheGame.getMidCount() +R.string.end_guess2.toString()  + "${guessTheGame.getMidCount() + 1}"
            endGame()
        }

        else{
            gameText.text = "${guessTheGame.getMidCount()}?"
        }
    }

    private fun endGame(){
        Toast.makeText(applicationContext, R.string.end_guess_game, Toast.LENGTH_LONG).show()

        val handler = Handler()
        handler.postDelayed({
            toMain()
        }, 2000)
    }

    private fun toMain(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun init(){
        gameText = findViewById(R.id.gameText)
        minCountText= intent.getStringExtra("minCount").toString()
        maxCountText= intent.getStringExtra("maxCount").toString()
        moreButton = findViewById(R.id.moreButton)
        lessButton = findViewById(R.id.lessButton)
        equalButton = findViewById(R.id.equalButton)
        backButton = findViewById(R.id.backButton)
        numText = findViewById(R.id.numText)
    }
}
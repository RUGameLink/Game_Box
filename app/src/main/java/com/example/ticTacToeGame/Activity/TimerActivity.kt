package com.example.ticTacToeGame.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.storybook.R
import com.example.ticTacToe.Games.GuessTheGame

class TimerActivity : AppCompatActivity() {
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        init()
        backButton.setOnClickListener(backListener)
    }

    var backListener: View.OnClickListener = View.OnClickListener {
        toMain()
    }

    private fun toMain(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun init(){
        backButton = findViewById(R.id.backButton)
    }
}
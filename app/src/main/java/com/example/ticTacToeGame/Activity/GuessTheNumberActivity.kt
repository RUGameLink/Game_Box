package com.example.ticTacToeGame.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.storybook.R
import com.example.ticTacToeGame.Games.TicTacToe

class GuessTheNumberActivity: AppCompatActivity() {
    private lateinit var moreButton: Button
    private lateinit var lessButton: Button
    private lateinit var equalButton: Button
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guessthenumber)

        init()

        moreButton.setOnClickListener(moreButtonListener)
        lessButton.setOnClickListener(lessButtonListener)
        equalButton.setOnClickListener(equalButtonListener)
        backButton.setOnClickListener(backListener)
    }

    private var moreButtonListener: View.OnClickListener = View.OnClickListener {

    }

    private var lessButtonListener: View.OnClickListener = View.OnClickListener {

    }

    private var equalButtonListener: View.OnClickListener = View.OnClickListener {

    }

    var backListener: View.OnClickListener = View.OnClickListener {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun init(){
        moreButton = findViewById(R.id.moreButton)
        lessButton = findViewById(R.id.lessButton)
        equalButton = findViewById(R.id.equalButton)
        backButton = findViewById(R.id.backButton)
    }
}
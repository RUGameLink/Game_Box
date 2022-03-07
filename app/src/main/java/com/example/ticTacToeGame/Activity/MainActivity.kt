package com.example.ticTacToeGame.Activity

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.storybook.R
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var donateButton: Button
    private lateinit var ticTacToeButton: Button
    private lateinit var soundPadButton: Button
    private lateinit var guessTheNumberButton: Button

    private lateinit var star1Image: ImageView
    private lateinit var star2Image: ImageView
    private lateinit var moonImage: ImageView
    private lateinit var sunnImage: ImageView

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        donateButton.setOnClickListener(myButtonClickListener)
        ticTacToeButton.setOnClickListener(ticTacToeListener)
        soundPadButton.setOnClickListener(soundPadListener)
        guessTheNumberButton.setOnClickListener(guessTheNumberListener)
        checkaDate()
    }

    private var soundPadListener: View.OnClickListener = View.OnClickListener {
        soundPadShow()
    }

    private var ticTacToeListener: View.OnClickListener = View.OnClickListener {
        ticTacToeShow()
    }

    private var myButtonClickListener: View.OnClickListener = View.OnClickListener {
        donating()
    }

    private var guessTheNumberListener: View.OnClickListener = View.OnClickListener {
        guessTheNumberShow()
    }

    private fun donating(){
        val url = "https://boosty.to/sadsm"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun guessTheNumberShow(){
        val i = Intent(this, GuessTheNumberActivity::class.java)
        startActivity(i)
    }

    private fun ticTacToeShow(){
        val i = Intent(this, TicTacToeActivity::class.java)
        startActivity(i)
    }

    private fun soundPadShow(){
        val i = Intent(this, SoundPadActivity::class.java)
        startActivity(i)
    }

    private fun init(){
        donateButton = findViewById(R.id.backButton)
        star1Image = findViewById(R.id.starView1)
        star2Image = findViewById(R.id.starView2)
        moonImage = findViewById(R.id.moonView)
        sunnImage = findViewById(R.id.sunnView)
        ticTacToeButton = findViewById(R.id.tictactoeButton)
        soundPadButton = findViewById(R.id.soundpadButton)
        guessTheNumberButton = findViewById(R.id.guessTheNumberButton)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun checkaDate(){
        val date: String =
            SimpleDateFormat("HH").format(Calendar.getInstance().time)

        when(date){
            "21", "22", "23", "00", "01", "02", "03", "04", "05", "06", "07"
            -> {star1Image.setVisibility(View.VISIBLE)
                star2Image.setVisibility(View.VISIBLE)
                moonImage.setVisibility(View.VISIBLE)
                sunnImage.setVisibility(View.INVISIBLE)
            }
            "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"
            -> {
                star1Image.setVisibility(View.INVISIBLE)
                star2Image.setVisibility(View.INVISIBLE)
                moonImage.setVisibility(View.INVISIBLE)
                sunnImage.setVisibility(View.VISIBLE)
            }
        }
    }
}
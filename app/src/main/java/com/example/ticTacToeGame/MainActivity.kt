package com.example.ticTacToeGame

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.storybook.R
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var donateButton: Button
    lateinit var ticTacToeButton: Button

    lateinit var star1Image: ImageView
    lateinit var star2Image: ImageView
    lateinit var moonImage: ImageView
    lateinit var sunnImage: ImageView

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        donateButton.setOnClickListener(myButtonClickListener)
        ticTacToeButton.setOnClickListener(ticTacToeListener)
        checkaDate()
    }

    var ticTacToeListener: View.OnClickListener = View.OnClickListener {
        ticTacToeShow()
    }

    var myButtonClickListener: View.OnClickListener = View.OnClickListener {
        donating()
    }

    fun donating(){
        val url = "https://boosty.to/sadsm"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    fun ticTacToeShow(){
        val i = Intent(this, TicTacToeActivity::class.java)
        startActivity(i)

    }

    fun init(){
        donateButton = findViewById(R.id.donateButton)
        star1Image = findViewById(R.id.starView1)
        star2Image = findViewById(R.id.starView2)
        moonImage = findViewById(R.id.moonView)
        sunnImage = findViewById(R.id.sunnView)
        ticTacToeButton = findViewById(R.id.ticTacToeButton)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun checkaDate(){
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
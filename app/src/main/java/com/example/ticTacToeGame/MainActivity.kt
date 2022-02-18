package com.example.ticTacToeGame

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.storybook.R

class MainActivity : AppCompatActivity() {
    lateinit var donateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        donateButton.setOnClickListener(myButtonClickListener);
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

    fun init(){
        donateButton = findViewById<TextView>(R.id.donatButton) as Button
    }
}
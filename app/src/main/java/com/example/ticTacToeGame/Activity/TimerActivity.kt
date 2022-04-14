package com.example.ticTacToeGame.Activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.storybook.R
import com.example.ticTacToe.Games.GuessTheGame
import com.example.ticTacToeGame.Games.TimerService
import kotlin.math.roundToInt

class TimerActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var timerButton: ImageButton
    private lateinit var timerView: TextView

    private var timerStart = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        init()
        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))

        backButton.setOnClickListener(backListener)
        timerButton.setOnClickListener(timerButtonListener)
    }
    private var updateTime : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIMER_EXTRA, 0.0)
            timerView.text = getTimeStringFromDouble(time)
        }
    }

    private fun getTimeStringFromDouble(time: Double): String{
        val resultIntent = time.roundToInt()
        val hours = resultIntent % 86400 / 3600
        val minutes = resultIntent % 86400 % 3600 / 60
        val seconds = resultIntent % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hour: Int, minute: Int, second: Int): String = String.format("%02d:%02d:%03d", hour, minute, second)

    var backListener: View.OnClickListener = View.OnClickListener {
        toMain()
    }

    var timerButtonListener: View.OnClickListener = View.OnClickListener {
        startStopTimer()
    }

    private fun startStopTimer() {
        if (timerStart){
            stopService(serviceIntent)
            timerStart = false
            time = 0.0
            val handler = Handler()
            handler.postDelayed({
                timerView.text = getString(R.string.timer_text)
            }, 2000)

        }
        else{
            serviceIntent.putExtra(TimerService.TIMER_EXTRA, time)
            startService(serviceIntent)
            timerStart = true
        }
    }

    private fun toMain(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun init(){
        timerView = findViewById(R.id.timerView)
        backButton = findViewById(R.id.backButton)
        timerButton = findViewById(R.id.startStopButton)
        serviceIntent = Intent(applicationContext, TimerService::class.java)
    }
}
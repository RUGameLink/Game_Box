package com.example.ticTacToeGame.Activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.storybook.R
import com.example.ticTacToeGame.Games.TimerGame
import com.example.ticTacToeGame.Services.TimerService
import kotlin.math.roundToInt

class TimerActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var timerButton: ImageButton
    private lateinit var timerView: TextView
    private lateinit var missionView: TextView

    private lateinit var timerGame: TimerGame

    private lateinit var serviceIntent: Intent
    private var time = 0.0
    private var timerStatus = 0

    private lateinit var resultView: ListView
    private lateinit var results: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        init()
        timerGame = TimerGame()
        missionView.text = "${timerGame.getGameTime()}"
        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))

        backButton.setOnClickListener(backListener)
        timerButton.setOnClickListener(timerButtonListener)

        hideBars()
    }
    private var updateTime : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIMER_EXTRA, 0.0)
            timerView.text = getTimeStringFromDouble(time)
        }
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            true
        } else super.onKeyDown(keyCode, event)
    }

    private fun getTimeStringFromDouble(time: Double): String{
        val resultIntent = time.roundToInt()
        val hours = resultIntent % 86400 / 3600
        val minutes = resultIntent % 86400 % 3600 / 60
        val seconds = resultIntent % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hour: Int, minute: Int, second: Int): String = String.format("%02d:%02d:%02d", hour, minute, second)

    private var backListener: View.OnClickListener = View.OnClickListener {
        toMain()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private var timerButtonListener: View.OnClickListener = View.OnClickListener {
        startStopTimer()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun startStopTimer() {
        when(timerStatus){
            0 -> {
                timerView.setTextColor(applicationContext.getColor(R.color.white))
                serviceIntent.putExtra(TimerService.TIMER_EXTRA, time)
                startService(serviceIntent)
                timerStatus = 1
            }
            1 -> {
                stopService(serviceIntent)
                timerStatus = 2

                val handler = Handler()
                handler.postDelayed({
                    addResToList()
                    checkStatus()
                }, 200)

            }
            2 -> {

            val handler = Handler()
            handler.postDelayed({
                timerView.setTextColor(applicationContext.getColor(R.color.white))
                timerView.text = getString(R.string.timer_text)
                time = 0.0
                timerStatus = 0
            }, 100)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkStatus(){
        var res = timerGame.checkResult(timerView.text.toString())
        if(res) {
            timerView.setTextColor(applicationContext.getColor(R.color.lime_green))
            timerGame = TimerGame()
            missionView.text = "${timerGame.getGameTime()}"
        }
        else
            timerView.setTextColor(applicationContext.getColor(R.color.crimson))
        println(res)
    }

    private fun toMain(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun addResToList(){
        results.reverse()
        results.add(timerView.text.toString())
        if (results.size > 3){
            results.removeFirst()
        }
        results.reverse()
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results)
        resultView.adapter = adapter

    }

    private fun init(){
        results = ArrayList()
        resultView = findViewById(R.id.resultView)
        timerView = findViewById(R.id.timerView)
        backButton = findViewById(R.id.backButton)
        timerButton = findViewById(R.id.startStopButton)
        missionView = findViewById(R.id.missionView)
        serviceIntent = Intent(applicationContext, TimerService::class.java)
    }
}
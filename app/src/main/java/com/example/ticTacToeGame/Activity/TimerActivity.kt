package com.example.ticTacToeGame.Activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.storybook.R
import com.example.ticTacToeGame.Services.Presets
import com.example.ticTacToeGame.Games.TimerGame
import com.example.ticTacToeGame.Services.TimerService
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import nl.dionsegijn.konfetti.xml.KonfettiView
import kotlin.math.roundToInt

class TimerActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var timerButton: ImageButton
    private lateinit var timerView: TextView
    private lateinit var missionView: TextView
    lateinit var viewKonfetti: KonfettiView

    private lateinit var timerGame: TimerGame

    private lateinit var serviceIntent: Intent
    private var time = 0.0
    private var timerStatus = 0

    private lateinit var resultView: ListView
    private lateinit var results: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>


    private val database = Firebase.database("https://gameboxapp-42309-default-rtdb.europe-west1.firebasedatabase.app")
    private lateinit var auth: String

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

        auth = intent.getStringExtra("uid").toString()
        println("Auth Timer ${auth}")
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

    @RequiresApi(Build.VERSION_CODES.M)
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
            viewKonfetti.start(Presets.parade())
            setTotalGameDB()
            setWinsCountDB()
            timerView.setTextColor(applicationContext.getColor(R.color.lime_green))
            timerGame = TimerGame()
            missionView.text = "${timerGame.getGameTime()}"
        }
        else{
            timerView.setTextColor(applicationContext.getColor(R.color.crimson))
            setTotalGameDB()
        }

        println(res)
    }

    private fun setTotalGameDB(){
        database.getReference(auth).child("timerAllGamesCount").get().addOnSuccessListener {
            var toTalGameCount = it.value
            if(toTalGameCount == null){
                timerGame.setTotalGamesCount(0)
                database.getReference(auth).child("timerAllGamesCount").setValue(timerGame.getTotalGamesCount())
            }
            else{
                var res = toTalGameCount.toString().toInt()
                timerGame.setTotalGamesCount(res)
                database.getReference(auth).child("timerAllGamesCount").setValue(timerGame.getTotalGamesCount())
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    private fun setWinsCountDB(){
        database.getReference(auth).child("timerWinsCount").get().addOnSuccessListener {
            var timerWinsCount = it.value
            if(timerWinsCount == null){
                timerGame.setTimerWinsCount(0)
                database.getReference(auth).child("timerWinsCount").setValue(timerGame.getTimerWinsCount())
            }
            else{
                var res = timerWinsCount.toString().toInt()
                timerGame.setTimerWinsCount(res)
                database.getReference(auth).child("timerWinsCount").setValue(timerGame.getTimerWinsCount())
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun toMain(){
        val handler = Handler()
        handler.postDelayed({
            stopService(serviceIntent)
            timerView.setTextColor(applicationContext.getColor(R.color.white))
            timerView.text = getString(R.string.timer_text)
            time = 0.0
            timerStatus = 0
        }, 100)

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
        viewKonfetti = findViewById(R.id.konfettiView)
    }
}
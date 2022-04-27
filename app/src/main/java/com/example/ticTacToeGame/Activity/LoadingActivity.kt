package com.example.ticTacToeGame.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.storybook.R

class LoadingActivity : AppCompatActivity() {
    lateinit var progressBar: ProgressBar
    lateinit var textLoading: TextView
    lateinit var handler: Handler
    var i = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        init()
        loading()
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

    fun loading(){
        handler = Handler()

        Thread(Runnable {
            // this loop will run until the value of i becomes 99
            while (i < 100) {
                i += 1
                // Update the progress bar and display the current value
                handler.post(Runnable {
                    progressBar.progress = i
                    // setting current progress to the textview
                    textLoading.text = "Loading " + i.toString() + "%"
                })
                try {
                    Thread.sleep(27)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            handler.postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            },0)
        }).start()


    }

    fun init(){
        progressBar = findViewById<ProgressBar>(R.id.progressBar) as ProgressBar
        textLoading = findViewById<TextView>(R.id.textView) as TextView
    }
}
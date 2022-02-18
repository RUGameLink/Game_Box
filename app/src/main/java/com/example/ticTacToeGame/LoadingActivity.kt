package com.example.ticTacToeGame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
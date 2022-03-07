package com.example.ticTacToeGame.Activity

import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.storybook.R

class SoundPadActivity : AppCompatActivity() {
    var check = true
    lateinit var backButton: Button

    lateinit var soundPool: SoundPool
    /*lateinit var sound1: Int
    lateinit var sound2: Int
    lateinit var sound3: Int
    lateinit var sound4: Int
    lateinit var sound5: Int
    lateinit var sound6: Int
    lateinit var sound7: Int
    lateinit var sound8: Int
    lateinit var sound9: Int
   // lateinit var mp: MediaPlayer
   // lateinit var mp2: MediaPlayer

    lateinit var bonfireButton: Button
    lateinit var rainButton: Button

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soundpad)
        init()
        backButton.setOnClickListener(backListener)

        bonfireButton.setOnClickListener(bonfireListener)
        rainButton.setOnClickListener(rainListener)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){//Проверка версии sdk системы для применения разной инициализации soundPool
            var audioAttributes: AudioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()

            soundPool = SoundPool.Builder()
                .setMaxStreams(9)
                .setAudioAttributes(audioAttributes)
                .build()
        }
        else{
            soundPool = SoundPool(9, AudioManager.STREAM_MUSIC, 0)
        }
    }

    private var backListener: View.OnClickListener = View.OnClickListener {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private var rainListener: View.OnClickListener = View.OnClickListener {


        when (check){
            true -> {
            //    mp = MediaPlayer.create(this, R.raw.rain)
              //  mp.isLooping = true
                //mp.start()

                rainButton.backgroundTintList = getColorStateList(com.example.storybook.R.color.purple_200)
                check = !check
            }
            false -> {
            //    mp.stop()
                rainButton.backgroundTintList = getColorStateList(com.example.storybook.R.color.white)
                check = !check
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private var bonfireListener: View.OnClickListener = View.OnClickListener {


        when (check){
            true -> {
             //   mp2 = MediaPlayer.create(this, R.raw.bonfire)
             //   mp2.isLooping = true
             //   mp2.start()
                bonfireButton.backgroundTintList = getColorStateList(com.example.storybook.R.color.purple_200)
                check = !check
            }
            false -> {
             //   mp.stop()
                bonfireButton.backgroundTintList = getColorStateList(com.example.storybook.R.color.white)
                check = !check
            }
        }
    }

    fun init(){
        backButton = findViewById(R.id.backButton)
        bonfireButton = findViewById(R.id.bonfireButton)
        rainButton = findViewById(R.id.rainButton)
    }*/
}
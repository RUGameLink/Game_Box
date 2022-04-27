package com.example.ticTacToeGame.Activity

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.storybook.R
import com.example.ticTacToeGame.Fragment.MenuFragment
import java.util.*

class MainActivity : AppCompatActivity() {
 /*   private lateinit var donateButton: Button
    private lateinit var ticTacToeButton: Button
    private lateinit var soundPadButton: Button
    private lateinit var guessTheNumberButton: Button*/

    private lateinit var star1Image: ImageView
    private lateinit var star2Image: ImageView
    private lateinit var moonImage: ImageView
    private lateinit var sunnImage: ImageView

    private lateinit var menuFrame: FrameLayout
    private lateinit var menuFragment: MenuFragment

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
      /*  donateButton.setOnClickListener(myButtonClickListener)
        ticTacToeButton.setOnClickListener(ticTacToeListener)
        soundPadButton.setOnClickListener(soundPadListener)
        guessTheNumberButton.setOnClickListener(guessTheNumberListener)*/
        checkaDate()
        //guessTheNumberShow()
        menuFragmentShow()

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

 /*   private var soundPadListener: View.OnClickListener = View.OnClickListener {
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
    }*/

    private fun menuFragmentShow(){
    //    val i = Intent(this, GuessTheNumberActivity::class.java)
    //    startActivity(i)
        menuFragment = MenuFragment()
        var fragmentTransaction :FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(menuFrame.id, menuFragment)
        fragmentTransaction.commit()
    }

  /*  private fun ticTacToeShow(){
        val i = Intent(this, TicTacToeActivity::class.java)
        startActivity(i)
    }

    private fun soundPadShow(){
        val i = Intent(this, SoundPadActivity::class.java)
        startActivity(i)
    }*/

    private fun init(){
        star1Image = findViewById(R.id.starView1)
        star2Image = findViewById(R.id.starView2)
        moonImage = findViewById(R.id.moonView)
        sunnImage = findViewById(R.id.sunnView)
    /*    donateButton = findViewById(R.id.backButton)

        ticTacToeButton = findViewById(R.id.tictactoeButton)
        soundPadButton = findViewById(R.id.soundpadButton2)
        guessTheNumberButton = findViewById(R.id.guessMyNumberButton)*/
        menuFrame = findViewById(R.id.menuFrame)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun checkaDate(){
        val date: String =
            SimpleDateFormat("HH").format(Calendar.getInstance().time)

        when(date){
            "21", "22", "23", "00", "01", "02", "03", "04", "05", "06", "07"
            -> {
                star1Image.visibility = View.VISIBLE
                star2Image.visibility = View.VISIBLE
                moonImage.visibility = View.VISIBLE
                sunnImage.visibility = View.INVISIBLE
            }
            "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"
            -> {
                star1Image.visibility = View.INVISIBLE
                star2Image.visibility = View.INVISIBLE
                moonImage.visibility = View.INVISIBLE
                sunnImage.visibility = View.VISIBLE
            }
        }
    }
}
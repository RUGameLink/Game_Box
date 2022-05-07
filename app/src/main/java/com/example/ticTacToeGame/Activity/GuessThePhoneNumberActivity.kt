package com.example.ticTacToeGame.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.ticTacToe.Games.GuessTheGame
import com.example.storybook.R
import com.example.ticTacToeGame.Services.Presets
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.github.muddz.styleabletoast.StyleableToast
import nl.dionsegijn.konfetti.xml.KonfettiView
import kotlin.properties.Delegates

class GuessThePhoneNumberActivity : AppCompatActivity() {

    private lateinit var minCountText: String
    private lateinit var maxCountText: String

    private lateinit var backButton: Button
    private lateinit var equalButton: Button
    private lateinit var askButton: ImageButton
    private lateinit var getNumText: EditText
    private lateinit var resText: TextView
    lateinit var viewKonfetti: KonfettiView

    private lateinit var guessTheGame: GuessTheGame

    private var maxCount by Delegates.notNull<Int>()
    private var minCount by Delegates.notNull<Int>()

    private val database = Firebase.database("https://gameboxapp-42309-default-rtdb.europe-west1.firebasedatabase.app")
    private lateinit var auth: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guessthephonenumber)

        init()
        maxCount = maxCountText.toInt()
        minCount = minCountText.toInt()

        guessTheGame = GuessTheGame(maxCount, minCount, true)
        guessTheGame.generateNumber()

        backButton.setOnClickListener(backListener)
        equalButton.setOnClickListener(equalListener)
        askButton.setOnClickListener(askListener)

        hideBars()

        auth = intent.getStringExtra("uid").toString()
        println("Auth Guess V2 ${auth}")
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

    private var backListener: View.OnClickListener = View.OnClickListener {
        toMain()
    }

    private var askListener: View.OnClickListener = View.OnClickListener {
        var toastText = "${getText(R.string.ask_text)} \n${minCount} - ${maxCount}"
        StyleableToast.makeText(this, toastText, Toast.LENGTH_SHORT, R.style.positive_toast).show()
    }

    private var equalListener: View.OnClickListener = View.OnClickListener {
        if(getNumText.text.isEmpty()){
            StyleableToast.makeText(this, "Введите число!", Toast.LENGTH_SHORT, R.style.negative_toast).show()
            return@OnClickListener
        }
        else{
            checkEqual()
            getNumText .setText("")
        }

    }

    private fun checkEqual() {
        var res = guessTheGame.checkNumber(getNumText.text.toString().toInt())
        when (res){
            1 -> resText.setText(R.string.num_is_big)
            2 -> resText.setText(R.string.num_is_small)
            0 -> {
                viewKonfetti.start(Presets.rain())
                resText.setText(R.string.num_is_true)
                endGame()
            }
        }
    }

    private fun setUserGameDB(){
        database.getReference(auth).child("guessTheGameUserGamesCount").get().addOnSuccessListener {
            var toTalGameCount = it.value
            if(toTalGameCount == null){
                guessTheGame.setUserWinsCount(0)
                database.getReference(auth).child("guessTheGameUserGamesCount").setValue(guessTheGame.getUserWinsCount())
            }
            else{
                var res = toTalGameCount.toString().toInt()
                guessTheGame.setUserWinsCount(res)
                database.getReference(auth).child("guessTheGameUserGamesCount").setValue(guessTheGame.getUserWinsCount())
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    private fun endGame(){
        setUserGameDB()
        StyleableToast.makeText(applicationContext, getText(R.string.end_guess_game).toString(), Toast.LENGTH_SHORT, R.style.positive_toast).show()

        val handler = Handler()
        handler.postDelayed({
            toMain()
        }, 2000)
    }

    private fun toMain(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun init(){
        minCountText= intent.getStringExtra("minCount").toString()
        maxCountText= intent.getStringExtra("maxCount").toString()

        backButton = findViewById(R.id.backButton)
        equalButton = findViewById(R.id.equalButton)
        getNumText = findViewById(R.id.getNumText)
        resText = findViewById(R.id.resText)
        askButton = findViewById(R.id.askButton)
        viewKonfetti = findViewById(R.id.konfettiView)
    }
}
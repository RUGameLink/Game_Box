package com.example.ticTacToeGame.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storybook.R
import com.example.ticTacToeGame.UserData.UserData
import com.example.ticTacToeGame.UserData.UserDataAdapter
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.github.muddz.styleabletoast.StyleableToast

class HistoryActivity : AppCompatActivity()  {
    private lateinit var historyView: RecyclerView
    private lateinit var backButton: Button

    private val database = Firebase.database("https://gameboxapp-42309-default-rtdb.europe-west1.firebasedatabase.app")
    private lateinit var auth: String

    private lateinit var userData: UserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        init()
        auth = intent.getStringExtra("uid").toString()
        println("Auth History ${auth}")

        backButton.setOnClickListener(backListener)

        userData = UserData()
        getInfoFromDB()

        hideBars()

    }


    private fun getInfoFromDB(){
        database.getReference(auth).get().addOnSuccessListener {
            var timerWinsCount = it.child("timerWinsCount").value
            if (timerWinsCount == null)
                userData.timerWinsCount = 0
            else
                userData.timerWinsCount = timerWinsCount.toString().toInt()

            //////

            var timerAllGamesCount = it.child("timerAllGamesCount").value
            if (timerAllGamesCount == null)
                userData.timerTotalCount = 0
            else
                userData.timerTotalCount = timerAllGamesCount.toString().toInt()

            //////

            var ticTacToeDrawCount = it.child("ticTacToeDrawCount").value
            if (ticTacToeDrawCount == null)
                userData.ticTacToeDrawCount = 0
            else
                userData.ticTacToeDrawCount = ticTacToeDrawCount.toString().toInt()

            ///////

            var ticTacToeZeroWinsCount = it.child("ticTacToeZeroWinsCount").value
            if (ticTacToeZeroWinsCount == null)
                userData.ticTacToeZeroWinsCount = 0
            else
                userData.ticTacToeZeroWinsCount = ticTacToeZeroWinsCount.toString().toInt()

            //////

            var ticTacToeCrossWinsCount = it.child("ticTacToeCrossWinsCount").value
            if (ticTacToeCrossWinsCount == null)
                userData.ticTacToeCrossWinsCount = 0
            else
                userData.ticTacToeCrossWinsCount = ticTacToeCrossWinsCount.toString().toInt()

            //////

            var ticTacToeAllGamesCount = it.child("ticTacToeAllGamesCount").value
            if (ticTacToeAllGamesCount == null)
                userData.ticTacToeAllGamesCount = 0
            else
                userData.ticTacToeAllGamesCount = ticTacToeAllGamesCount.toString().toInt()

            ///////

            var guessTheGameUserGamesCount = it.child("guessTheGameUserGamesCount").value
            if(guessTheGameUserGamesCount == null)
                userData.guessTheGameUserWins = 0
            else
                userData.guessTheGameUserWins = guessTheGameUserGamesCount.toString().toInt()

            /////

            var guessTheGamePhoneGamesCount = it.child("guessTheGamePhoneGamesCount").value
            if(guessTheGamePhoneGamesCount == null)
                userData.guessTheGamePhoneWins = 0
            else
                userData.guessTheGamePhoneWins = guessTheGamePhoneGamesCount.toString().toInt()

            ////

            val recyclerView: RecyclerView = findViewById(R.id.historyView)
            val linearLayoutManager = LinearLayoutManager(applicationContext)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.adapter = UserDataAdapter(userData)
        }.addOnFailureListener{
            StyleableToast.makeText(applicationContext, getText(R.string.loading_history_error).toString(), Toast.LENGTH_SHORT, R.style.negative_toast).show()
        }
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

    var backListener: View.OnClickListener = View.OnClickListener {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun init(){
        historyView = findViewById(R.id.historyView)
        backButton = findViewById(R.id.backButton2)
    }
}
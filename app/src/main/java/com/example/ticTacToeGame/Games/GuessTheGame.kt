package com.example.ticTacToeGame.Games

import android.widget.Button
import android.widget.TextView
import kotlin.properties.Delegates

class GuessTheGame {
    private lateinit var minCountText: String
    private lateinit var maxCountText: String

    private lateinit var biggerButton: Button
    private lateinit var lessButton: Button
    private lateinit var endButton: Button
    lateinit var gameText: TextView
    private lateinit var rangeText: TextView

    private var maxCount by Delegates.notNull<Int>()
    private var minCount by Delegates.notNull<Int>()
    private var midCount by Delegates.notNull<Int>()


}
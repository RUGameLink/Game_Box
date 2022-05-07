package com.example.ticTacToeGame.UserData


data class UserData(
    var ticTacToeAllGamesCount: Int ?= null,
    var ticTacToeCrossWinsCount: Int ?= null,
    var ticTacToeZeroWinsCount: Int ?= null,
    var ticTacToeDrawCount: Int ?= null,

    var timerWinsCount: Int ?= null,
    var timerTotalCount: Int ?= null,

    var guessTheGamePhoneWins: Int ?= null,
    var guessTheGameUserWins: Int ?= null
)

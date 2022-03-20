package com.example.ticTacToeGame.Fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.storybook.R


class guessGameFragment : Fragment() {
    lateinit var backButton: Button
    lateinit var startButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_guess_game, container, false)

        init(view)
        backButton.setOnClickListener(backListener)
        startButton.setOnClickListener(startListener)
        return view
    }

    private var backListener: View.OnClickListener = View.OnClickListener {
        menuShow()
    }

    private var startListener: View.OnClickListener = View.OnClickListener {
        startGame()
    }

    private fun startGame(){
        val builder = AlertDialog.Builder(guessGameFragment.container)
    }

    private fun menuShow(){
        val menuFragment = MenuFragment()
        var fragmentTransaction : FragmentTransaction = requireFragmentManager().beginTransaction()
        fragmentTransaction.replace(R.id.menuFrame, menuFragment)
        fragmentTransaction.commit()
    }

    private fun init(view: View){
        backButton = view.findViewById(R.id.backButton)
        startButton = view.findViewById(R.id.backButton)

    }
}
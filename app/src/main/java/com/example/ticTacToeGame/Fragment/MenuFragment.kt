package com.example.ticTacToeGame.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import com.example.storybook.R
import com.example.ticTacToeGame.Activity.MainActivity
import com.example.ticTacToeGame.Activity.SoundPadActivity
import com.example.ticTacToeGame.Activity.TicTacToeActivity
import com.example.ticTacToeGame.Games.GuessTheGame

class MenuFragment : Fragment() {
    private lateinit var donateButton: Button
    private lateinit var ticTacToeButton: Button
    private lateinit var soundPadButton: Button
    private lateinit var guessTheNumberButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        init(view)
        donateButton.setOnClickListener(myButtonClickListener)
        ticTacToeButton.setOnClickListener(ticTacToeListener)
        soundPadButton.setOnClickListener(soundPadListener)
        guessTheNumberButton.setOnClickListener(guessTheNumberListener)

        return view
    }

    private var soundPadListener: View.OnClickListener = View.OnClickListener {
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

    private fun guessTheNumberShow(){
        val guessGameFragment = guessGameFragment()
        var fragmentTransaction :FragmentTransaction = requireFragmentManager().beginTransaction()
        fragmentTransaction.replace(R.id.menuFrame, guessGameFragment)
        fragmentTransaction.commit()
    }

    private fun donating(){
        val url = "https://boosty.to/sadsm"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun ticTacToeShow(){
        val i = Intent(getActivity(), TicTacToeActivity::class.java)
        startActivity(i)
    }

    private fun soundPadShow(){
        val i = Intent(getActivity(), SoundPadActivity::class.java)
        startActivity(i)
    }

    private fun init(view: View){
        donateButton = view.findViewById(R.id.db)
        ticTacToeButton = view.findViewById(R.id.tictactoeButton)!!
        soundPadButton = view.findViewById(R.id.soundpadButton)
        guessTheNumberButton = view.findViewById(R.id.guessMyNumberButton)
    }

}
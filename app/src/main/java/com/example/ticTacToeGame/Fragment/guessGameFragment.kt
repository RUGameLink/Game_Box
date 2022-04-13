package com.example.ticTacToeGame.Fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.storybook.R
import com.example.ticTacToeGame.Activity.GuessTheNumberActivity
import com.example.ticTacToeGame.Activity.GuessThePhoneNumberActivity


class guessGameFragment : Fragment() {
    lateinit var backButton: Button
    lateinit var guessMyNumberButton: Button
    lateinit var guessPhNumberButton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_guess_game, container, false)

        init(view)
        backButton.setOnClickListener(backListener)
        guessMyNumberButton.setOnClickListener(myNumberListener)
        guessPhNumberButton.setOnClickListener(PhNumberListener)
        return view
    }

    private var backListener: View.OnClickListener = View.OnClickListener {
        menuShow()
    }

    private var myNumberListener: View.OnClickListener = View.OnClickListener {
        startGameMyNum(it)
    }

    private var PhNumberListener: View.OnClickListener = View.OnClickListener {
        startGamePhNum(it)
    }

    private fun startGameMyNum(view: View) {
        val inflater = layoutInflater
        val builder = AlertDialog.Builder(view.context)
        val dialoglayout: View = inflater.inflate(R.layout.guess_game_dialog, null)
        builder.setView(dialoglayout)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(R.drawable.background)

        val dialogBtn = dialoglayout.findViewById<Button>(R.id.backButton)
        val dialogMinTextView = dialoglayout.findViewById<EditText>(R.id.minNumText)
        val dialogMaxTextView = dialoglayout.findViewById<EditText>(R.id.maxNumText)

        dialogBtn.setOnClickListener{
            if(dialogMinTextView.text.isEmpty() || dialogMaxTextView.text.isEmpty()){
                Toast.makeText(activity, "Заполните поля для старта!", Toast.LENGTH_SHORT).show()
            }
            else {
                var minCount = dialogMinTextView.text.toString()
                var maxCount = dialogMaxTextView.text.toString()
                if(maxCount.toInt() <= minCount.toInt()){
                    Toast.makeText(activity, "Числа должны быть неодинаковыми, минимальное значение меньше максимального!", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(activity, "Все готово, начинаем", Toast.LENGTH_SHORT).show()

                    val i = Intent(activity, GuessTheNumberActivity::class.java)
                    i.putExtra("minCount", minCount)
                    i.putExtra("maxCount", maxCount)
                    startActivity(i)
                }
            }
        }
    }

    private fun startGamePhNum(view: View) {
        val inflater = layoutInflater
        val builder = AlertDialog.Builder(view.context)
        val dialoglayout: View = inflater.inflate(R.layout.guess_game_dialog, null)
        builder.setView(dialoglayout)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(R.drawable.background2)

        val dialogBtn = dialoglayout.findViewById<Button>(R.id.backButton)
        val dialogMinTextView = dialoglayout.findViewById<EditText>(R.id.minNumText)
        val dialogMaxTextView = dialoglayout.findViewById<EditText>(R.id.maxNumText)

        dialogBtn.setOnClickListener{
            if(dialogMinTextView.text.isEmpty() || dialogMaxTextView.text.isEmpty()){
                Toast.makeText(activity, "Заполните поля для старта!", Toast.LENGTH_SHORT).show()
            }
            else {
                var minCount = dialogMinTextView.text.toString()
                var maxCount = dialogMaxTextView.text.toString()
                if(maxCount.toInt() <= minCount.toInt()){
                    Toast.makeText(activity, "Числа должны быть неодинаковыми, минимальное значение меньше максимального!", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(activity, "Все готово, начинаем", Toast.LENGTH_SHORT).show()

                    val i = Intent(activity, GuessThePhoneNumberActivity::class.java)
                    i.putExtra("minCount", minCount)
                    i.putExtra("maxCount", maxCount)
                    startActivity(i)
                }
            }
        }

    }

    private fun menuShow(){
        val menuFragment = MenuFragment()
        var fragmentTransaction : FragmentTransaction = requireFragmentManager().beginTransaction()
        fragmentTransaction.replace(R.id.menuFrame, menuFragment)
        fragmentTransaction.commit()
    }

    private fun init(view: View){
        backButton = view.findViewById(R.id.backButton)
        guessMyNumberButton = view.findViewById(R.id.guessMyNumberButton)
        guessPhNumberButton = view.findViewById(R.id.guessPhNumberButton)
    }
}
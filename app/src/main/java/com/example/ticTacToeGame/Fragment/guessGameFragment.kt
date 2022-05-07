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
import io.github.muddz.styleabletoast.StyleableToast


class guessGameFragment : Fragment() {
    lateinit var backButton: Button
    lateinit var guessMyNumberButton: Button
    lateinit var guessPhNumberButton: Button

    private lateinit var auth: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_guess_game, container, false)

        init(view)
        backButton.setOnClickListener(backListener)
        guessMyNumberButton.setOnClickListener(myNumberListener)
        guessPhNumberButton.setOnClickListener(PhNumberListener)

        auth = requireArguments().getString("uid").toString()
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
                activity?.let { it1 -> StyleableToast.makeText(it1, getText(R.string.bad_start_guess).toString(), Toast.LENGTH_SHORT, R.style.negative_toast).show() }
            }
            else {
                var minCount = dialogMinTextView.text.toString()
                var maxCount = dialogMaxTextView.text.toString()
                if(maxCount.toInt() <= minCount.toInt()){
                    activity?.let { it1 -> StyleableToast.makeText(it1, getText(R.string.negative_counts).toString(), Toast.LENGTH_SHORT, R.style.negative_toast).show() }
                }
                else{
                //    Toast.makeText(activity, "Все готово, начинаем", Toast.LENGTH_SHORT).show()
                    activity?.let { it1 -> StyleableToast.makeText(it1,
                        getText(R.string.start_guess_game).toString(), Toast.LENGTH_SHORT, R.style.positive_toast).show() }

                    val i = Intent(activity, GuessTheNumberActivity::class.java)
                    i.putExtra("minCount", minCount)
                    i.putExtra("maxCount", maxCount)
                    i.putExtra("uid", auth)
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
                activity?.let { it1 -> StyleableToast.makeText(it1, getText(R.string.bad_start_guess).toString(), Toast.LENGTH_SHORT, R.style.negative_toast).show() }
            }
            else {
                var minCount = dialogMinTextView.text.toString()
                var maxCount = dialogMaxTextView.text.toString()
                if(maxCount.toInt() <= minCount.toInt()){
                    activity?.let { it1 -> StyleableToast.makeText(it1, getText(R.string.negative_counts).toString(), Toast.LENGTH_SHORT, R.style.negative_toast).show() }
                }
                else{
                    activity?.let { it1 -> StyleableToast.makeText(it1,
                        getText(R.string.start_guess_game).toString(), Toast.LENGTH_SHORT, R.style.positive_toast).show() }

                    val i = Intent(activity, GuessThePhoneNumberActivity::class.java)
                    i.putExtra("minCount", minCount)
                    i.putExtra("maxCount", maxCount)
                    i.putExtra("uid", auth)
                    startActivity(i)
                }
            }
        }

    }

    private fun menuShow(){
        val bundle = Bundle()
        bundle.putString("uid", auth)
        val menuFragment = MenuFragment()
        menuFragment.arguments = bundle
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
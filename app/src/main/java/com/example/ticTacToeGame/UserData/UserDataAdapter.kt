package com.example.ticTacToeGame.UserData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.storybook.R

class UserDataAdapter(private val userData: UserData): RecyclerView.Adapter<UserDataAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val ticTacToeTotalCount: TextView = itemView.findViewById(R.id.ticTacToeTotalCount)
        val ticTacToeCrossWinsCount: TextView = itemView.findViewById(R.id.ticTacToeCrossWinsCount)
        val ticTacToeZeroWinsCount: TextView = itemView.findViewById(R.id.ticTacToeZeroWinsCount)
        val ticTacToeDrawCount: TextView = itemView.findViewById(R.id.ticTacToeDrawCount)

        val timerTotalCount: TextView = itemView.findViewById(R.id.timerTotalCount)
        val timerWinsCount: TextView = itemView.findViewById(R.id.timerWinsCount)

        val phoneWinsCount: TextView = itemView.findViewById(R.id.phoneWinsCount)
        val userWinsCount: TextView = itemView.findViewById(R.id.userWinsCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.user_data_items, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.ticTacToeTotalCount.text = userData.ticTacToeAllGamesCount.toString()
        holder.ticTacToeCrossWinsCount.text = userData.ticTacToeCrossWinsCount.toString()
        holder.ticTacToeZeroWinsCount.text = userData.ticTacToeZeroWinsCount.toString()
        holder.ticTacToeDrawCount.text = userData.ticTacToeDrawCount.toString()

        holder.timerTotalCount.text = userData.timerTotalCount.toString()
        holder.timerWinsCount.text = userData.timerWinsCount.toString()

        holder.phoneWinsCount.text = userData.guessTheGamePhoneWins.toString()
        holder.userWinsCount.text = userData.guessTheGameUserWins.toString()
    }

    override fun getItemCount(): Int {
        return 1
    }

}
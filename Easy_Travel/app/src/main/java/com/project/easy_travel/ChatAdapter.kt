package com.project.easy_travel

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.project.easy_travel.Model.ChatModel

class ChatAdapter (
        private val comments:List<ChatModel>

        ) : RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

    class ChatHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val titleText : TextView = itemView.findViewById(R.id.chatText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chat_list_element, parent, false)
        return ChatHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        val currentItem = comments[position]
        holder.itemView.apply {
            val titleText : TextView = findViewById<TextView>(R.id.chatText)
            titleText.text = currentItem.text
            if (currentItem.isYou) {
                titleText.setBackgroundColor(Color.parseColor("#AE6B4E"))
                titleText.gravity = Gravity.END
            } else {
                titleText.setBackgroundColor(Color.parseColor("#399AE8"))
            }
        }

    }

    override fun getItemCount(): Int {
        return comments.size
    }


}
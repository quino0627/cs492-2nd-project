package com.example.quino0627.mastagram

import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.quino0627.mastagram.Model.User
import com.example.quino0627.mastagram.R.id.parent

class FriendsAdapter(val friendsList: ArrayList<User>): RecyclerView.Adapter<FriendsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.friend_view, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return friendsList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.boundItem(friendsList[p1])
    }

    private lateinit var listener: AdapterView.OnItemSelectedListener

    fun setClickListener(listener: AdapterView.OnItemSelectedListener){
        this.listener = listener
    }


    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val container = itemView.findViewById<CardView>(R.id.friend_card_view)
        fun boundItem(friend: User){
            val textName = itemView.findViewById<AppCompatTextView>(R.id.friend_name) as AppCompatTextView
            val textPhone = itemView.findViewById<AppCompatTextView>(R.id.friend_phone) as AppCompatTextView
            Log.d("IN THE BOUNDITEM", "asdf")
            textName.text = friend.name
            textPhone.text = friend.phone
        }

    }

    interface onItemSelectedListener{
        fun onItemSelected(selectedFriend: User)
    }
}
package com.example.quino0627.mastagram

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.quino0627.mastagram.Model.Friend
import com.example.quino0627.mastagram.Model.Post
import com.example.quino0627.mastagram.Model.User
import com.example.quino0627.mastagram.R.id.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.jetbrains.anko.support.v4.toast

class FriendsAdapter(val friendsList: ArrayList<Friend>): RecyclerView.Adapter<FriendsAdapter.ViewHolder>(){

    lateinit var retrofitApi:RetrofitApi

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.friend_view, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return friendsList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.boundItem(friendsList[p1])
        val imageForFollwing = p0.itemView.findViewById<ImageView>(R.id.iv_heart) as ImageView
        //imageForFollwing.setOnClickListener(){
        retrofitApi = APIUtils.getUserService()
        //}
        imageForFollwing.setOnClickListener(
            {
                Log.e("TOUCHED", "!!")
                if(friendsList[p1].real_friend == false){
                    imageForFollwing.setImageResource(R.drawable.heart_red)
                    friendsList[p1].real_friend = true
                    Log.e("FOLLWOING", "!!")

                    var call = retrofitApi.followFunction(MainActivity.myFBUserId, friendsList[p1])
                    call.enqueue(object: Callback<User> {
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            Log.d("IN THE CALLBACK", "asdf")
                            Log.d("ASDF", (response.body() == null).toString())
                            if (response.isSuccessful){
                                Log.e("FOLLOWING SUCCESSFUL", "!!")
                            }
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Log.e("Error: " ,t.message)
                        }
                    })


                } else{
                    imageForFollwing.setImageResource(R.drawable.heart_none)
                    friendsList[p1].real_friend = false
                    Log.e("UNFOLLWOING", "!!")

                    var call = retrofitApi.unFollowFunction(MainActivity.myFBUserId, friendsList[p1])
                    call.enqueue(object: Callback<User> {
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            Log.d("IN THE CALLBACK", "asdf")
                            Log.d("ASDF", (response.body() == null).toString())
                            if (response.isSuccessful){
                                Log.e("UNFOLLOWING SUCCESSFUL", "!!")
                            }
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Log.e("Error: " ,t.message)
                        }
                    })

                }
            })

    }

    private lateinit var listener: AdapterView.OnItemSelectedListener

    fun setClickListener(listener: AdapterView.OnItemSelectedListener){
        this.listener = listener
    }


    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val container = itemView.findViewById<CardView>(R.id.friend_card_view)
        fun boundItem(friend: Friend){
            val textName = itemView.findViewById<AppCompatTextView>(R.id.friend_name) as AppCompatTextView
            val textPhone = itemView.findViewById<AppCompatTextView>(R.id.friend_phone) as AppCompatTextView
            val imageOfHeIsMyFriend = itemView.findViewById<ImageView>(R.id.iv_heart) as ImageView
            Log.d("IN THE BOUNDITEM", "asdf")
            textName.text = friend.name
            textPhone.text = friend.phone
            if (friend.real_friend){
                Log.e("HE IS MY REAL FRIEND!",  friend.name)
                imageOfHeIsMyFriend.setImageResource(R.drawable.heart_red)
            }
        }



    }

    interface onItemSelectedListener{
        fun onItemSelected(selectedFriend: User)
    }
}
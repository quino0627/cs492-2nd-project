package com.example.quino0627.mastagram

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.quino0627.mastagram.Model.Post
import org.jetbrains.anko.sdk25.coroutines.onClick

class MyPostsAdapter(val postList: List<Post>, val context: Context): RecyclerView.Adapter<MyPostsAdapter.ViewHolder>(){

    private lateinit var listener1 : MyPostsAdapter.OnItemSelectedListener

    fun setClickListener(listener1: OnItemSelectedListener){
        this.listener1 = listener1
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyPostsAdapter.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.gallery_view,p0, false)
        return MyPostsAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(p0: MyPostsAdapter.ViewHolder, p1: Int) {
        Glide.with(context).load("http://143.248.140.106:1080/"+ postList[p1].pirctueUrl).into(p0.postImage)
        p0.container.onClick {
            listener1.onItemSelected(postList[p1])
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var postImage = itemView.findViewById<ImageView>(R.id.iview)
        var container = itemView.findViewById<CardView>(R.id.image_container)

    }

    interface  OnItemSelectedListener{
        fun onItemSelected(selectedPost: Post)
    }

}
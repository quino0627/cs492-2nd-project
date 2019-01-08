package com.example.quino0627.mastagram

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.quino0627.mastagram.Model.Post
import kotlinx.android.synthetic.main.post_view.view.*
import org.w3c.dom.Text

class PostItemAdapter(val postList: List<Post>, val context: Context):RecyclerView.Adapter<PostItemAdapter.ViewHolder>(){



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.post_view, p0, false))
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.postUserName.text = postList[p1].uploader_name
//        Log.e("ASDF",postList.get(p1).uploader_name)
        p0.postContent.text = postList[p1].contents
//        p0.tag1.text = "not"
//        p0.tag2.text = "yet"
//        p0.tag3.text = "defined"
        p0.tag1.setText(if (postList.get(p1).tag[0] != null) postList.get(p1).tag[0] else "not")
        p0.tag2.setText(if (postList.get(p1).tag[1] != null) postList.get(p1).tag[1] else "yet")
        p0.tag3.setText(if (postList.get(p1).tag[2] != null) postList.get(p1).tag[2] else "defined")
        Glide.with(context).load("http://143.248.140.106:1080/"+ postList[p1].pirctueUrl).into(p0.postImage)
        //Image need to implement
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var postUserName = itemView.findViewById<TextView>(R.id.user_name)
        var postContent = itemView.findViewById<TextView>(R.id.content)
        var postImage = itemView.findViewById<ImageView>(R.id.thumbnail)
        var tag1 = itemView.findViewById<TextView>(R.id.post_tag_1)
        var tag2 = itemView.findViewById<TextView>(R.id.post_tag_2)
        var tag3 = itemView.findViewById<TextView>(R.id.post_tag_3)
    }
}
package com.example.quino0627.mastagram

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.post_view.*

class PostDetailsActivity(): AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_view)

        user_name.text = intent.getStringExtra("name")
        Glide.with(this).load("http://143.248.140.106:1080/"+ intent.getStringExtra("imageUrl")).into(thumbnail)
        content.text = intent.getStringExtra("content")
        post_tag_1.text = intent.getStringExtra("tag1")
        post_tag_2.text = intent.getStringExtra("tag2")
        post_tag_3.text = intent.getStringExtra("tag3")

    }

}
package com.example.quino0627.mastagram

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.util.Log
import kotlinx.android.synthetic.main.activity_upload_post.*

class UploadPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_post)

        val Picture = intent.getStringExtra("image")
        Log.d("THIS IS INTENTED IMAGE", Picture)
        val picture_uri = Uri.parse(Picture)
        //val imageView = findViewById<ImageView>(R.id.fullScreenImageView)

        val imageView = uploading_image
        imageView.setImageURI(picture_uri)
        //imageView.setImageResource(imgResId)
    }
}

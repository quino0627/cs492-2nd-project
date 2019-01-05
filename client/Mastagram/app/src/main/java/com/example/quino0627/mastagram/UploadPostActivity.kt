package com.example.quino0627.mastagram

import android.app.PendingIntent.getActivity
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.quino0627.mastagram.Model.Post
import kotlinx.android.synthetic.main.activity_upload_post.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import android.provider.MediaStore
import android.graphics.Bitmap
import android.content.Intent
import android.provider.MediaStore.Images.Media.getBitmap
import java.io.FileNotFoundException
import java.io.IOException

import java.io.ByteArrayOutputStream


class UploadPostActivity : AppCompatActivity() {

    lateinit var retrofitApi:RetrofitApi
    lateinit var editContent:EditText
    lateinit var btnSave:Button
    lateinit var tag1:TextView
    lateinit var tag2:TextView
    lateinit var tag3:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_post)

        var Picture = intent.getStringExtra("image")
        Log.d("THIS IS INTENTED IMAGE", Picture)
        var picture_uri = Uri.parse(Picture)
        //val imageView = findViewById<ImageView>(R.id.fullScreenImageView)

        val imageView = uploading_image
        imageView.setImageURI(picture_uri)
        //imageView.setImageResource(imgResId)
        if(Picture.indexOf("file://")==-1){
            Picture = "file://"+Picture
        }
        picture_uri = Uri.parse(Picture)
        editContent = findViewById(R.id.editContent)
        tag1 = findViewById(R.id.tag1)
        tag2 = findViewById(R.id.tag2)
        tag3 = findViewById(R.id.tag3)
        btnSave = findViewById(R.id.upload_btn)

        retrofitApi = APIUtils.getUserService()

        btnSave.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                var bitmap: Bitmap? = null

//                val result = async(CommonPool){
//
//                }

                    try {
                        Log.d("I'm IN TRY", (bitmap==null).toString())
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picture_uri)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    toast("CLICKED")
                    var post = Post()
                    post.setPicturerUrl(encoder(bitmap))
                    post.setTags(arrayOf(tag1.text.toString(), tag2.text.toString(), tag3.text.toString()))
                    post.myDate = "20181010"
                    post.contents = editContent.text.toString()
                    addPost(post)




//                toast("CLICKED")
//                var post = Post()
//                post.setPicturerUrl(encoder(bitmap))
//                post.setTags(arrayOf(tag1.text.toString(), tag2.text.toString(), tag3.text.toString()))
//                post.myDate = "20181010"
//                post.contents = editContent.text.toString()
//
//                addPost(post)
            }
        })
    }

    fun addPost(post:Post){
        Log.d("ADDPOST", "ISCLICKED")
        val call = retrofitApi.addPost(post)
        call.enqueue(object:Callback<Post>{
            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e("ERROR: ", t.message)
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if(response.isSuccessful()){
                    toast("Post Uploaded Successfully!")
                }
            }

        })
    }

    fun encoder(image:Bitmap?): String {
        var imageex = image
        Log.d("IMAGEFILE", imageex.toString())
        var baos = ByteArrayOutputStream()
        imageex!!.compress(Bitmap.CompressFormat.PNG,10,baos)
        var b = baos.toByteArray()
        var imageEncoded = Base64.encodeToString(b,Base64.DEFAULT)
        return imageEncoded
    }


}

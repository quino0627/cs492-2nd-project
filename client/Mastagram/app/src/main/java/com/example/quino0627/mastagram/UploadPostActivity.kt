package com.example.quino0627.mastagram

import android.app.PendingIntent.getActivity
import android.app.ProgressDialog
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.util.Base64
import android.util.Log
import android.view.View
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
import android.support.design.widget.TabLayout
import android.view.View.*
import android.widget.*
import java.io.FileNotFoundException
import java.io.IOException
import com.example.quino0627.mastagram.MainActivity
import java.io.ByteArrayOutputStream


class UploadPostActivity : AppCompatActivity() {

    lateinit var retrofitApi:RetrofitApi
    lateinit var editContent:EditText
    lateinit var btnSave:Button
//    lateinit var tag1:TextView
//    lateinit var tag2:TextView
//    lateinit var tag3:TextView

    var clicked = false;
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
        editContent = findViewById<EditText>(R.id.editContent)
        //tag1 = findViewById(R.id.tag1)
        //tag2 = findViewById(R.id.tag2)
        //tag3 = findViewById(R.id.tag3)
        btnSave = findViewById(R.id.upload_btn)

        retrofitApi = APIUtils.getUserService()

        btnSave.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
        if(clicked==false) {
         clicked = true;
            var bitmap: Bitmap? = null


            try {
                Log.d("I'm IN TRY", (bitmap == null).toString())
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picture_uri)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            var post = Post()
            post.uploader_id = MainActivity.myFBUserId
            post.setPictureUrl(encoder(bitmap))
            post.setTags(arrayOf("I WILL", "IMPLEMENT", "LATER"))
            post.contents = editContent.text.toString()
            addPost(post)
        }
            }
        })
    }

    fun addPost(post:Post){
        Log.d("ADDPOST", "ISCLICKED")
        val call = retrofitApi.addPost(post)

//        final ProgressDialog progressDoalog;
//////        progressDoalog = new ProgressDialog(MainActivity.this);
//////        progressDoalog.setMax(100);
//////        progressDoalog.setMessage("Its loading....");
//////        progressDoalog.setTitle("ProgressDialog bar example");
//////        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//////        // show it
//////        progressDoalog.show();
        var progressDialog = ProgressDialog(this)
//        progressDialog.max = 100
        progressDialog.setMessage("태그를 생성하고 있어요...")
        progressDialog.setTitle("업로드 중")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //progressDialog.show()
        findViewById<ProgressBar>(R.id.progressbar).visibility= VISIBLE

        call.enqueue(object:Callback<Post>{
            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e("ERROR: ", t.message)
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if(response.isSuccessful()){
                    toast("업로드 완료!!")
                    //int position = 1;
                    //getSupportActionBar().setSelectedNavigationItem(position);
                    clicked = false;
                    findViewById<ProgressBar>(R.id.progressbar).visibility= GONE
                    //progressDialog.dismiss()
                    finish()
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

package com.example.quino0627.mastagram

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.quino0627.mastagram.Model.*
import com.facebook.AccessToken
import com.facebook.Profile
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.FileNotFoundException
import java.io.IOException

class RegisterActivity : AppCompatActivity() {

    lateinit var retrofitApi:RetrofitApi
    lateinit var registerBtn:Button
    lateinit var registerName:EditText
    lateinit var registerPhone:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var intent = intent;
        var user_id = intent.extras.getString("user_id")
        Log.e("FUCKINGUSERID", user_id)
        registerBtn = findViewById(R.id.register_btn)
        registerName = findViewById(R.id.register_name)
        registerPhone = findViewById(R.id.register_phone)
        retrofitApi = APIUtils.getUserService()

        //var accessToken = AccessToken.getCurrentAccessToken()
        //var fbuserId = accessToken.userId //이게 디비에 올라가서 유저_id 가 되고 이걸로 유저를 식별 유닉!


        var user1 = User()
        var userId1 = UserId()
        var name1 = Name()
        var phone1 = Phone()
        userId1.type = user_id
        name1.type = registerName.text.toString()
        phone1.type = registerPhone.text.toString()
        user1.userId = userId1
        user1.phone = phone1
        user1.name = name1
//        user1.userId.type = user_id.toString()
//        user1.name.type = registerName.text.toString()
//        user1.phone.type = registerPhone.text.toString()

        registerBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val call = retrofitApi.addUser(user1)
                call.enqueue(object: Callback<User> {
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.e("ERROR: ", t.message)
                    }

                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if(response.isSuccessful()){
                            toast("User Registered Successfully!")
                        }
                    }

                })

            }


        }
        )
    }

}

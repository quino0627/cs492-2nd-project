package com.example.quino0627.mastagram

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.facebook.AccessToken

class RegisterActivity : AppCompatActivity() {

    lateinit var retrofitApi: RetrofitApi
    lateinit var btnRegister:Button
    lateinit var nameRegister:EditText
    lateinit var phoneRegister:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        retrofitApi = APIUtils.getUserService()
        var accessToken = AccessToken.getCurrentAccessToken()

        btnRegister = findViewById(R.id.register_btn)
        nameRegister = findViewById(R.id.register_name)
        phoneRegister = findViewById(R.id.register_phone)
         

    }


}

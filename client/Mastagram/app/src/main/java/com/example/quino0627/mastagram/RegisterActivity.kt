package com.example.quino0627.mastagram

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
<<<<<<< HEAD
import android.widget.Button
import android.widget.EditText
import com.facebook.AccessToken

class RegisterActivity : AppCompatActivity() {

    lateinit var retrofitApi: RetrofitApi
    lateinit var btnRegister:Button
    lateinit var nameRegister:EditText
    lateinit var phoneRegister:EditText
=======
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.quino0627.mastagram.Model.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var retrofitApi:RetrofitApi
//    lateinit var registerBtn:Button
//    lateinit var registerName:EditText
//    lateinit var registerPhone:EditText

>>>>>>> help

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

<<<<<<< HEAD
        retrofitApi = APIUtils.getUserService()
        var accessToken = AccessToken.getCurrentAccessToken()

        btnRegister = findViewById(R.id.register_btn)
        nameRegister = findViewById(R.id.register_name)
        phoneRegister = findViewById(R.id.register_phone)
         

    }

=======
        var intent = intent;
        var user_id = intent.extras.getString("user_id")
        Log.e("FUCKINGUSERID", user_id)
        var registerBtn = findViewById<Button>(R.id.register_btn)
        var registerName = findViewById<EditText>(R.id.register_name)
        var registerPhone = findViewById<EditText>(R.id.register_phone)
        retrofitApi = APIUtils.getUserService()

        //var accessToken = AccessToken.getCurrentAccessToken()
        //var fbuserId = accessToken.userId //이게 디비에 올라가서 유저_id 가 되고 이걸로 유저를 식별 유닉!


        var user1 = User()
//        var userId1 = UserId()
//        var name1 = Name()
//        var phone1 = Phone()
//        userId1.type = user_id
//        name1.type = registerName.text.toString()
//        phone1.type = registerPhone.text.toString()
//        user1.userId = userId1
//        user1.phone = phone1
//        user1.name = name1


        registerBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                Log.e("registerName", registerName.text.toString())
                Log.e("registerName", registerPhone.text.toString())
                user1.userId = user_id.toString()
                user1.name = registerName.text.toString()
                user1.phone = registerPhone.text.toString()

                val call = retrofitApi.addUser(user1)
                call.enqueue(object: Callback<User> {
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.e("ERROR: ", t.message)
                    }

                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if(response.isSuccessful()){
                            toast("회원가입 완료!!")
                            Log.e("CALL", call.toString())
                            finish()
                        }
                    }

                })

            }


        }
        )
    }
>>>>>>> help

}

package com.example.quino0627.mastagram.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import com.example.quino0627.mastagram.APIUtils;
import com.example.quino0627.mastagram.Model.RegisterCheck;
import com.example.quino0627.mastagram.R;
import com.example.quino0627.mastagram.RegisterActivity;
import com.example.quino0627.mastagram.RetrofitApi;
import com.facebook.*;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private static final String EMAIL = "email";
    private static final String USER_POSTS = "user_posts";
    private static final String AUTH_TYPE = "rerequest";

    private CallbackManager mCallbackManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        mCallbackManager = CallbackManager.Factory.create();
        final RetrofitApi retrofitApi = APIUtils.getUserService();
        LoginButton mLoginButton = findViewById(R.id.btn_facebook_login);

        // Set the initial permissions to request from the user while logging in
        mLoginButton.setReadPermissions(Arrays.asList(EMAIL, USER_POSTS));

        mLoginButton.setAuthType(AUTH_TYPE);

        // Register a callback to respond to the user
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {



                setResult(RESULT_OK);
                AccessToken accessToken = AccessToken.getCurrentAccessToken();

                final String userId = accessToken.getUserId(); //이게 디비에 올라가서 유저_id 가 되고 이걸로 유저를 식별 유닉!
                Log.d("THIS IS USERID", userId.toString());

                final Call<RegisterCheck> call = retrofitApi.isRegistered(userId);
                call.enqueue(new Callback<RegisterCheck>(){
                    @Override
                    public void onResponse(Call<RegisterCheck> call, Response<RegisterCheck> response) {
                        if (response.isSuccessful()){
                            Log.d("userId is ",userId);
                            Log.d("RESULT IS?" , response.body().result.toString());
                            if (response.body().result == false){
                                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                                registerIntent.putExtra("user_id",userId);
                                startActivity(registerIntent);
                            }
                        }
                    }

                    public void onFailure(Call<RegisterCheck> call, Throwable t) {
                        Log.e("fail to get BOOLEAN", t.getMessage());
                    }

                });

                finish();


            }

            @Override
            public void onCancel() {
                setResult(RESULT_CANCELED);
                finish();
            }

            @Override
            public void onError(FacebookException e) {
                // Handle exception
            }
        });
    }
}
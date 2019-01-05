package com.example.quino0627.mastagram;

public class APIUtils {

    private APIUtils(){

    };

    public static final String API_URL = "http://143.248.140.106:1080";

    public static RetrofitApi getUserService(){
        return RetrofitClient.getClient(API_URL).create(RetrofitApi.class);
    }
}

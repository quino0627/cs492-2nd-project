package com.example.quino0627.mastagram;

import com.example.quino0627.mastagram.Model.Post;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitApi {

 @POST("api/posts/create")
    Call<Post> addPost(@Body Post post);

}

package com.example.quino0627.mastagram;

import com.example.quino0627.mastagram.Model.Post;
import com.example.quino0627.mastagram.Model.RegisterCheck;
import com.example.quino0627.mastagram.Model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface RetrofitApi {

 @POST("api/posts/create")
    Call<Post> addPost(@Body Post post);

 @GET("api/posts/all")
    Call<List<Post>> getPosts();

 @GET("api/users/login/{user_id}")
    Call<RegisterCheck> isRegistered(@Path("user_id") String user_id);

 @POST("api/users/create")
    Call<User> addUser(@Body User user);

}

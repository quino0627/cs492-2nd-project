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

 //GET , api/users/info/:user_id 에여

 @GET("api/users/info/{user_id}")
    Call<User> getUser(@Path("user_id") String user_id);

 //GET/api/users/friends_list/:user_id
    // 유저 아이디를 주고, 그 유저의 친구(User model) 리스트를 받음

 @GET("api/users/friends_list/{user_id}")
    Call<List<User>> getFriendsList(@Path("user_id") String user_id);


 //GET/api/posts/ones_timeline/:user_id
    //친구들의 포스트를 받음(특정 유저의 타임라인)
  @GET("api/posts/ones_timeline/{user_id}")
    Call<List<Post>> getTimeLine(@Path("user_id") String user_id);
}

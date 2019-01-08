package com.example.quino0627.mastagram;

import com.example.quino0627.mastagram.Model.Post;
import com.example.quino0627.mastagram.Model.RegisterCheck;
<<<<<<< HEAD
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
=======
import com.example.quino0627.mastagram.Model.User;
import retrofit2.Call;
import retrofit2.http.*;
>>>>>>> help

import java.util.List;

public interface RetrofitApi {

 @POST("api/posts/create")
    Call<Post> addPost(@Body Post post);
 @GET("api/posts/delete/{post_id}")
    Call<Post> deletePost(@Path("post_id") String post_id);

 @GET("api/posts/all")
    Call<List<Post>> getPosts();

<<<<<<< HEAD
 @GET("api/users/login/{user_fid}")
    Call<RegisterCheck> isRegistered(@Path("user_fid") String id);
=======
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

//    GET/api/users/all
//    전체 유저들의 리스트를 반환(parameter x)

 @GET("api/users/all/{user_id}")
    Call<List<User>> getAllUsers(@Path("user_id") String user_id);

 //PUT/api/user/following/:user_id
    //유저 아이디를 받아서 그 유저를 팔로우 하기
    //Request parameter에 자기 아이디, 날릴 제이슨에 팔로잉할 아이디

    @PUT("api/users/following/{user_id}")
    Call<User> followFunction(@Path("user_id") String user_id, @Body User toFollowUser);

    //GET/api/users/delete/:user_id
    //유저 아이디(페이스북아이디)를 받아 유저 삭제

    @PUT("api/users/delete_friend/{user_id}")
    Call<User> unFollowFunction(@Path("user_id") String user_id, @Body User toUnFollowUser);

    //GET/api/posts/ones_posts/:user_id
    @GET("api/posts/ones_posts/{user_id}")
    Call<List<Post>> getOnesPosts(@Path("user_id") String user_id);
>>>>>>> help
}

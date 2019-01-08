package com.example.quino0627.mastagram.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Friend extends User {

//    @SerializedName("user_id")
//    @Expose
//    public String userId;
//    @SerializedName("name")
//    @Expose
//    public String name;
//
//    @SerializedName("phone")
//    @Expose
//    public String phone;
//
//    @SerializedName("friends")
//    @Expose
//    public String[] friends;

    @SerializedName("real_friend")
    @Expose
    public boolean real_friend = false;


//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String[] getFriends() {
//        return friends;
//    }
//
//    public void setFriends(String[] friends) {
//        this.friends = friends;
//    }

    public boolean getReal_friend(){ return real_friend;}
    public void setReal_friend(boolean real_friend){ this.real_friend = real_friend;}

}

package com.example.quino0627.mastagram.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("pictureUrl")
    @Expose
    private String picturerUrl;

    @SerializedName("uploader")
    @Expose
    private String uploader = "woLoginTest";

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("contents")
    @Expose
    private String contents;

    @SerializedName("tag")
    @Expose
    private String[] tag;

    @SerializedName("__v")
    @Expose
    private Integer __z = 0;

    public String getPirctuerUrl(){
        return picturerUrl;
    }

    public void setPicturerUrl(String picturerUrl){
        this.picturerUrl = picturerUrl;
    }

    public String getUploader(){
        return uploader;
    }

    public void setUploader(String uploader){
        this.uploader = uploader;
    }

    public String getMyDate(){
        return date;
    }

    public void setMyDate(String uploader){
        this.date = date;
    }

    public String getContents(){
        return contents;
    }

    public void setContents(String contents){
        this.contents = contents;
    }

    public String getTags(){
        return contents;
    }

    public void setTags(String[] tag){
        this.tag = tag;
    }
}

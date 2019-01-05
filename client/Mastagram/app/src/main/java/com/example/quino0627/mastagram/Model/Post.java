package com.example.quino0627.mastagram.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("pictureUrl")
    @Expose
    private String pictureUrl;

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

    public String getPirctueUrl(){
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl){
        this.pictureUrl = pictureUrl;
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

    public void setMyDate(String date){
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

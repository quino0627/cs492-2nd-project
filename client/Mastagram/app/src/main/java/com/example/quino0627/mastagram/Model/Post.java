package com.example.quino0627.mastagram.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;

public class Post {

    @SerializedName("pictureUrl")
    @Expose
    private String pictureUrl;

    @SerializedName("uploader_name")
    @Expose
    private String uploader_name;

    @SerializedName("uploader_id")
    @Expose
    private String uploader_id;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("contents")
    @Expose
    private String contents;

    @SerializedName("tag")
    @Expose
    public String[] tag = {"THIS IS","NOT YET","DEFINED"};

    @SerializedName("__v")
    @Expose
    private Integer __z = 0;

    public String getPirctueUrl(){
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl){
        this.pictureUrl = pictureUrl;
    }

    public String getUploader_name(){
        return uploader_name;
    }

    public void setUploader_name(String uploader_name){
        this.uploader_name = uploader_name;
    }

    public String getUploader_id(){
        return uploader_id;
    }

    public void setUploader_id(String uploader_id){
        this.uploader_id = uploader_id;
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

    public String[] getTags(){
        return tag;
    }

    public void setTags(String[] tag){
        this.tag = tag;
    }
}

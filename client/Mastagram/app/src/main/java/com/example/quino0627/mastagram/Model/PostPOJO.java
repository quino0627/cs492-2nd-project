package com.example.quino0627.mastagram.Model;

public class PostPOJO {

    private String pictureUrl;
    private String uploader;
    private String date;
    private String contents;
    private Integer __v;
    private String[] tag;

    public PostPOJO(String pictureUrl, String uploader, String date, String contents, Integer __v, String[] tag){
        this.pictureUrl = pictureUrl;
        this.uploader = uploader;
        this.date = date;
        this.contents = contents;
        this.__v = __v;
        this.tag = tag;
    }

    public String getPirctueUrl(){
        return pictureUrl;
    }

    public void setPictureUrl(String picturerUrl){
        this.pictureUrl = picturerUrl;
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

    public Integer get__v(){
        return __v;
    }

    public void set__v(Integer __v){
        this.__v = __v;
    }
}

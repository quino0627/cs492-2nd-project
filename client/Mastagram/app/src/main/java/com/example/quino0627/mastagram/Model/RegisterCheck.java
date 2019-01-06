package com.example.quino0627.mastagram.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterCheck {
    @SerializedName("result")
    @Expose
    public Boolean result;

    public Boolean getResultUrl(){
        return result;
    }

    public void setResultUrl(Boolean result){
        this.result = result;
    }
}

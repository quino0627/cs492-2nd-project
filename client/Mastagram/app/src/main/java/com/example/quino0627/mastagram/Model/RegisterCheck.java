package com.example.quino0627.mastagram.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterCheck {
<<<<<<< HEAD

    @SerializedName("result")
    @Expose
    private Boolean result;

    public Boolean getResult(){
        return result;
    }

    public void setResult(Boolean result){
=======
    @SerializedName("result")
    @Expose
    public Boolean result;

    public Boolean getResultUrl(){
        return result;
    }

    public void setResultUrl(Boolean result){
>>>>>>> help
        this.result = result;
    }
}

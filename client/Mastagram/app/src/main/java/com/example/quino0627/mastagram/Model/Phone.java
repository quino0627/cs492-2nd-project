package com.example.quino0627.mastagram.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Phone {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("required")
    @Expose
    private Boolean required = true;
    @SerializedName("unique")
    @Expose
    private Boolean unique = true;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public String getRequired() {
//        return required;
//    }
//
//    public void setRequired(String required) {
//        this.required = required;
//    }
//
//    public String getUnique() {
//        return unique;
//    }
//
//    public void setUnique(String unique) {
//        this.unique = unique;
//    }

}
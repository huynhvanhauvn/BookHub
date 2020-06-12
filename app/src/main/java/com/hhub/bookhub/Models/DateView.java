package com.hhub.bookhub.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DateView {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("view")
    @Expose
    private Integer view;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

}

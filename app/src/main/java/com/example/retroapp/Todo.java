package com.example.retroapp;

import com.google.gson.annotations.SerializedName;

public class Todo {
    private int userId;

    private Integer id;

    private String title;

    @SerializedName("completed")
    private String status;

   /*
    public Todo(int userId, String title, String status ){
        this.userId = userId;
        this.title = title;
        this.status = status;
    }

    */

    public int getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

}

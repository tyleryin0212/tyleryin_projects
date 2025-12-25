package com.tyleryin.medialibrary.DTO;

import jakarta.validation.constraints.Min;

public class UpdateItemRequest {
    //optional: if null, don't update
    private String title;

    //optional: if null, don't update
    @Min(0)
    private Integer year;

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public Integer getYear(){
        return year;
    }

    public void setYear(Integer year){
        this.year = year;
    }
}

package com.example.e_commerce.models;

public class ShowRvModel {
    private String text ;
    private String img_url ;

    public ShowRvModel(String text, String img_url) {
        this.text = text;
        this.img_url = img_url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return img_url;
    }

    public void setImage(String img_url) {
        this.img_url = img_url;
    }
}

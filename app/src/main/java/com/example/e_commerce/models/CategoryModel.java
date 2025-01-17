package com.example.e_commerce.models;

public class CategoryModel {
    private String img_url;
    private String type;
    private String name;

    public CategoryModel(String img_url, String type, String name) {
        this.img_url = img_url;
        this.type = type;
        this.name = name;
    }

    public CategoryModel() {
    }


    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

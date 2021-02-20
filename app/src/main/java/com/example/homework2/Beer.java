package com.example.homework2;

public class Beer {

    private String name;
    private String image_url;
    private String description;
    private boolean favorite;
    private String data;

    public Beer(String name, String image_url, String description, String data){
        this.name = name;
        this.image_url = image_url;
        this.description = description;
        this.favorite = false;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

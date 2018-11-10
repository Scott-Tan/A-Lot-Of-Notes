package com.example.a_lot_of_notes.a_lot_of_notes.adapters;

public class ImageRecyclerViewItem {
    private String image_title;
    private String image_path;

    public ImageRecyclerViewItem(String image_title, String image_path){
        this.image_title = image_title;
        this.image_path = image_path;
    }

    public String getImage_title(){
        return image_title;
    }

    public String getImage_path(){
        return image_path;
    }

    public void setImage_title(String image_title){
        this.image_title = image_title;
    }

    public void setImage_path(String image_path){
        this.image_path = image_path;
    }

}

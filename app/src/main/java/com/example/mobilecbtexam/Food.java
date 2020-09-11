package com.example.mobilecbtexam;

/**
 * Created by sherif146 on 10/04/2017.
 */
public class Food {
    private int id;
    private String name;
    private String username;
    private byte[] image;


    public Food(String name, String username, byte[] image, int id){
        this.name = name;
        this.username = username;
        this.image = image;
        this.id = id;
    }

    public int getId(){
        return id;
    }
    public byte[] getImage(){
        return image;
    }
    public String getUsername(){
        return username;
    }
    public String getName(){
        return name;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setImage(byte[] image){
        this.image = image;
    }
    public void getUsername(String username){
        this.username = username;
    }
    public void setName(String name){
        this.name = name;
    }
}

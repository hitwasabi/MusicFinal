package com.bkacad.tu.musicfinal;



public class Song {
    public String name;
    public String author;
    public int img;
    public int muSic;

    public Song(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public Song() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}


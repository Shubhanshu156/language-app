package com.example.languageapp;

public class WORDS {
    private String englishword;
    private String meowkword;
    private int maudioid;
    private int resourceid=NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED=-1;
    public String getEnglishword(){
        return englishword;


    }
    public String getMeowkword(){
        return meowkword;

    }
    WORDS(String englishword,String meowkword,int audioid){
        this.englishword=englishword;
        this.meowkword=meowkword;
        this.maudioid=audioid;
    }
    WORDS(String englishword,String meowkword,int resourceid,int audioid){
        this.resourceid=resourceid;
        this.englishword=englishword;
        this.meowkword=meowkword;
        this.maudioid=audioid;
      //  this.maudioid=audioid;
    }
    public int getimageid(){
        return  resourceid;
    }
    public boolean hasimage(){
        return resourceid!=NO_IMAGE_PROVIDED;

    }

    public int getAudioid() {
        return maudioid;
    }
}

package com.learnyourself.learn;

public class LearnData {
    String name;
    String url;
    String fileName_st ;
    String ststus ;

    LearnData(String name, String url){
        this.name = name;
        this.url = url;
    }

    public LearnData(String name, String url, String ststus, String  fileName_st) {
        this.name = name;
        this.url = url;
        this.fileName_st = fileName_st;
        this.ststus = ststus;
    }

    public LearnData(String name, String url, String fileName_st) {
        this.name = name;
        this.url = url;
        this.fileName_st = fileName_st;
    }

    public String getStstus() {
        return ststus;
    }

    public void setStstus(String ststus) {
        this.ststus = ststus;
    }

    public String getFileName_st() {
        return fileName_st;
    }

    public void setFileName_st(String fileName_st) {
        this.fileName_st = fileName_st;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



}

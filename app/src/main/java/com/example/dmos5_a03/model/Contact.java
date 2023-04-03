package com.example.dmos5_a03.model;

public class Contact{
    private String nickname;
    private String name;
    private String tel;

    public Contact(String nickname, String name, String tel){
        this.nickname = nickname;
        this.name = name;
        this.tel = tel;
    }

    public String getNickname(){ return nickname; }
    public String getName(){
        return name;
    }
    public String getTel(){
        return tel;
    }
}

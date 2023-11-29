package com.example.job;

import java.util.ArrayList;

public class Position {
    private ArrayList<User> users = new ArrayList<>();
    private static int num = -1;
    private int id;
    private int totalperson;
    private String name;
    private String address;
    private Double money;
    public Position(int total,String name,String address,Double money){
        this.id = ++num;
        this.totalperson = total;
        this.name = name;
        this.address = address;
        this.money = money;
    }
    public Boolean isfav(){
        if(users.indexOf(Module.getInstance().getUser())==-1){
            return false;
        }else {
            return true;
        }
    }
    public void movefav(User user){
        users.remove(user);
    }
    public void addfav(User user){
        users.add(user);
    }
}

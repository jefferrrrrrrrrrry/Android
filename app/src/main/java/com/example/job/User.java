package com.example.job;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private static int num = -1;
    private int id;
    private String username;
    private String password;
    private static HashMap<String,User> userlist=new HashMap<>() ;
    private ArrayList<Position> favpositions = new ArrayList<>();
    static {
        userlist.put("123",new User("123","456"));
    }
    public static User getUser(String username){
        return userlist.get(username);
    }
    public User(String username, String password){
        this.id = ++num;
        this.username = username;
        this.password = password;
    }
    public String getPassword(){
        return password;
    }
    public String getUsername(){
        return username;
    }
    public static Boolean normaluser(String username){
        return !username.isEmpty();
    }
    public static Boolean noramlpassword(String password){
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";

        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(regex);

        // 创建 matcher 对象
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
    public  void destroyuser(User now){
        //数据库的接口来删除user
        userlist.remove(now);
    }
    public void update(String username,String password){
        //数据库接口来更新user
        this.username = username;
        this.password = password;
    }
    public static void register(String username,String password){
        userlist.put(username,new User(username,password));
    }
    public static void deleteAccount(User user){
        userlist.remove(user.getUsername());
        //System.out.println("删除"+user.getUsername());
        //System.out.println(userlist.size());
        //数据库操作
    }
    public void movefav(Position position){
        favpositions.remove(position);
    }
    public void addfav(Position position){
        favpositions.add(position);
    }
}

package com.example.job;

import android.net.Uri;
import android.util.Log;

import com.example.job.Job.JobItem;
import com.example.job.Reminder.ReminderManager;
import com.example.job.chat.Chat;
import com.example.job.clock.ClockItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private static int num = -1;
    private int id;
    private Uri uri;
    private String username;
    private String password;
    private static HashMap<String,User> userlist=new HashMap<>() ;
    private ArrayList<JobItem> favjobs = new ArrayList<>();
    private ReminderManager reminderManager=new ReminderManager();
    private ArrayList<ClockItem> clocks = new ArrayList<>();
    private ArrayList<Chat> chats = new ArrayList<>();
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

        chats.add(new Chat("老板1", "明天来面试", "10:05"));
        chats.add(new Chat("老板2", "v50来面试", "10:10"));
        chats.add(new Chat("老板3", "明天来面试", "10:05"));
        chats.add(new Chat("老板4", "v50来面试", "10:10"));
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
    public void movefav(JobItem jobItem){
        favjobs.remove(jobItem);
    }
    public void addfav(JobItem jobItem){
        favjobs.add(jobItem);
    }

    public ReminderManager getReminderManager() {
        return reminderManager;
    }

    public ArrayList<ClockItem> getClocks() {
        return clocks;
    }
    /*public ArrayList<Position> getFavpositions(){
        ArrayList<Position> positions = new ArrayList<>();
        for(int i=0;i<=positions.size()-1;i++){
            positions.add(favpositions.get(i));
        }
        return positions;
    }*/

    public ArrayList<Chat> getChats() {
        return chats;
    }
    public Uri geturi(){
        return uri;
    }
    public void seturi(Uri uri){
        this.uri = uri;
    }
    public ArrayList<JobItem> getFavjobs(){
        return favjobs;
    }
}

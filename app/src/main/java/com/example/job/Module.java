package com.example.job;

import android.util.Log;

import com.example.job.Job.JobItem;

import java.util.Objects;

public class Module {
    // 在类内部创建一个Singleton类型的私有静态实例
    private static Module instance = null;
    private User current_user = null;
    // 将构造方法定义为私有，这样外部就不能通过new来创建此类的实例
    private Module() {
    }

    // 提供一个公共的静态方法，用于获取这个类的唯一实例
    public static Module getInstance() {
        if (instance == null) {
            instance = new Module();
        }
        return instance;
    }
    public User getUser(){
        return current_user;
    }
    public void setUser(User user){
        current_user = user;
    }
    public void logout(){
        current_user = null;
    }
    public void deleteAccount(){
        User.deleteAccount(current_user);
        logout();
    }
    public void setCurrent_user(User user){
        current_user = user;
        Log.v("LoginActivity","user");
        System.out.println(current_user.getUsername());
    }
    public void transmitfav(JobItem jobItem){
        if(jobItem.isfav()){
            jobItem.movefav(current_user);
            current_user.movefav(jobItem);
        }else {
            jobItem.addfav(current_user);
            current_user.addfav(jobItem);
        }
    }
}
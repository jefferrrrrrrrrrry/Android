package com.example.job.Job;

import com.example.job.Module;
import com.example.job.User;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class JobItem {
    private String jobname;
    private String address;
    private String hrname;
    private String salary;
    private String link;
    private String origin;
    private boolean favor;
    private boolean isFull;
    private ArrayList<User> users = new ArrayList<>();

    public JobItem(String jobname, String address, String hrname, String salary, String link, String origin) {
        this.jobname = jobname;
        this.address = address;
        this.hrname = hrname;
        this.salary = salary;
        this.link = link;
        this.origin = origin;
        this.favor = false;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHrname() {
        return hrname;
    }

    public void setHrname(String hrname) {
        this.hrname = hrname;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public boolean isFavor() {
        return isfav();
    }

    public void setFavor(boolean favor) {
        this.favor = favor;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof JobItem)) {
            return false;
        }
        JobItem job = (JobItem) obj;
        return jobname == job.jobname &&
                address == job.address &&
                hrname == job.hrname &&
                salary == job.salary &&
                link == job.link;
    }
}

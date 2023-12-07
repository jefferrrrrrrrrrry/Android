package com.example.job.Job;

import java.util.ArrayList;

public class JobsAll {
    private static ArrayList<JobItem> all = new ArrayList<>();
    static {
        all.add(new JobItem("Smoking", "Li Tang", "Ding Zhen", "5000/month", "www.baidu.com"));
    }
    public static ArrayList<JobItem> getAll(){
        return all;
    }
}

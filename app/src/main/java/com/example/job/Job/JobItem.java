package com.example.job.Job;

public class JobItem {
    private String jobname;
    private String address;
    private String hrname;
    private String salary;
    private String link;

    private boolean favor;

    public JobItem(String jobname, String address, String hrname, String salary, String link) {
        this.jobname = jobname;
        this.address = address;
        this.hrname = hrname;
        this.salary = salary;
        this.link = link;
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
        return favor;
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
}

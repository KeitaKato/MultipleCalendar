package com.example.eiga_.readingcalendar.utils;

public class Plan {
    private String title;
    private String startTime;
    private String endTime;
    private String useTime;
    private String type;
    private String income;
    private String spending;
    private String memo;

    public Plan(String title,String startTime,String endTime,String useTime,String type,String income,String spending,String memo) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.useTime = useTime;
        this.type = type;
        this.income = income;
        this.spending = spending;
        this.memo = memo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getSpending() {
        return spending;
    }

    public void setSpending(String spending) {
        this.spending = spending;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}

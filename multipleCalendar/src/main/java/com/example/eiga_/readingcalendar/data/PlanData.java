package com.example.eiga_.readingcalendar.data;

import java.io.Serializable;

public class PlanData implements Serializable{
    public static final String SERIAL_NAME = "PlanData";
    private int id;
    private String title;
    private String startTime;
    private String endTime;
    private int useTime;
    private int notice;
    private int type;
    private String review;
    private String income;
    private String spending;
    private String place;
    private String tool;
    private boolean endCheck;
    private String memo;
    private String presetId;
    private String readingId;

    public PlanData() {
    }

    public PlanData(int id, String title, String startTime, String endTime, int useTime, int notice, int type,
                    String review, String income, String spending, String place, String tool, boolean endCheck, String memo) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.useTime = useTime;
        this.notice = notice;
        this.type = type;
        this.review = review;
        this.income = income;
        this.spending = spending;
        this.place = place;
        this.tool = tool;
        this.endCheck = endCheck;
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

    public int getUseTime() {
        return useTime;
    }

    public void setUseTime(int useTime) {
        this.useTime = useTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPresetId() {
        return presetId;
    }

    public void setPresetId(String presetId) {
        this.presetId = presetId;
    }

    public String getReadingId() {
        return readingId;
    }

    public void setReadingId(String readingId) {
        this.readingId = readingId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTool() {
        return tool;
    }

    public void setTool(String tool) {
        this.tool = tool;
    }

    public boolean isEndCheck() {
        return endCheck;
    }

    public void setEndCheck(boolean endCheck) {
        this.endCheck = endCheck;
    }

    public int getNotice() {
        return notice;
    }

    public void setNotice(int notice) {
        this.notice = notice;
    }
}

package com.example.eiga_.readingcalendar.data;

import java.io.Serializable;

public class PlanTypeData implements Serializable{
    public static final String SERIAL_NAME = "PlanTypeData";

    private int id;
    private String typeName;
    private boolean reviewFlag;
    private boolean incomeFlag;
    private boolean spendingFlag;
    private boolean placeFlag;
    private boolean toolFlag;
    private boolean checkFlag;

    public PlanTypeData(){

    }

    public PlanTypeData(int id, String typeName, boolean reviewFlag, boolean incomeFlag,
                        boolean spendingFlag, boolean placeFlag, boolean toolFlag, boolean checkFlag) {
        this.id = id;
        this.typeName = typeName;
        this.reviewFlag = reviewFlag;
        this.incomeFlag = incomeFlag;
        this.spendingFlag = spendingFlag;
        this.placeFlag = placeFlag;
        this.toolFlag = toolFlag;
        this.checkFlag = checkFlag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isReviewFlag() {
        return reviewFlag;
    }

    public void setReviewFlag(boolean reviewFlag) {
        this.reviewFlag = reviewFlag;
    }

    public boolean isIncomeFlag() {
        return incomeFlag;
    }

    public void setIncomeFlag(boolean incomeFlag) {
        this.incomeFlag = incomeFlag;
    }

    public boolean isSpendingFlag() {
        return spendingFlag;
    }

    public void setSpendingFlag(boolean spendingFlag) {
        this.spendingFlag = spendingFlag;
    }

    public boolean isPlaceFlag() {
        return placeFlag;
    }

    public void setPlaceFlag(boolean placeFlag) {
        this.placeFlag = placeFlag;
    }

    public boolean isToolFlag() {
        return toolFlag;
    }

    public void setToolFlag(boolean toolFlag) {
        this.toolFlag = toolFlag;
    }

    public boolean isCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }
}

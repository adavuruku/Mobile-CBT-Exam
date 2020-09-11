package com.example.mobilecbtexam;

/**
 * Created by sherif146 on 16/04/2017.
 */
public class cbtExam {
    private int id;
    private String date_exam, CbtScore, CbtPercentage, CbtTime;

    public cbtExam(String date_exam, String CbtScore,String CbtPercentage,String CbtTime, int id){
        this.date_exam = date_exam;
        this.CbtScore = CbtScore;
        this.CbtPercentage = CbtPercentage;
        this.CbtTime = CbtTime;
        this.id = id;
    }

    public String getDate_exam(){
        return date_exam;
    }
    public String getCbtScore(){
        return CbtScore;
    }
    public String getCbtPercentage(){
        return CbtPercentage;
    }
    public String getCbtTime(){
        return CbtTime;
    }
    public int getId(){
        return id;
    }

    public void setCbtPercentage(String cbtPercentage) {
        this.CbtPercentage = cbtPercentage;
    }

    public void setCbtScore(String cbtScore) {
        this.CbtScore = cbtScore;
    }

    public void setCbtTime(String cbtTime) {
        this.CbtTime = cbtTime;
    }

    public void setDate_exam(String date_exam) {
        this.date_exam = date_exam;
    }

    public void setId(int id) {
        this.id = id;
    }
}

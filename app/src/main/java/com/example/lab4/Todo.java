package com.example.lab4;

public class Todo {


    private String txt;
    private boolean isUrgent;
    public Todo (String txt, boolean isUrgent) {
        this.txt = txt;
        this.isUrgent = isUrgent;
    }
    public void setTxt (String txt) {
        this.txt = txt;
    }
    public String getTxt () {
        return this.txt;
    }
    public void setIsUrgent (boolean isUrgent) {
        this.isUrgent = isUrgent;
    }
    public boolean getIsUrgent () {
        return this.isUrgent;
    }
}

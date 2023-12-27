package com.ashysystem.mbhq.model;

public class HappinessQuestionModel {
    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String  Question="";
    private int id=0,val=0;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private boolean selected=false;

    public void setValue(int i) {
        this.val=i;
    }
    public int getValue() {
        return val;
    }
}

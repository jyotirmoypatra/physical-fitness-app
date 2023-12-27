package com.ashysystem.mbhq.model;

public class ContentItemHappinessQuestion extends HappinessModel {

    private  Category category;
    private HappinessQuestionModel happinessQuestionModel;
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public HappinessQuestionModel getHappinessQuestionModel() {
        return happinessQuestionModel;
    }

    public void setHappinessQuestionModel(HappinessQuestionModel happinessQuestionModel) {
        this.happinessQuestionModel = happinessQuestionModel;
    }


}

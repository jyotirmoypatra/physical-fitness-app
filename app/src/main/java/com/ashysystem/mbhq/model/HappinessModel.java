package com.ashysystem.mbhq.model;


public class HappinessModel {
    private Category category;
    private HappinessQuestionModel happinessQuestionModel;




    public HappinessModel(Category category, HappinessQuestionModel happinessQuestionModel) {
        this.category = category;
        this.happinessQuestionModel = happinessQuestionModel;
        //Log.e("const",category.getId()+"?");
    }

    public HappinessModel() {

    }

    public Category getCategory() {
        return category;
    }

    public HappinessQuestionModel getTodoModel() {
        return happinessQuestionModel;
    }

}

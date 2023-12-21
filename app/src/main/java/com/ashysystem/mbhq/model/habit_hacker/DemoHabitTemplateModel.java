package com.ashysystem.mbhq.model.habit_hacker;

public class DemoHabitTemplateModel {

    private GetHabitTemplatesResponseModel.TemplateDetail habitTemplate;
    private String section;
    private Boolean showHeader = false;

    public GetHabitTemplatesResponseModel.TemplateDetail getHabitTemplate() {
        return habitTemplate;
    }

    public DemoHabitTemplateModel setHabitTemplate(GetHabitTemplatesResponseModel.TemplateDetail habitTemplate) {
        this.habitTemplate = habitTemplate;
        return this;
    }

    public String getSection() {
        return section;
    }

    public DemoHabitTemplateModel setSection(String section) {
        this.section = section;
        return this;
    }

    public Boolean getShowHeader() {
        return showHeader;
    }

    public DemoHabitTemplateModel setShowHeader(Boolean showHeader) {
        this.showHeader = showHeader;
        return this;
    }

}

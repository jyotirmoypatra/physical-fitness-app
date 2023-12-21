package com.ashysystem.mbhq.model;

public class GuidedMeditationModel {

    private MeditationCourseModel.Webinar webinar;

    private boolean isSelected;

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public MeditationCourseModel.Webinar getWebinar() {
        return webinar;
    }

    public void setWebinar(MeditationCourseModel.Webinar webinar) {
        this.webinar = webinar;
    }
}

package com.ashysystem.mbhq.model;

public class TempModelHappinessSave {
    private int UserId=0;
    private int Value=0;
    private int QuestionnaireAttemptId=0;

    private Integer QuestionnaireHappinessMasterId=0;
    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }

    public int getQuestionnaireAttemptId() {
        return QuestionnaireAttemptId;
    }

    public void setQuestionnaireAttemptId(int questionnaireAttemptId) {
        QuestionnaireAttemptId = questionnaireAttemptId;
    }
    public Integer getQuestionnaireHappinessMasterId() {
        return QuestionnaireHappinessMasterId;
    }

    public void setQuestionnaireHappinessMasterId(Integer questionnaireHappinessMasterId) {
        QuestionnaireHappinessMasterId = questionnaireHappinessMasterId;
    }

}

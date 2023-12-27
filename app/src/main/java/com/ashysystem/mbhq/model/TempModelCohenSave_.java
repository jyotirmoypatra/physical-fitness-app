package com.ashysystem.mbhq.model;

import java.util.List;

public class TempModelCohenSave_ {
    private int QuestionMasterId;
    private String Question;
    private int QuestionType;
    private int Answer;
    private boolean IsActive;
    private boolean IsDeleted;
    private List<Option1> OptionList;

    public int getQuestionMasterId() {
        return QuestionMasterId;
    }

    public void setQuestionMasterId(int questionMasterId) {
        QuestionMasterId = questionMasterId;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public int getQuestionType() {
        return QuestionType;
    }

    public void setQuestionType(int questionType) {
        QuestionType = questionType;
    }

    public int getAnswer() {
        return Answer;
    }

    public void setAnswer(int answer) {
        Answer = answer;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public List<Option1> getOptionList() {
        return OptionList;
    }

    public void setOptionList(List<Option1> optionList) {
        OptionList = optionList;
    }

   public static class Option1 {
        private String DASOptionLabel;
        private int DASOptionValue;
        private String DASOptionLabelHtml;

        public String getDASOptionLabel() {
            return DASOptionLabel;
        }

        public void setDASOptionLabel(String DASOptionLabel) {
            this.DASOptionLabel = DASOptionLabel;
        }

        public int getDASOptionValue() {
            return DASOptionValue;
        }

        public void setDASOptionValue(int DASOptionValue) {
            this.DASOptionValue = DASOptionValue;
        }

        public String getDASOptionLabelHtml() {
            return DASOptionLabelHtml;
        }

        public void setDASOptionLabelHtml(String DASOptionLabelHtml) {
            this.DASOptionLabelHtml = DASOptionLabelHtml;
        }
    }
}

// Getters and setters

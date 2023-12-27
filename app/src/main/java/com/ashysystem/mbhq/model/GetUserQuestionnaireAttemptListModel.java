package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUserQuestionnaireAttemptListModel {
    @SerializedName("Details")
    @Expose
    private List<Detail> details = null;
    @SerializedName("SuccessFlag")
    @Expose
    private Boolean successFlag;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public Boolean getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(Boolean successFlag) {
        this.successFlag = successFlag;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    ////////////////////
    public class Detail {

        @SerializedName("QuestionnaireAttemptId")
        @Expose
        private Integer questionnaireAttemptId;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("QuestionnaireTypeId")
        @Expose
        private Integer questionnaireTypeId;
        @SerializedName("QuestionnaireType")
        @Expose
        private String questionnaireType;
        @SerializedName("QuestionnaireStatusID")
        @Expose
        private Integer questionnaireStatusID;
        @SerializedName("QuestionnaireStatus")
        @Expose
        private String questionnaireStatus;
        @SerializedName("Total")
        @Expose
        private Integer total;

        @SerializedName("DAS21Anxiety")
        @Expose
        private Integer DAS21Anxiety;
        @SerializedName("DAS21Stress")
        @Expose
        private Integer DAS21Stress;
        @SerializedName("DAS21Depression")
        @Expose
        private Integer DAS21Depression;

        @SerializedName("Result")
        @Expose
        private Object result;
        @SerializedName("CreatedAt")
        @Expose
        private String createdAt;
        @SerializedName("UpdatedAt")
        @Expose
        private Object updatedAt;
        @SerializedName("IsActive")
        @Expose
        private Boolean isActive;
        @SerializedName("IsDeleted")
        @Expose
        private Boolean isDeleted;
        @SerializedName("RahePerceivedStressAnswerList")
        @Expose
        private List<RahePerceivedStressAnswerList> rahePerceivedStressAnswerList = null;
        @SerializedName("CohenStressScaleAnswerList")
        @Expose
        private List<CohenStressScaleAnswerList> cohenStressScaleAnswerList = null;///here
        @SerializedName("QuestionnaireHappinessAnswerList")
        @Expose
        private List<QuestionnaireHappinessAnswerList> questionnaireHappinessAnswerList = null;

        @SerializedName("DAS21AnswerList")
        @Expose
        private List<DasStressScaleAnswerList> dasStressScaleAnswerLists = null;



        public Integer getQuestionnaireAttemptId() {
            return questionnaireAttemptId;
        }

        public void setQuestionnaireAttemptId(Integer questionnaireAttemptId) {
            this.questionnaireAttemptId = questionnaireAttemptId;
        }

        public Integer getDAS21Anxiety() {
            return DAS21Anxiety;
        }

        public void setDAS21Anxiety(Integer DAS21Anxiety) {
            this.DAS21Anxiety = DAS21Anxiety;
        }

        public Integer getDAS21Stress() {
            return DAS21Stress;
        }

        public void setDAS21Stress(Integer DAS21Stress) {
            this.DAS21Stress = DAS21Stress;
        }

        public Integer getDAS21Depression() {
            return DAS21Depression;
        }

        public void setDAS21Depression(Integer DAS21Depression) {
            this.DAS21Depression = DAS21Depression;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getQuestionnaireTypeId() {
            return questionnaireTypeId;
        }

        public void setQuestionnaireTypeId(Integer questionnaireTypeId) {
            this.questionnaireTypeId = questionnaireTypeId;
        }

        public String getQuestionnaireType() {
            return questionnaireType;
        }

        public void setQuestionnaireType(String questionnaireType) {
            this.questionnaireType = questionnaireType;
        }

        public Integer getQuestionnaireStatusID() {
            return questionnaireStatusID;
        }

        public void setQuestionnaireStatusID(Integer questionnaireStatusID) {
            this.questionnaireStatusID = questionnaireStatusID;
        }

        public String getQuestionnaireStatus() {
            return questionnaireStatus;
        }

        public void setQuestionnaireStatus(String questionnaireStatus) {
            this.questionnaireStatus = questionnaireStatus;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public Boolean getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
        }

        public List<RahePerceivedStressAnswerList> getRahePerceivedStressAnswerList() {
            return rahePerceivedStressAnswerList;
        }

        public void setRahePerceivedStressAnswerList(List<RahePerceivedStressAnswerList> rahePerceivedStressAnswerList) {
            this.rahePerceivedStressAnswerList = rahePerceivedStressAnswerList;
        }

        public List<CohenStressScaleAnswerList> getCohenStressScaleAnswerList() {
            return cohenStressScaleAnswerList;
        }

        public void setCohenStressScaleAnswerList(List<CohenStressScaleAnswerList> cohenStressScaleAnswerList) {
            this.cohenStressScaleAnswerList = cohenStressScaleAnswerList;
        }

        public List<QuestionnaireHappinessAnswerList> getQuestionnaireHappinessAnswerList() {
            return questionnaireHappinessAnswerList;
        }

        public void setQuestionnaireHappinessAnswerList(List<QuestionnaireHappinessAnswerList> questionnaireHappinessAnswerList) {
            this.questionnaireHappinessAnswerList = questionnaireHappinessAnswerList;
        }

        public List<DasStressScaleAnswerList> getDasStressScaleAnswerLists() {
            return dasStressScaleAnswerLists;
        }

        public void setDasStressScaleAnswerLists(List<DasStressScaleAnswerList> dasStressScaleAnswerLists) {
            this.dasStressScaleAnswerLists = dasStressScaleAnswerLists;
        }
    }

    public class CohenStressScaleAnswerList {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("QuestionnaireCohenStressScaleMasterId")
        @Expose
        private Integer questionnaireCohenStressScaleMasterId;
        @SerializedName("Question")
        @Expose
        private String question;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("QuestionnaireAttemptId")
        @Expose
        private Integer questionnaireAttemptId;
        @SerializedName("Answer")
        @Expose
        private Object answer;
        @SerializedName("Value")
        @Expose
        private Integer value;
        @SerializedName("CreatedAt")
        @Expose
        private String createdAt;
        @SerializedName("UpdatedAt")
        @Expose
        private Object updatedAt;
        @SerializedName("AnswerOptionModel")
        @Expose
        private Object answerOptionModel;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getQuestionnaireCohenStressScaleMasterId() {
            return questionnaireCohenStressScaleMasterId;
        }

        public void setQuestionnaireCohenStressScaleMasterId(Integer questionnaireCohenStressScaleMasterId) {
            this.questionnaireCohenStressScaleMasterId = questionnaireCohenStressScaleMasterId;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getQuestionnaireAttemptId() {
            return questionnaireAttemptId;
        }

        public void setQuestionnaireAttemptId(Integer questionnaireAttemptId) {
            this.questionnaireAttemptId = questionnaireAttemptId;
        }

        public Object getAnswer() {
            return answer;
        }

        public void setAnswer(Object answer) {
            this.answer = answer;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getAnswerOptionModel() {
            return answerOptionModel;
        }

        public void setAnswerOptionModel(Object answerOptionModel) {
            this.answerOptionModel = answerOptionModel;
        }

    }




    //////////////
    public class DasStressScaleAnswerList {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("QuestionnaireDAS21MasterId")
        @Expose
        private Integer questionnairedas21MasterId;
        @SerializedName("Question")
        @Expose
        private String question;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("QuestionnaireAttemptId")
        @Expose
        private Integer questionnaireAttemptId;
        @SerializedName("Answer")
        @Expose
        private Integer answer;
        @SerializedName("Value")
        @Expose
        private Integer value;
        @SerializedName("CreatedAt")
        @Expose
        private String createdAt;
//        @SerializedName("UpdatedAt")
//        @Expose
//        private Object updatedAt;


        public Integer getQuestionnairedas21MasterId() {
            return questionnairedas21MasterId;
        }

        public void setQuestionnairedas21MasterId(Integer questionnairedas21MasterId) {
            this.questionnairedas21MasterId = questionnairedas21MasterId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }



        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getQuestionnaireAttemptId() {
            return questionnaireAttemptId;
        }

        public void setQuestionnaireAttemptId(Integer questionnaireAttemptId) {
            this.questionnaireAttemptId = questionnaireAttemptId;
        }

        public Integer getAnswer() {
            return answer;
        }

        public void setAnswer(Integer answer) {
            this.answer = answer;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }



    }
    public class RahePerceivedStressAnswerList {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("QuestionnaireRahePerceivedStressMasterId")
        @Expose
        private Integer questionnaireRahePerceivedStressMasterId;
        @SerializedName("Question")
        @Expose
        private String question;
        @SerializedName("QuestionValue")
        @Expose
        private Integer questionValue;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("QuestionnaireAttemptId")
        @Expose
        private Integer questionnaireAttemptId;
        @SerializedName("Answer")
        @Expose
        private Object answer;
        @SerializedName("Value")
        @Expose
        private Integer value;
        @SerializedName("CreatedAt")
        @Expose
        private String createdAt;
        @SerializedName("UpdatedAt")
        @Expose
        private Object updatedAt;
        @SerializedName("QuestionValueAnswerTotal")
        @Expose
        private Integer questionValueAnswerTotal;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getQuestionnaireRahePerceivedStressMasterId() {
            return questionnaireRahePerceivedStressMasterId;
        }

        public void setQuestionnaireRahePerceivedStressMasterId(Integer questionnaireRahePerceivedStressMasterId) {
            this.questionnaireRahePerceivedStressMasterId = questionnaireRahePerceivedStressMasterId;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public Integer getQuestionValue() {
            return questionValue;
        }

        public void setQuestionValue(Integer questionValue) {
            this.questionValue = questionValue;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getQuestionnaireAttemptId() {
            return questionnaireAttemptId;
        }

        public void setQuestionnaireAttemptId(Integer questionnaireAttemptId) {
            this.questionnaireAttemptId = questionnaireAttemptId;
        }

        public Object getAnswer() {
            return answer;
        }

        public void setAnswer(Object answer) {
            this.answer = answer;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getQuestionValueAnswerTotal() {
            return questionValueAnswerTotal;
        }

        public void setQuestionValueAnswerTotal(Integer questionValueAnswerTotal) {
            this.questionValueAnswerTotal = questionValueAnswerTotal;
        }

    }
    public class QuestionnaireHappinessAnswerList {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("QuestionnaireHappinessMasterId")
        @Expose
        private Integer questionnaireHappinessMasterId;
        @SerializedName("QuestionOptionA")
        @Expose
        private String questionOptionA;
        @SerializedName("QuestionOptionB")
        @Expose
        private String questionOptionB;
        @SerializedName("QuestionOptionC")
        @Expose
        private String questionOptionC;
        @SerializedName("QuestionOptionD")
        @Expose
        private String questionOptionD;
        @SerializedName("QuestionOptionE")
        @Expose
        private String questionOptionE;
        @SerializedName("QuestionValueA")
        @Expose
        private Integer questionValueA;
        @SerializedName("QuestionValueB")
        @Expose
        private Integer questionValueB;
        @SerializedName("QuestionValueC")
        @Expose
        private Integer questionValueC;
        @SerializedName("QuestionValueD")
        @Expose
        private Integer questionValueD;
        @SerializedName("QuestionValueE")
        @Expose
        private Integer questionValueE;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("QuestionnaireAttemptId")
        @Expose
        private Integer questionnaireAttemptId;
        @SerializedName("Answer")
        @Expose
        private Object answer;
        @SerializedName("Value")
        @Expose
        private Integer value;
        @SerializedName("CreatedAt")
        @Expose
        private String createdAt;
        @SerializedName("UpdatedAt")
        @Expose
        private Object updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getQuestionnaireHappinessMasterId() {
            return questionnaireHappinessMasterId;
        }

        public void setQuestionnaireHappinessMasterId(Integer questionnaireHappinessMasterId) {
            this.questionnaireHappinessMasterId = questionnaireHappinessMasterId;
        }

        public String getQuestionOptionA() {
            return questionOptionA;
        }

        public void setQuestionOptionA(String questionOptionA) {
            this.questionOptionA = questionOptionA;
        }

        public String getQuestionOptionB() {
            return questionOptionB;
        }

        public void setQuestionOptionB(String questionOptionB) {
            this.questionOptionB = questionOptionB;
        }

        public String getQuestionOptionC() {
            return questionOptionC;
        }

        public void setQuestionOptionC(String questionOptionC) {
            this.questionOptionC = questionOptionC;
        }

        public String getQuestionOptionD() {
            return questionOptionD;
        }

        public void setQuestionOptionD(String questionOptionD) {
            this.questionOptionD = questionOptionD;
        }

        public String getQuestionOptionE() {
            return questionOptionE;
        }

        public void setQuestionOptionE(String questionOptionE) {
            this.questionOptionE = questionOptionE;
        }

        public Integer getQuestionValueA() {
            return questionValueA;
        }

        public void setQuestionValueA(Integer questionValueA) {
            this.questionValueA = questionValueA;
        }

        public Integer getQuestionValueB() {
            return questionValueB;
        }

        public void setQuestionValueB(Integer questionValueB) {
            this.questionValueB = questionValueB;
        }

        public Integer getQuestionValueC() {
            return questionValueC;
        }

        public void setQuestionValueC(Integer questionValueC) {
            this.questionValueC = questionValueC;
        }

        public Integer getQuestionValueD() {
            return questionValueD;
        }

        public void setQuestionValueD(Integer questionValueD) {
            this.questionValueD = questionValueD;
        }

        public Integer getQuestionValueE() {
            return questionValueE;
        }

        public void setQuestionValueE(Integer questionValueE) {
            this.questionValueE = questionValueE;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getQuestionnaireAttemptId() {
            return questionnaireAttemptId;
        }

        public void setQuestionnaireAttemptId(Integer questionnaireAttemptId) {
            this.questionnaireAttemptId = questionnaireAttemptId;
        }

        public Object getAnswer() {
            return answer;
        }

        public void setAnswer(Object answer) {
            this.answer = answer;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}

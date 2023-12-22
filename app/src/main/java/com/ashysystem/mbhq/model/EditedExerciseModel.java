package com.ashysystem.mbhq.model;

/**
 * Created by android-arindam on 19/1/17.
 */

public class EditedExerciseModel {
    private Integer Id;
    private String RestComment="";
    private String SetCount="";
    private Integer SequenceNo;
    private String RepGoal="";
    private Boolean OneFromGroup;
    private Integer IsSuperSet;
    private Integer RepUnit;
    private Integer RestUnit;
    private String CircuitExerciseTip="";
    private Integer NewExerciseId=0;
    private transient Boolean setSelection=false;

    public Boolean getSetSelection() {
        return setSelection;
    }

    public void setSetSelection(Boolean setSelection) {
        this.setSelection = setSelection;
    }





    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getRestComment() {
        return RestComment;
    }

    public void setRestComment(String restComment) {
        RestComment = restComment;
    }

    public String getSetCount() {
        return SetCount;
    }

    public void setSetCount(String setCount) {
        SetCount = setCount;
    }

    public Integer getSequenceNo() {
        return SequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        SequenceNo = sequenceNo;
    }

    public String getRepGoal() {
        return RepGoal;
    }

    public void setRepGoal(String repGoal) {
        RepGoal = repGoal;
    }

    public Boolean getOneFromGroup() {
        return OneFromGroup;
    }

    public void setOneFromGroup(Boolean oneFromGroup) {
        OneFromGroup = oneFromGroup;
    }

    public Integer getIsSuperSet() {
        return IsSuperSet;
    }

    public void setIsSuperSet(Integer isSuperSet) {
        IsSuperSet = isSuperSet;
    }

    public Integer getRepUnit() {
        return RepUnit;
    }

    public void setRepUnit(Integer repUnit) {
        RepUnit = repUnit;
    }

    public Integer getRestUnit() {
        return RestUnit;
    }

    public void setRestUnit(Integer restUnit) {
        RestUnit = restUnit;
    }

    public String getCircuitExerciseTip() {
        return CircuitExerciseTip;
    }

    public void setCircuitExerciseTip(String circuitExerciseTip) {
        CircuitExerciseTip = circuitExerciseTip;
    }

    public Integer getNewExerciseId() {
        return NewExerciseId;
    }

    public void setNewExerciseId(Integer newExerciseId) {
        NewExerciseId = newExerciseId;
    }
}

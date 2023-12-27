package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by android-arindam on 19/1/17.
 */




public class CircuitListModel {

    @SerializedName("Success")
    @Expose
    private Boolean success;
    @SerializedName("ErrorMessage")
    @Expose
    private Object errorMessage;
    @SerializedName("obj")
    @Expose
    private List<ObjCircuit> obj = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<ObjCircuit> getObj() {
        return obj;
    }

    public void setObj(List<ObjCircuit> obj) {
        this.obj = obj;
    }
   public class ObjCircuit {

        @SerializedName("CircuitId")
        @Expose
        private Integer circuitId;
        @SerializedName("CircuitName")
        @Expose
        private String circuitName;

        public Integer getCircuitId() {
            return circuitId;
        }

        public void setCircuitId(Integer circuitId) {
            this.circuitId = circuitId;
        }

        public String getCircuitName() {
            return circuitName;
        }

        public void setCircuitName(String circuitName) {
            this.circuitName = circuitName;
        }

}




}
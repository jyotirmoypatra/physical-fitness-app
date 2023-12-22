package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by android-arindam on 13/3/18.
 */


    public class TargetExerciseModel {

        @SerializedName("Success")
        @Expose
        private Boolean success;
        @SerializedName("ErrorMessage")
        @Expose
        private Object errorMessage;
        @SerializedName("obj")
        @Expose
        private List<Obj> obj = null;

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

        public List<Obj> getObj() {
            return obj;
        }

        public void setObj(List<Obj> obj) {
            this.obj = obj;
        }
        /////////////////////
        public class Obj {

            @SerializedName("ExerciseId")
            @Expose
            private Integer exerciseId;
            @SerializedName("ExerciseName")
            @Expose
            private String exerciseName;
            @SerializedName("FullBody")
            @Expose
            private Boolean fullBody;
            @SerializedName("UpperBody")
            @Expose
            private Boolean upperBody;
            @SerializedName("LowerBody")
            @Expose
            private Boolean lowerBody;
            @SerializedName("Core")
            @Expose
            private Boolean core;

            public Integer getExerciseId() {
                return exerciseId;
            }

            public void setExerciseId(Integer exerciseId) {
                this.exerciseId = exerciseId;
            }

            public String getExerciseName() {
                return exerciseName;
            }

            public void setExerciseName(String exerciseName) {
                this.exerciseName = exerciseName;
            }

            public Boolean getFullBody() {
                return fullBody;
            }

            public void setFullBody(Boolean fullBody) {
                this.fullBody = fullBody;
            }

            public Boolean getUpperBody() {
                return upperBody;
            }

            public void setUpperBody(Boolean upperBody) {
                this.upperBody = upperBody;
            }

            public Boolean getLowerBody() {
                return lowerBody;
            }

            public void setLowerBody(Boolean lowerBody) {
                this.lowerBody = lowerBody;
            }

            public Boolean getCore() {
                return core;
            }

            public void setCore(Boolean core) {
                this.core = core;
            }

        }

    }


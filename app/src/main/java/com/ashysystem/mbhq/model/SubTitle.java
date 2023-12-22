package com.ashysystem.mbhq.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android-arindam on 5/12/17.
 */

public class SubTitle implements Parcelable {
    List<SessionOverViewModel.AltBodyWeightExercise> altBodyWeightExercise;
    List<SessionOverViewModel.SubstituteExercise> substituteExercise;
    String name="";
    String imagePath="";
    String equipment="";


    String videoUrl="";
    Integer id=0;

    protected SubTitle(Parcel in) {
        name = in.readString();
        imagePath = in.readString();
        equipment = in.readString();
        videoUrl = in.readString();
        altBodyWeightExercise=new ArrayList<>();
        in.readList(altBodyWeightExercise,SessionOverViewModel.AltBodyWeightExercise.class.getClassLoader());
        substituteExercise=new ArrayList<>();
        in.readList(substituteExercise,SessionOverViewModel.SubstituteExercise.class.getClassLoader());
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
    }

    public static final Creator<SubTitle> CREATOR = new Creator<SubTitle>() {
        @Override
        public SubTitle createFromParcel(Parcel in) {
            return new SubTitle(in);
        }

        @Override
        public SubTitle[] newArray(int size) {
            return new SubTitle[size];
        }
    };

    public List<SessionOverViewModel.AltBodyWeightExercise> getAltBodyWeightExercise() {
        return altBodyWeightExercise;
    }

    public void setAltBodyWeightExercise(List<SessionOverViewModel.AltBodyWeightExercise> altBodyWeightExercise) {
        this.altBodyWeightExercise = altBodyWeightExercise;
    }

    public List<SessionOverViewModel.SubstituteExercise> getSubstituteExercise() {
        return substituteExercise;
    }

    public void setSubstituteExercise(List<SessionOverViewModel.SubstituteExercise> substituteExercise) {
        this.substituteExercise = substituteExercise;
    }







    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }



    public Integer getId() {
        return id;
    }







    public String getName() {
        return name;
    }

    public SubTitle(String s, Integer id, String imagePath, String video, String equipment, List<SessionOverViewModel.AltBodyWeightExercise> alt, List<SessionOverViewModel.SubstituteExercise> sub) {
        this.name=s;
        this.id=id;
        this.imagePath=imagePath;
        this.videoUrl=video;
        this.equipment=equipment;
        this.altBodyWeightExercise=alt;
        this.substituteExercise=sub;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imagePath);
        dest.writeString(equipment);
        dest.writeString(videoUrl);
        dest.writeList(altBodyWeightExercise);
        dest.writeList(substituteExercise);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
    }


    public class SubstituteExercise {

        @SerializedName("SubstituteExerciseId")
        @Expose
        private Integer substituteExerciseId;
        @SerializedName("SubstituteExerciseName")
        @Expose
        private String substituteExerciseName;

        public Integer getSubstituteExerciseId() {
            return substituteExerciseId;
        }

        public void setSubstituteExerciseId(Integer substituteExerciseId) {
            this.substituteExerciseId = substituteExerciseId;
        }

        public String getSubstituteExerciseName() {
            return substituteExerciseName;
        }

        public void setSubstituteExerciseName(String substituteExerciseName) {
            this.substituteExerciseName = substituteExerciseName;
        }

    }
    public class AltBodyWeightExercise {

        @SerializedName("BodyWeightAltExerciseId")
        @Expose
        private Integer bodyWeightAltExerciseId;
        @SerializedName("BodyWeightAltExerciseName")
        @Expose
        private String bodyWeightAltExerciseName;

        public Integer getBodyWeightAltExerciseId() {
            return bodyWeightAltExerciseId;
        }

        public void setBodyWeightAltExerciseId(Integer bodyWeightAltExerciseId) {
            this.bodyWeightAltExerciseId = bodyWeightAltExerciseId;
        }

        public String getBodyWeightAltExerciseName() {
            return bodyWeightAltExerciseName;
        }

        public void setBodyWeightAltExerciseName(String bodyWeightAltExerciseName) {
            this.bodyWeightAltExerciseName = bodyWeightAltExerciseName;
        }

    }
}



 package com.ashysystem.mbhq.model;

 import com.google.gson.annotations.Expose;
 import com.google.gson.annotations.SerializedName;

 import java.util.List;


 public class SessionOverViewModel
 {

    @SerializedName("Success")
    @Expose
    private Boolean success;
    @SerializedName("ErrorMessage")
    @Expose
    private Object errorMessage;
    @SerializedName("obj")
    @Expose
    private Obj obj;

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

    public Obj getObj() {
        return obj;
    }

    public void setObj(Obj obj) {
        this.obj = obj;
    }
     public class Exercise implements Cloneable{

         @SerializedName("Id")
         @Expose
         private Integer id;
         @SerializedName("SequenceNo")
         @Expose
         private Integer sequenceNo;
         @SerializedName("SetCount")
         @Expose
         private Integer setCount;
         @SerializedName("Name")
         @Expose
         private String name;
         @SerializedName("IsCircuit")
         @Expose
         private Boolean isCircuit;
         @SerializedName("ExerciseRepGoal")
         @Expose
         private String exerciseRepGoal;
         @SerializedName("RestComment")
         @Expose
         private String restComment;
         @SerializedName("CircuitRepeat")
         @Expose
         private String circuitRepeat;
         @SerializedName("VideoUrl")
         @Expose
         private String videoUrl;
         @SerializedName("VideoUrlPublic")
         @Expose
         private Object videoUrlPublic;
         @SerializedName("CircuitExercises")
         @Expose
         private List<Circuit> circuitExercises = null;
         @SerializedName("PhotoList")
         @Expose
         private List<String> photoList = null;
         @SerializedName("SmallPhotoList")
         @Expose
         private Object smallPhotoList;
         @SerializedName("Tips")
         @Expose
         private List<String> tips = null;
         @SerializedName("Instructions")
         @Expose
         private List<String> instructions = null;
         @SerializedName("Equipments")
         @Expose
         private List<String> equipments = null;
         @SerializedName("SubstituteEquipments")
         @Expose
         private List<String> substituteEquipments = null;
         @SerializedName("SubstituteExercises")
         @Expose
         private List<SubstituteExercise> substituteExercises = null;
         @SerializedName("AltBodyWeights")
         @Expose
         private List<Object> altBodyWeights = null;
         /*@SerializedName("AltBodyWeightExercises")
         @Expose
         private List<Object> altBodyWeightExercises = null;*/
         @SerializedName("AltBodyWeightExercises")
         @Expose
         private List<AltBodyWeightExercise> altBodyWeightExercises = null;
         @SerializedName("SrNo")
         @Expose
         private Integer srNo;
         @SerializedName("OneFromGroup")
         @Expose
         private Boolean oneFromGroup;
         @SerializedName("IsSuperSet")
         @Expose
         private Integer isSuperSet;
         @SerializedName("BodyType")
         @Expose
         private Integer bodyType;
         @SerializedName("Location")
         @Expose
         private Integer location;
         @SerializedName("Phase")
         @Expose
         private String phase;
         @SerializedName("SuperSetPosition")
         @Expose
         private Integer superSetPosition;
         @SerializedName("OrExercisePosition")
         @Expose
         private Integer orExercisePosition;
         @SerializedName("RepsUnit")
         @Expose
         private Integer repsUnit;



         @SerializedName("CircuitMinutes")
         @Expose
         private Integer CircuitMinutes;

         @SerializedName("RestUnitId")
         @Expose
         private Integer RestUnitId;

         @SerializedName("RepsUnitText")
         @Expose
         private String repsUnitText;

         //////////////
         @SerializedName("RestUnitText")
         @Expose
         private String restUnitText;
         @SerializedName("CircuitExerciseTips")
         @Expose
         private String circuitExerciseTips;
         @SerializedName("LabelOnly")
         @Expose
         private Boolean labelOnly;
         @SerializedName("RestUnit")
         @Expose
         private Integer restUnit;


         private transient String headerIndex="";



         private transient String middle="";



         private transient Integer newId=0;

         public Exercise(Integer id, Integer sequenceNo, Integer setCount, String name, Boolean isCircuit, String exerciseRepGoal, String restComment, String circuitRepeat, String videoUrl, Object videoUrlPublic, List<Circuit> circuitExercises, List<String> photoList, Object smallPhotoList, List<String> tips, List<String> instructions, List<String> equipments, List<String> substituteEquipments, List<SubstituteExercise> substituteExercises, List<Object> altBodyWeights, List<AltBodyWeightExercise> altBodyWeightExercises, Integer srNo, Boolean oneFromGroup, Integer isSuperSet, Integer bodyType, Integer location, String phase, Integer superSetPosition, Integer orExercisePosition, Integer repsUnit, String repsUnitText, String circuitExerciseTips, Boolean labelOnly, Integer restUnit, String headerIndex, String middle,String restUnitText ,Integer RestUnitId,Integer newId) {
             this.id = id;
             this.sequenceNo = sequenceNo;
             this.setCount = setCount;
             this.name = name;
             this.isCircuit = isCircuit;
             this.exerciseRepGoal = exerciseRepGoal;
             this.restComment = restComment;
             this.circuitRepeat = circuitRepeat;
             this.videoUrl = videoUrl;
             this.videoUrlPublic = videoUrlPublic;
             this.circuitExercises = circuitExercises;
             this.photoList = photoList;
             this.smallPhotoList = smallPhotoList;
             this.tips = tips;
             this.instructions = instructions;
             this.equipments = equipments;
             this.substituteEquipments = substituteEquipments;
             this.substituteExercises = substituteExercises;
             this.altBodyWeights = altBodyWeights;
             this.altBodyWeightExercises = altBodyWeightExercises;
             this.srNo = srNo;
             this.oneFromGroup = oneFromGroup;
             this.isSuperSet = isSuperSet;
             this.bodyType = bodyType;
             this.location = location;
             this.phase = phase;
             this.superSetPosition = superSetPosition;
             this.orExercisePosition = orExercisePosition;
             this.repsUnit = repsUnit;
             this.repsUnitText = repsUnitText;
             this.circuitExerciseTips = circuitExerciseTips;
             this.labelOnly = labelOnly;
             this.restUnit = restUnit;
             this.headerIndex = headerIndex;
             this.middle = middle;
             this.restUnitText=restUnitText;
             this.RestUnitId=RestUnitId;
             this.newId=newId;
         }

         public Exercise() {

         }
         public Integer getNewId() {
             return newId;
         }

         public void setNewId(Integer newId) {
             this.newId = newId;
         }

         public Integer getRestUnitId() {
             return RestUnitId;
         }

         public void setRestUnitId(Integer restUnitId) {
             RestUnitId = restUnitId;
         }
         public Object clone() throws CloneNotSupportedException {

             return (Exercise)super.clone();
         }
         public String getRestUnitText() {
             return restUnitText;
         }

         public void setRestUnitText(String restUnitText) {
             this.restUnitText = restUnitText;
         }
         public String getMiddle() {
             return middle;
         }

         public void setMiddle(String middle) {
             this.middle = middle;
         }

         public String getHeaderIndex() {
             return headerIndex;
         }

         public void setHeaderIndex(String headerIndex) {
             this.headerIndex = headerIndex;
         }

         public Integer getId() {
             return id;
         }

         public void setId(Integer id) {
             this.id = id;
         }

         public Integer getSequenceNo() {
             return sequenceNo;
         }

         public void setSequenceNo(Integer sequenceNo) {
             this.sequenceNo = sequenceNo;
         }

         public Integer getSetCount() {
             return setCount;
         }

         public void setSetCount(Integer setCount) {
             this.setCount = setCount;
         }

         public String getName() {
             return name;
         }

         public void setName(String name) {
             this.name = name;
         }

         public Boolean getIsCircuit() {
             return isCircuit;
         }

         public void setIsCircuit(Boolean isCircuit) {
             this.isCircuit = isCircuit;
         }

         public String getExerciseRepGoal() {
             return exerciseRepGoal;
         }

         public void setExerciseRepGoal(String exerciseRepGoal) {
             this.exerciseRepGoal = exerciseRepGoal;
         }

         public String getRestComment() {
             return restComment;
         }

         public void setRestComment(String restComment) {
             this.restComment = restComment;
         }

         public String getCircuitRepeat() {
             return circuitRepeat;
         }

         public void setCircuitRepeat(String circuitRepeat) {
             this.circuitRepeat = circuitRepeat;
         }

         public String getVideoUrl() {
             return videoUrl;
         }

         public void setVideoUrl(String videoUrl) {
             this.videoUrl = videoUrl;
         }

         public Object getVideoUrlPublic() {
             return videoUrlPublic;
         }

         public void setVideoUrlPublic(Object videoUrlPublic) {
             this.videoUrlPublic = videoUrlPublic;
         }

         public List<Circuit> getCircuitExercises() {
             return circuitExercises;
         }

         public void setCircuitExercises(List<Circuit> circuitExercises) {
             this.circuitExercises = circuitExercises;
         }

         public List<String> getPhotoList() {
             return photoList;
         }

         public void setPhotoList(List<String> photoList) {
             this.photoList = photoList;
         }

         public Object getSmallPhotoList() {
             return smallPhotoList;
         }

         public void setSmallPhotoList(Object smallPhotoList) {
             this.smallPhotoList = smallPhotoList;
         }

         public List<String> getTips() {
             return tips;
         }

         public void setTips(List<String> tips) {
             this.tips = tips;
         }

         public List<String> getInstructions() {
             return instructions;
         }

         public void setInstructions(List<String> instructions) {
             this.instructions = instructions;
         }

         public List<String> getEquipments() {
             return equipments;
         }

         public void setEquipments(List<String> equipments) {
             this.equipments = equipments;
         }

         public List<String> getSubstituteEquipments() {
             return substituteEquipments;
         }

         public void setSubstituteEquipments(List<String> substituteEquipments) {
             this.substituteEquipments = substituteEquipments;
         }

         public List<SubstituteExercise> getSubstituteExercises() {
             return substituteExercises;
         }

         public void setSubstituteExercises(List<SubstituteExercise> substituteExercises) {
             this.substituteExercises = substituteExercises;
         }

         public List<Object> getAltBodyWeights() {
             return altBodyWeights;
         }

         public void setAltBodyWeights(List<Object> altBodyWeights) {
             this.altBodyWeights = altBodyWeights;
         }

         /*public List<Object> getAltBodyWeightExercises() {
             return altBodyWeightExercises;
         }

         public void setAltBodyWeightExercises(List<Object> altBodyWeightExercises) {
             this.altBodyWeightExercises = altBodyWeightExercises;
         }*/

         public List<AltBodyWeightExercise> getAltBodyWeightExercises() {
             return altBodyWeightExercises;
         }

         public void setAltBodyWeightExercises(List<AltBodyWeightExercise> altBodyWeightExercises) {
             this.altBodyWeightExercises = altBodyWeightExercises;
         }

         public Integer getSrNo() {
             return srNo;
         }

         public void setSrNo(Integer srNo) {
             this.srNo = srNo;
         }

         public Boolean getOneFromGroup() {
             return oneFromGroup;
         }

         public void setOneFromGroup(Boolean oneFromGroup) {
             this.oneFromGroup = oneFromGroup;
         }

         public Integer getIsSuperSet() {
             return isSuperSet;
         }

         public void setIsSuperSet(Integer isSuperSet) {
             this.isSuperSet = isSuperSet;
         }

         public Integer getBodyType() {
             return bodyType;
         }

         public void setBodyType(Integer bodyType) {
             this.bodyType = bodyType;
         }

         public Integer getLocation() {
             return location;
         }

         public void setLocation(Integer location) {
             this.location = location;
         }

         public String getPhase() {
             return phase;
         }

         public void setPhase(String phase) {
             this.phase = phase;
         }

         public Integer getSuperSetPosition() {
             return superSetPosition;
         }

         public void setSuperSetPosition(Integer superSetPosition) {
             this.superSetPosition = superSetPosition;
         }

         public Integer getOrExercisePosition() {
             return orExercisePosition;
         }

         public void setOrExercisePosition(Integer orExercisePosition) {
             this.orExercisePosition = orExercisePosition;
         }
         public Integer getCircuitMinutes() {
             return CircuitMinutes;
         }

         public void setCircuitMinutes(Integer circuitMinutes) {
             CircuitMinutes = circuitMinutes;
         }

         public Integer getRepsUnit() {
             return repsUnit;
         }

         public void setRepsUnit(Integer repsUnit) {
             this.repsUnit = repsUnit;
         }


         public String getCircuitExerciseTips() {
             return circuitExerciseTips;
         }

         public void setCircuitExerciseTips(String circuitExerciseTips) {
             this.circuitExerciseTips = circuitExerciseTips;
         }

         public Boolean getLabelOnly() {
             return labelOnly;
         }

         public void setLabelOnly(Boolean labelOnly) {
             this.labelOnly = labelOnly;
         }

         public Integer getRestUnit() {
             return restUnit;
         }

         public void setRestUnit(Integer restUnit) {
             this.restUnit = restUnit;
         }

         public String getRepsUnitText() {
             return repsUnitText;
         }

         public void setRepsUnitText(String repsUnitText) {
             this.repsUnitText = repsUnitText;
         }
     }

     public class Circuit implements Cloneable{

         @SerializedName("ExerciseId")
         @Expose
         private Integer exerciseId;
         @SerializedName("UserId")
         @Expose
         private Integer userId;
         @SerializedName("SequenceNo")
         @Expose
         private Integer sequenceNo;
         @SerializedName("ExerciseName")
         @Expose
         private String exerciseName;
         @SerializedName("SetCount")
         @Expose
         private Integer setCount;
         @SerializedName("RepGoal")
         @Expose
         private String repGoal;
         @SerializedName("RestComment")
         @Expose
         private String restComment;
         @SerializedName("IsSuperSet")
         @Expose
         private Integer isSuperSet;
         @SerializedName("AtHome")
         @Expose
         private Boolean atHome;
         @SerializedName("Gym")
         @Expose
         private Boolean gym;
         @SerializedName("Park")
         @Expose
         private Boolean park;
         @SerializedName("Weights")
         @Expose
         private Boolean weights;
         @SerializedName("CardioHigh")
         @Expose
         private Boolean cardioHigh;
         @SerializedName("CardioLow")
         @Expose
         private Boolean cardioLow;
         @SerializedName("HIIT")
         @Expose
         private Boolean hIIT;
         @SerializedName("YogaFlex")
         @Expose
         private Boolean yogaFlex;
         @SerializedName("Pilates")
         @Expose
         private Boolean pilates;
         @SerializedName("Sport")
         @Expose
         private Boolean sport;
         @SerializedName("FBW")
         @Expose
         private Boolean fBW;
         @SerializedName("FunctionalFitness")
         @Expose
         private Boolean functionalFitness;
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
         @SerializedName("MinimumFitness")
         @Expose
         private Integer minimumFitness;
         @SerializedName("VideoId")
         @Expose
         private Integer videoId;
         @SerializedName("PhotoList")
         @Expose
         private List<String> photoList = null;
         @SerializedName("SmallPhotoList")
         @Expose
         private List<String> smallPhotoList = null;
         @SerializedName("Tips")
         @Expose
         private List<Object> tips = null;
         @SerializedName("Instructions")
         @Expose
         private List<Object> instructions = null;
         @SerializedName("VideoUrl")
         @Expose
         private Object videoUrl;
         @SerializedName("VideoUrlPublic")
         @Expose
         private String videoPublicUrl;
         @SerializedName("Equipments")
         @Expose
         private List<String> equipments = null;
         @SerializedName("SubstituteEquipments")
         @Expose
         private List<Object> substituteEquipments = null;
         @SerializedName("SubstituteExercises")
         @Expose
         private List<SubstituteExercise> substituteExercises = null;
         @SerializedName("AltBodyWeights")
         @Expose
         private List<Object> altBodyWeights = null;
         /*@SerializedName("AltBodyWeightExercises")
         @Expose
         private List<Object> altBodyWeightExercises = null;*/
         @SerializedName("AltBodyWeightExercises")
         @Expose
         private List<AltBodyWeightExercise> altBodyWeightExercises = null;
         @SerializedName("SuperSetPosition")
         @Expose
         private Integer superSetPosition;
         @SerializedName("RepsUnit")
         @Expose
         private Integer repsUnit;
         @SerializedName("RestUnitId")
         @Expose
         private Integer restUnitId;
         @SerializedName("CircuitExerciseTips")
         @Expose
         private Object circuitExerciseTips;
         @SerializedName("RestUnit")
         @Expose
         private Object restUnit;
         @SerializedName("Round")
         @Expose
         private String round;
         @SerializedName("Station")
         @Expose
         private Object station;
         @SerializedName("LabelOnly")
         @Expose
         private Boolean labelOnly;
         @SerializedName("RestUnitText")
         @Expose
         private String restUnitText;
         @SerializedName("RepsUnitText")
         @Expose
         private String repsUnitText;


         private transient  String index="";
         private transient  int rank=0;

         private transient  String group="";
         private transient  String middle="";

         public Circuit(Integer exerciseId, Integer userId, Integer sequenceNo, String exerciseName, Integer setCount, String repGoal, String restComment, Integer isSuperSet, Boolean atHome, Boolean gym, Boolean park, Boolean weights, Boolean cardioHigh, Boolean cardioLow, Boolean hIIT, Boolean yogaFlex, Boolean pilates, Boolean sport, Boolean fBW, Boolean functionalFitness, Boolean fullBody, Boolean upperBody, Boolean lowerBody, Boolean core, Integer minimumFitness, Integer videoId, List<String> photoList, List<String> smallPhotoList, List<Object> tips, List<Object> instructions, Object videoUrl,String videoPublicUrl,List<String> equipments, List<Object> substituteEquipments, List<SubstituteExercise> substituteExercises, List<Object> altBodyWeights, List<AltBodyWeightExercise> altBodyWeightExercises, Integer superSetPosition, Integer repsUnit, Integer restUnitId, Object circuitExerciseTips, Object restUnit, String round, Object station, Boolean labelOnly, String restUnitText, String repsUnitText, String index, String group, String middle,int rank) {
             this.exerciseId = exerciseId;
             this.userId = userId;
             this.sequenceNo = sequenceNo;
             this.exerciseName = exerciseName;
             this.setCount = setCount;
             this.repGoal = repGoal;
             this.restComment = restComment;
             this.isSuperSet = isSuperSet;
             this.atHome = atHome;
             this.gym = gym;
             this.park = park;
             this.weights = weights;
             this.cardioHigh = cardioHigh;
             this.cardioLow = cardioLow;
             this.hIIT = hIIT;
             this.yogaFlex = yogaFlex;
             this.pilates = pilates;
             this.sport = sport;
             this.fBW = fBW;
             this.functionalFitness = functionalFitness;
             this.fullBody = fullBody;
             this.upperBody = upperBody;
             this.lowerBody = lowerBody;
             this.core = core;
             this.minimumFitness = minimumFitness;
             this.videoId = videoId;
             this.photoList = photoList;
             this.smallPhotoList = smallPhotoList;
             this.tips = tips;
             this.instructions = instructions;
             this.videoUrl = videoUrl;
             this.videoPublicUrl = videoPublicUrl;
             this.equipments = equipments;
             this.substituteEquipments = substituteEquipments;
             this.substituteExercises = substituteExercises;
             this.altBodyWeights = altBodyWeights;
             this.altBodyWeightExercises = altBodyWeightExercises;
             this.superSetPosition = superSetPosition;
             this.repsUnit = repsUnit;
             this.restUnitId = restUnitId;
             this.circuitExerciseTips = circuitExerciseTips;
             this.restUnit = restUnit;
             this.round = round;
             this.station = station;
             this.labelOnly = labelOnly;
             this.restUnitText = restUnitText;
             this.repsUnitText = repsUnitText;
             this.index = index;
             this.group = group;
             this.middle = middle;
             this.rank=rank;
         }
         public String getMiddle() {
             return middle;
         }

         public void setMiddle(String middle) {
             this.middle = middle;
         }

         public String getGroup() {
             return group;
         }

         public void setGroup(String group) {
             this.group = group;
         }

         public String getIndex() {
             return index;
         }

         public void setIndex(String index) {
             this.index = index;
         }
         public int getRank() {
             return rank;
         }

         public void setRank(int rank) {
             this.rank = rank;
         }

         public Integer getExerciseId() {
             return exerciseId;
         }

         public void setExerciseId(Integer exerciseId) {
             this.exerciseId = exerciseId;
         }

         public Integer getUserId() {
             return userId;
         }

         public void setUserId(Integer userId) {
             this.userId = userId;
         }

         public Integer getSequenceNo() {
             return sequenceNo;
         }

         public void setSequenceNo(Integer sequenceNo) {
             this.sequenceNo = sequenceNo;
         }

         public String getExerciseName() {
             return exerciseName;
         }

         public void setExerciseName(String exerciseName) {
             this.exerciseName = exerciseName;
         }

         public Integer getSetCount() {
             return setCount;
         }

         public void setSetCount(Integer setCount) {
             this.setCount = setCount;
         }

         public String getRepGoal() {
             return repGoal;
         }

         public void setRepGoal(String repGoal) {
             this.repGoal = repGoal;
         }

         public String getRestComment() {
             return restComment;
         }

         public void setRestComment(String restComment) {
             this.restComment = restComment;
         }

         public Integer getIsSuperSet() {
             return isSuperSet;
         }

         public void setIsSuperSet(Integer isSuperSet) {
             this.isSuperSet = isSuperSet;
         }

         public Boolean getAtHome() {
             return atHome;
         }

         public void setAtHome(Boolean atHome) {
             this.atHome = atHome;
         }

         public Boolean getGym() {
             return gym;
         }

         public void setGym(Boolean gym) {
             this.gym = gym;
         }

         public Boolean getPark() {
             return park;
         }

         public void setPark(Boolean park) {
             this.park = park;
         }

         public Boolean getWeights() {
             return weights;
         }

         public void setWeights(Boolean weights) {
             this.weights = weights;
         }

         public Boolean getCardioHigh() {
             return cardioHigh;
         }

         public void setCardioHigh(Boolean cardioHigh) {
             this.cardioHigh = cardioHigh;
         }

         public Boolean getCardioLow() {
             return cardioLow;
         }

         public void setCardioLow(Boolean cardioLow) {
             this.cardioLow = cardioLow;
         }

         public Boolean getHIIT() {
             return hIIT;
         }

         public void setHIIT(Boolean hIIT) {
             this.hIIT = hIIT;
         }

         public Boolean getYogaFlex() {
             return yogaFlex;
         }

         public void setYogaFlex(Boolean yogaFlex) {
             this.yogaFlex = yogaFlex;
         }

         public Boolean getPilates() {
             return pilates;
         }

         public void setPilates(Boolean pilates) {
             this.pilates = pilates;
         }

         public Boolean getSport() {
             return sport;
         }

         public void setSport(Boolean sport) {
             this.sport = sport;
         }

         public Boolean getFBW() {
             return fBW;
         }

         public void setFBW(Boolean fBW) {
             this.fBW = fBW;
         }

         public Boolean getFunctionalFitness() {
             return functionalFitness;
         }

         public void setFunctionalFitness(Boolean functionalFitness) {
             this.functionalFitness = functionalFitness;
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

         public Integer getMinimumFitness() {
             return minimumFitness;
         }

         public void setMinimumFitness(Integer minimumFitness) {
             this.minimumFitness = minimumFitness;
         }

         public Integer getVideoId() {
             return videoId;
         }

         public void setVideoId(Integer videoId) {
             this.videoId = videoId;
         }

         public List<String> getPhotoList() {
             return photoList;
         }

         public void setPhotoList(List<String> photoList) {
             this.photoList = photoList;
         }

         public List<String> getSmallPhotoList() {
             return smallPhotoList;
         }

         public void setSmallPhotoList(List<String> smallPhotoList) {
             this.smallPhotoList = smallPhotoList;
         }

         public List<Object> getTips() {
             return tips;
         }

         public void setTips(List<Object> tips) {
             this.tips = tips;
         }

         public List<Object> getInstructions() {
             return instructions;
         }

         public void setInstructions(List<Object> instructions) {
             this.instructions = instructions;
         }

         public Object getVideoUrl() {
             return videoUrl;
         }

         public void setVideoUrl(Object videoUrl) {
             this.videoUrl = videoUrl;
         }

         public String getVideoPublicUrl()
         {
             return videoPublicUrl;
         }

         public void setVideoPublicUrl(String videoPublicUrl){
             this.videoPublicUrl=videoPublicUrl;
         }

         public List<String> getEquipments() {
             return equipments;
         }

         public void setEquipments(List<String> equipments) {
             this.equipments = equipments;
         }

         public List<Object> getSubstituteEquipments() {
             return substituteEquipments;
         }

         public void setSubstituteEquipments(List<Object> substituteEquipments) {
             this.substituteEquipments = substituteEquipments;
         }

         public List<SubstituteExercise> getSubstituteExercises() {
             return substituteExercises;
         }

         public void setSubstituteExercises(List<SubstituteExercise> substituteExercises) {
             this.substituteExercises = substituteExercises;
         }

         public List<Object> getAltBodyWeights() {
             return altBodyWeights;
         }

         public void setAltBodyWeights(List<Object> altBodyWeights) {
             this.altBodyWeights = altBodyWeights;
         }

         /*public List<Object> getAltBodyWeightExercises() {
             return altBodyWeightExercises;
         }

         public void setAltBodyWeightExercises(List<Object> altBodyWeightExercises) {
             this.altBodyWeightExercises = altBodyWeightExercises;
         }*/

         public List<AltBodyWeightExercise> getAltBodyWeightExercises() {
             return altBodyWeightExercises;
         }

         public void setAltBodyWeightExercises(List<AltBodyWeightExercise> altBodyWeightExercises) {
             this.altBodyWeightExercises = altBodyWeightExercises;
         }

         public Integer getSuperSetPosition() {
             return superSetPosition;
         }

         public void setSuperSetPosition(Integer superSetPosition) {
             this.superSetPosition = superSetPosition;
         }

         public Integer getRepsUnit() {
             return repsUnit;
         }

         public void setRepsUnit(Integer repsUnit) {
             this.repsUnit = repsUnit;
         }

         public Integer getRestUnitId() {
             return restUnitId;
         }

         public void setRestUnitId(Integer restUnitId) {
             this.restUnitId = restUnitId;
         }

         public Object getCircuitExerciseTips() {
             return circuitExerciseTips;
         }

         public void setCircuitExerciseTips(Object circuitExerciseTips) {
             this.circuitExerciseTips = circuitExerciseTips;
         }

         public Object getRestUnit() {
             return restUnit;
         }

         public void setRestUnit(Object restUnit) {
             this.restUnit = restUnit;
         }

         public String getRound() {
             return round;
         }

         public void setRound(String round) {
             this.round = round;
         }

         public Object getStation() {
             return station;
         }

         public void setStation(Object station) {
             this.station = station;
         }

         public Boolean getLabelOnly() {
             return labelOnly;
         }

         public void setLabelOnly(Boolean labelOnly) {
             this.labelOnly = labelOnly;
         }

         public String getRestUnitText() {
             return restUnitText;
         }

         public void setRestUnitText(String restUnitText) {
             this.restUnitText = restUnitText;
         }

         public String getRepsUnitText() {
             return repsUnitText;
         }

         public void setRepsUnitText(String repsUnitText) {
             this.repsUnitText = repsUnitText;
         }
         public Object clone() throws CloneNotSupportedException {

             return (Circuit)super.clone();
         }

     }
     public class Obj {

         @SerializedName("ExerciseSessionId")
         @Expose
         public Integer exerciseSessionId;
         @SerializedName("SessionTitle")
         @Expose
         private String sessionTitle;
         @SerializedName("WorkoutType")
         @Expose
         private String workoutType;
         @SerializedName("SessionCategory")
         @Expose
         private Integer sessionCategory;
         @SerializedName("BodyArea")
         @Expose
         private String bodyArea;
         @SerializedName("EquipmentRequired")
         @Expose
         private Integer equipmentRequired;
         @SerializedName("SessionCode")
         @Expose
         private String sessionCode;
         @SerializedName("IsPersonalised")
         @Expose
         private Boolean isPersonalised;
         @SerializedName("PersonlisedSessionId")
         @Expose
         private Object personlisedSessionId;
         @SerializedName("Duration")
         @Expose
         private Integer duration;
         @SerializedName("Exercises")
         @Expose
         private List<Exercise> exercises = null;
         @SerializedName("SessionOverview")
         @Expose
         private List<String> sessionOverview = null;
         @SerializedName("Location")
         @Expose
         private String location;




         /////////////////
         @SerializedName("Time")
         @Expose
         private Integer Time;



         @SerializedName("IsFavourite")
         @Expose
         private boolean IsFavourite;
         @SerializedName("SessionFlowId")
         @Expose
         private Integer SessionFlowId;
         /////////////
         @SerializedName("VideoLink")
         @Expose
         private String videoLink;
         @SerializedName("DownloadVideoLink")
         @Expose
         private String downloadVideoLink;



         @SerializedName("PublicVideoUrl")
         @Expose
         private String publicVideoUrl;

         public Integer getExerciseSessionId() {
             return exerciseSessionId;
         }

         public void setExerciseSessionId(Integer exerciseSessionId) {
             this.exerciseSessionId = exerciseSessionId;
         }

         public String getSessionTitle() {
             return sessionTitle;
         }

         public void setSessionTitle(String sessionTitle) {
             this.sessionTitle = sessionTitle;
         }

         public String getWorkoutType() {
             return workoutType;
         }

         public void setWorkoutType(String workoutType) {
             this.workoutType = workoutType;
         }

         public Integer getSessionCategory() {
             return sessionCategory;
         }

         public void setSessionCategory(Integer sessionCategory) {
             this.sessionCategory = sessionCategory;
         }

         public String getBodyArea() {
             return bodyArea;
         }

         public void setBodyArea(String bodyArea) {
             this.bodyArea = bodyArea;
         }

         public Integer getEquipmentRequired() {
             return equipmentRequired;
         }

         public void setEquipmentRequired(Integer equipmentRequired) {
             this.equipmentRequired = equipmentRequired;
         }

         public String getSessionCode() {
             return sessionCode;
         }

         public void setSessionCode(String sessionCode) {
             this.sessionCode = sessionCode;
         }

         public Boolean getIsPersonalised() {
             return isPersonalised;
         }

         public void setIsPersonalised(Boolean isPersonalised) {
             this.isPersonalised = isPersonalised;
         }

         public Object getPersonlisedSessionId() {
             return personlisedSessionId;
         }

         public void setPersonlisedSessionId(Object personlisedSessionId) {
             this.personlisedSessionId = personlisedSessionId;
         }

         public Integer getDuration() {
             return duration;
         }

         public void setDuration(Integer duration) {
             this.duration = duration;
         }

         public List<Exercise> getExercises() {
             return exercises;
         }

         public void setExercises(List<Exercise> exercises) {
             this.exercises = exercises;
         }

         public List<String> getSessionOverview() {
             return sessionOverview;
         }

         public void setSessionOverview(List<String> sessionOverview) {
             this.sessionOverview = sessionOverview;
         }

         public String getLocation() {
             return location;
         }

         public void setLocation(String location) {
             this.location = location;
         }



         public String getVideoLink() {
             return videoLink;
         }
         public String getPublicVideoUrl() {
             return publicVideoUrl;
         }

         public void setPublicVideoUrl(String publicVideoUrl) {
             this.publicVideoUrl = publicVideoUrl;
         }
         public void setVideoLink(String videoLink) {
             this.videoLink = videoLink;
         }

         public String getDownloadVideoLink() {
             return downloadVideoLink;
         }

         public void setDownloadVideoLink(String downloadVideoLink) {
             this.downloadVideoLink = downloadVideoLink;
         }
         public boolean isFavourite() {
             return IsFavourite;
         }

         public void setFavourite(boolean favourite) {
             IsFavourite = favourite;
         }
         public Integer getTime() {
             return Time;
         }

         public void setTime(Integer time) {
             Time = time;
         }
         public Integer getSessionFlowId() {
             return SessionFlowId;
         }

         public void setSessionFlowId(Integer sessionFlowId) {
             this.SessionFlowId = sessionFlowId;
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






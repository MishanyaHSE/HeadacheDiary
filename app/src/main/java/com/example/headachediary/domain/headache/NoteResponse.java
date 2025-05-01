package com.example.headachediary.domain.headache;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoteResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("is_headache")
    @Expose
    private Boolean isHeadache;
    @SerializedName("headache_time")
    @Expose
    private String headacheTime;
    @SerializedName("intensity")
    @Expose
    private Integer intensity;
    @SerializedName("medicine")
    @Expose
    private List<Medicine> medicine;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getIsHeadache() {
        return isHeadache;
    }

    public void setIsHeadache(Boolean isHeadache) {
        this.isHeadache = isHeadache;
    }

    public String getHeadacheTime() {
        return headacheTime;
    }

    public void setHeadacheTime(String headacheTime) {
        this.headacheTime = headacheTime;
    }

    public Integer getIntensity() {
        return intensity;
    }

    public void setIntensity(Integer intensity) {
        this.intensity = intensity;
    }

    public List<Medicine> getMedicine() {
        return medicine;
    }

    public void setMedicine(List<Medicine> medicine) {
        this.medicine = medicine;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NoteResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("userId");
        sb.append('=');
        sb.append(((this.userId == null)?"<null>":this.userId));
        sb.append(',');
        sb.append("date");
        sb.append('=');
        sb.append(((this.date == null)?"<null>":this.date));
        sb.append(',');
        sb.append("isHeadache");
        sb.append('=');
        sb.append(((this.isHeadache == null)?"<null>":this.isHeadache));
        sb.append(',');
        sb.append("headacheTime");
        sb.append('=');
        sb.append(((this.headacheTime == null)?"<null>":this.headacheTime));
        sb.append(',');
        sb.append("intensity");
        sb.append('=');
        sb.append(((this.intensity == null)?"<null>":this.intensity));
        sb.append(',');
        sb.append("medicine");
        sb.append('=');
        sb.append(((this.medicine == null)?"<null>":this.medicine));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.date == null)? 0 :this.date.hashCode()));
        result = ((result* 31)+((this.intensity == null)? 0 :this.intensity.hashCode()));
        result = ((result* 31)+((this.headacheTime == null)? 0 :this.headacheTime.hashCode()));
        result = ((result* 31)+((this.isHeadache == null)? 0 :this.isHeadache.hashCode()));
        result = ((result* 31)+((this.medicine == null)? 0 :this.medicine.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.userId == null)? 0 :this.userId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NoteResponse) == false) {
            return false;
        }
        NoteResponse rhs = ((NoteResponse) other);
        return ((((((((this.date == rhs.date)||((this.date!= null)&&this.date.equals(rhs.date)))&&((this.intensity == rhs.intensity)||((this.intensity!= null)&&this.intensity.equals(rhs.intensity))))&&((this.headacheTime == rhs.headacheTime)||((this.headacheTime!= null)&&this.headacheTime.equals(rhs.headacheTime))))&&((this.isHeadache == rhs.isHeadache)||((this.isHeadache!= null)&&this.isHeadache.equals(rhs.isHeadache))))&&((this.medicine == rhs.medicine)||((this.medicine!= null)&&this.medicine.equals(rhs.medicine))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.userId == rhs.userId)||((this.userId!= null)&&this.userId.equals(rhs.userId))));
    }

}

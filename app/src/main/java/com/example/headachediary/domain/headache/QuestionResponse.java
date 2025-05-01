package com.example.headachediary.domain.headache;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("second_question")
    @Expose
    private Boolean secondQuestion;
    @SerializedName("third_question")
    @Expose
    private Boolean thirdQuestion;
    @SerializedName("fourth_question")
    @Expose
    private Boolean fourthQuestion;

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

    public Boolean getSecondQuestion() {
        return secondQuestion;
    }

    public void setSecondQuestion(Boolean secondQuestion) {
        this.secondQuestion = secondQuestion;
    }

    public Boolean getThirdQuestion() {
        return thirdQuestion;
    }

    public void setThirdQuestion(Boolean thirdQuestion) {
        this.thirdQuestion = thirdQuestion;
    }

    public Boolean getFourthQuestion() {
        return fourthQuestion;
    }

    public void setFourthQuestion(Boolean fourthQuestion) {
        this.fourthQuestion = fourthQuestion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(QuestionResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("userId");
        sb.append('=');
        sb.append(((this.userId == null)?"<null>":this.userId));
        sb.append(',');
        sb.append("secondQuestion");
        sb.append('=');
        sb.append(((this.secondQuestion == null)?"<null>":this.secondQuestion));
        sb.append(',');
        sb.append("thirdQuestion");
        sb.append('=');
        sb.append(((this.thirdQuestion == null)?"<null>":this.thirdQuestion));
        sb.append(',');
        sb.append("fourthQuestion");
        sb.append('=');
        sb.append(((this.fourthQuestion == null)?"<null>":this.fourthQuestion));
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
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.secondQuestion == null)? 0 :this.secondQuestion.hashCode()));
        result = ((result* 31)+((this.userId == null)? 0 :this.userId.hashCode()));
        result = ((result* 31)+((this.thirdQuestion == null)? 0 :this.thirdQuestion.hashCode()));
        result = ((result* 31)+((this.fourthQuestion == null)? 0 :this.fourthQuestion.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof QuestionResponse) == false) {
            return false;
        }
        QuestionResponse rhs = ((QuestionResponse) other);
        return ((((((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id)))&&((this.secondQuestion == rhs.secondQuestion)||((this.secondQuestion!= null)&&this.secondQuestion.equals(rhs.secondQuestion))))&&((this.userId == rhs.userId)||((this.userId!= null)&&this.userId.equals(rhs.userId))))&&((this.thirdQuestion == rhs.thirdQuestion)||((this.thirdQuestion!= null)&&this.thirdQuestion.equals(rhs.thirdQuestion))))&&((this.fourthQuestion == rhs.fourthQuestion)||((this.fourthQuestion!= null)&&this.fourthQuestion.equals(rhs.fourthQuestion))));
    }

}

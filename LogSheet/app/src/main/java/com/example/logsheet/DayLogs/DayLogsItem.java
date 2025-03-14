package com.example.logsheet.DayLogs;

public class DayLogsItem {
    String id, userId, feeling, activityDesc, hourDuration, minuteDuration, intensity,
            questionAnswer1, questionAnswer2, questionAnswer3, questionAnswer4, questionAnswer5, questionAnswer6, questionAnswer7,
            duration;

    public DayLogsItem(String id, String userId, String feeling, String activityDesc, String hourDuration, String minuteDuration, String intensity, String questionAnswer1, String questionAnswer2, String questionAnswer3, String questionAnswer4, String questionAnswer5, String questionAnswer6, String questionAnswer7) {
        this.id = id;
        this.userId = userId;
        this.feeling = feeling;
        this.activityDesc = activityDesc;
        this.hourDuration = hourDuration;
        this.minuteDuration = minuteDuration;
        this.intensity = intensity;
        this.questionAnswer1 = questionAnswer1;
        this.questionAnswer2 = questionAnswer2;
        this.questionAnswer3 = questionAnswer3;
        this.questionAnswer4 = questionAnswer4;
        this.questionAnswer5 = questionAnswer5;
        this.questionAnswer6 = questionAnswer6;
        this.questionAnswer7 = questionAnswer7;
    }

    public String getQuestionAnswer2() {
        return questionAnswer2;
    }

    public void setQuestionAnswer2(String questionAnswer2) {
        this.questionAnswer2 = questionAnswer2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }

    public String getHourDuration() {
        return hourDuration;
    }

    public void setHourDuration(String hourDuration) {
        this.hourDuration = hourDuration;
    }

    public String getMinuteDuration() {
        return minuteDuration;
    }

    public void setMinuteDuration(String minuteDuration) {
        this.minuteDuration = minuteDuration;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public String getQuestionAnswer1() {
        return questionAnswer1;
    }

    public void setQuestionAnswer1(String questionAnswer1) {
        this.questionAnswer1 = questionAnswer1;
    }

    public String getQuestionAnswer3() {
        return questionAnswer3;
    }

    public void setQuestionAnswer3(String questionAnswer3) {
        this.questionAnswer3 = questionAnswer3;
    }

    public String getQuestionAnswer4() {
        return questionAnswer4;
    }

    public void setQuestionAnswer4(String questionAnswer4) {
        this.questionAnswer4 = questionAnswer4;
    }

    public String getQuestionAnswer5() {
        return questionAnswer5;
    }

    public void setQuestionAnswer5(String questionAnswer5) {
        this.questionAnswer5 = questionAnswer5;
    }

    public String getQuestionAnswer6() {
        return questionAnswer6;
    }

    public void setQuestionAnswer6(String questionAnswer6) {
        this.questionAnswer6 = questionAnswer6;
    }

    public String getQuestionAnswer7() {
        return questionAnswer7;
    }

    public void setQuestionAnswer7(String questionAnswer7) {
        this.questionAnswer7 = questionAnswer7;
    }

    public String getDuration() {
        return hourDuration + "hr " + minuteDuration + "mins";
    }
}

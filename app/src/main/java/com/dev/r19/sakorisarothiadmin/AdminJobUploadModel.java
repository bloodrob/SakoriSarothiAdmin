package com.dev.r19.sakorisarothiadmin;

/**
 * Created by R19 on 1/10/2020.
 */

public class AdminJobUploadModel {
    private String JobName;
    private String JobSubject;
    private String LastDate;
    private String JobExperience;
    private String StiepndSalary;
    private String JobDetails;
    private String MyFileUrl;

    public AdminJobUploadModel() {

    }
    public AdminJobUploadModel(String JobName, String JobSubject, String LastDate, String JobExperience, String StipendSalary, String JObDetails, String MyFileUrl) {
        this.JobName = JobName;
        this.JobSubject = JobSubject;
        this.LastDate = LastDate;
        this.JobExperience = JobExperience;
        this.StiepndSalary = StipendSalary;
        this.JobDetails = JObDetails;
        this.MyFileUrl = MyFileUrl;
    }

    public String getJobName() {
        return JobName;
    }public void setJobName(String JobName) {
        this.JobName = JobName;
    }

    public String getJobSubject() {
        return JobSubject;
    }
    public void setJobSubject(String JobSubject) {
        this.JobSubject = JobSubject;
    }

    public String getLastDate() {
        return LastDate;
    }
    public void setLastDate(String LastDate) {
        this.LastDate = LastDate;
    }

    public String getJobExperience() {
        return JobExperience;
    }public void setJobExperience(String JobExperience) {
        this.JobExperience =JobExperience;
    }

    public String getStiepndSalary() {
        return StiepndSalary;
    }
    public void setStiepndSalary(String StiepndSalary){
        this.StiepndSalary = StiepndSalary;
    }

    public String getJobDetails() {
        return JobDetails;
    }
    public void setJobDetails(String JobDetails) {
        this.JobDetails = JobDetails;
    }

    public String getMyFileUrl() {
        return MyFileUrl;
    }
    public void setMyFileUrl(String MyFileUrl) {
        this.MyFileUrl = MyFileUrl;
    }
}

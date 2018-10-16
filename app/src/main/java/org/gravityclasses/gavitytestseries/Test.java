package org.gravityclasses.gavitytestseries;

public class Test {
    String name,durationMinutes;
    int id,isEnabled=1,classId,packageId;
    TestPaper tp;
    TestStatus ts=null;
    TestResult tr=null;

    public Test(String name,String durationMinutes,int id,int isEnabled,int classId,int packageId,TestPaper tp){
        this.name=name;
        this.durationMinutes=durationMinutes;
        this.id=id;
        this.isEnabled=isEnabled;
        this.classId=classId;
        this.packageId=packageId;
        this.tp=tp;

    }

    public void setTestStatus(TestStatus ts){
        this.ts=ts;

    }

    public void setTestResult(TestResult tr){
        this.tr=tr;

    }




}

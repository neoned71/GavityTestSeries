package org.gravityclasses.gavitytestseries;

public class Test {
    String name,durationMinutes;
    int id,isEnabled=1,classId,packageId;
    TestPaper tp;
    TestStatus ts=null;
    TestResult tr=null;

    public Test(String name,String durationMinutes,int id,int isEnabled,int classId,int packageId,TestPaper tp){

    }

    public void setTestStatus(TestStatus ts){
        this.ts=ts;

    }

    public void setTestResult(TestResult tr){
        this.tr=tr;

    }




}

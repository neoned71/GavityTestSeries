package org.gravityclasses.gavitytestseries;

public class TestThumbnail {
    String name,durationMinutes;
    int id,isEnabled=1,classId,packageId,tpId;
    TestStatus ts=null;
    TestResult tr=null;

    public TestThumbnail(String name, String durationMinutes, int id, int isEnabled, int classId, int packageId,int tpId){
        this.name=name;
        this.durationMinutes=durationMinutes;
        this.id=id;
        this.isEnabled=isEnabled;
        this.classId=classId;
        this.packageId=packageId;
        this.tpId=tpId;
    }

    public void setTestStatus(TestStatus ts){
        this.ts=ts;

    }

    public void setTestResult(TestResult tr){
        this.tr=tr;

    }




}

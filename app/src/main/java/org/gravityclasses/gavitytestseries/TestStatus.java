package org.gravityclasses.gavitytestseries;

import java.util.Vector;

class TestStatus {
    Vector<QStatus> status;
    int testId,studentId;
    String lastRegisteredTime;

    public TestStatus(Vector status,int studentId,int testId,String lastRegisteredTime){
        this.status=status;
        this.studentId=studentId;
        this.testId=testId;
        this.lastRegisteredTime=lastRegisteredTime;


    }
}

package org.gravityclasses.gavitytestseries;

import java.util.Vector;

class TestResult {
    Vector<QResult> result;
    int testId,marksObtained,qAttempted,qNegative,qPositive,rank;

    public TestResult(Vector result,int testId,int marksObtained, int qAttempted,int qNegative,int qPositive,int rank){
        this.result=result;
        this.testId=testId;
        this.marksObtained=marksObtained;
        this.qAttempted=qAttempted;
        this.qNegative=qNegative;
        this.qPositive=qPositive;
        this.rank=rank;
    }
}

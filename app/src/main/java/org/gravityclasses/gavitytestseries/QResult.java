package org.gravityclasses.gavitytestseries;

class QResult {
    int question_id,time,choice,correctAns,marksObtained;
    String subject;
    public QResult(int question_id,int time,int choice,int correctAns,int marksObtained,String subject){
        this.subject=subject;
        this.question_id=question_id;
        this.time=time;
        this.choice=choice;
        this.correctAns=correctAns;
        this.marksObtained=marksObtained;
    }

}

package org.gravityclasses.gavitytestseries;

import java.util.Random;

public class QuestionResult {
    int qId,time,marks;
    boolean isCorrect;
    char correctChoice,markedChoice;
    String qBody,qSolution;

    public QuestionResult(int qId,int time,int marks,boolean isCorrect,char correctChoice,char markedChoice,String qBody,String qSolution)
    {
        this.qId=qId;
        this.time=time;
        this.marks=marks;
        this.isCorrect=isCorrect;
        this.correctChoice=correctChoice;
        this.markedChoice=markedChoice;
        this.qBody=qBody;
        this.qSolution=qSolution;
    }

    public QuestionResult()
    {
        Random rand=new Random();
        this.qId=1;
        this.time=rand.nextInt(30);
        this.marks=rand.nextInt(30);
        this.isCorrect=rand.nextBoolean();
        this.correctChoice='A';
        this.markedChoice='B';
        this.qBody="some random stuff";
        this.qSolution="and some totally random stuff again";
    }
}

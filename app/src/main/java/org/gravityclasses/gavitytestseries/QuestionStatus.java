package org.gravityclasses.gavitytestseries;

public class QuestionStatus {
    public int choice,time,visited,attempted;

    public QuestionStatus(int choice,int time,int visited){
        this.choice=choice;
        this.time=time;
        this.visited=visited;
    }
    public QuestionStatus(int choice,int time,int visited,int attempted){
        this.choice=choice;
        this.time=time;
        this.visited=visited;
        this.attempted=attempted;
    }
}

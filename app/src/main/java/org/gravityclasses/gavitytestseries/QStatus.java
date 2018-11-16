package org.gravityclasses.gavitytestseries;

class QStatus {
    int choice=0,time=0,visited=0,attempted=0;

    public QStatus(int choice,int time,int visited,int attempted)
    {
        this.choice=choice;
        this.attempted=attempted;
        this.time=time;
        this.visited=visited;
    }

    public QStatus()
    {

    }
}

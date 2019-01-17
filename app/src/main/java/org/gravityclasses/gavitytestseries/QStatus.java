package org.gravityclasses.gavitytestseries;

class QStatus {
    int time=0,visited=0,attempted=0;
    String choice;

    public QStatus(String choice,int time,int visited,int attempted)
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

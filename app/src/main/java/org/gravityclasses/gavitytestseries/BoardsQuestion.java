package org.gravityclasses.gavitytestseries;

public class BoardsQuestion extends Question {


    public BoardsQuestion(int id, String question, String subject, String topic, String difficulty, String marking, String correctAns, Solution s)
    {
        this.question=question;
        this.id=id;
        this.subject=subject;
        this.topic=topic;
        this.difficulty=difficulty;
        this.marking=marking;
        this.correctAns=correctAns;
        this.solution=s;
        this.questionType=9;
    }
}

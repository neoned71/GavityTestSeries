package org.gravityclasses.gavitytestseries;

import java.util.Vector;

public class TrueFalseQuestion extends Question {
    public Vector<QOption> options;


    public TrueFalseQuestion(int id, String question, String image, String subject, String topic, String difficulty, String marking, String correctAns, Solution s)
    {
        this.image=image;
        this.question=question;
        this.id=id;
        this.subject=subject;
        this.topic=topic;
        this.difficulty=difficulty;
        this.marking=marking;
        this.correctAns=correctAns;
        this.solution=s;
        this.questionType=5;
        options=new Vector<>();
        options.add(new QOption("text","True"));
        options.add(new QOption("text","False"));

    }
}

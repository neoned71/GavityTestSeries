package org.gravityclasses.gavitytestseries;

import java.util.Vector;

public class MatrixTwoQuestion extends Question {
    public Vector<QOption> options;
    Vector<QuestionItem> qi;


    public MatrixTwoQuestion(int id,Vector<QuestionItem> qi, String subject, String topic, String difficulty, String marking, String correctAns,Solution s)
    {
        this.qi=qi;
        this.id=id;
        this.subject=subject;
        this.topic=topic;
        this.difficulty=difficulty;
        this.marking=marking;
        this.correctAns=correctAns;
        this.options=options;
        this.solution=s;
        this.questionType=4;
        options=new Vector<>();
        String[] op={"A~S, B~R, C~Q, D~P","A~Q, B~S, C~R, D~P","A~R, B~S, C~P, D~Q","A~R, B~S, C~P, D~R"};
        for (String d:op)
        {
            options.add(new QOption("text",d));
        }

    }
}

package org.gravityclasses.gavitytestseries;

import java.util.Vector;

public class AssertionReasonQuestion extends Question {
    public Vector<QOption> options;
    public QuestionItem assertion,reason;


    public AssertionReasonQuestion(int id, QuestionItem a,QuestionItem r, String subject, String topic, String difficulty, String marking, String correctAns, Solution s)
    {
        this.assertion=a;
        this.reason=r;
        this.id=id;
        this.subject=subject;
        this.topic=topic;
        this.difficulty=difficulty;
        this.marking=marking;
        this.correctAns=correctAns;

        this.solution=s;
        this.questionType=8;
        options=new Vector<>();
        String[] op={"Assertion is true, Reason is true Reason is a correct explanation for Assertion","Assertion is true, Reason is true, Reason is not a correct explanation for Assertion","Assertion is true, Reason is False","Assertion is False, reason is true"};
        for (String d:op)
        {
            options.add(new QOption("text",d));
        }

    }
}

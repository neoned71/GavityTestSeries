package org.gravityclasses.gavitytestseries;

import java.util.Vector;

public class ComprehensionQuestion extends Question {
    public Vector<QOption> options;
    public Comprehension comprehension;


    public ComprehensionQuestion(int id, String question, String image, String subject, String topic, String difficulty, String marking, String correctAns, Vector options, Solution s,Comprehension c)
    {
        this.image=image;
        this.question=question;
        this.id=id;
        this.subject=subject;
        this.topic=topic;
        this.difficulty=difficulty;
        this.marking=marking;
        this.correctAns=correctAns;
        this.options=options;
        this.solution=s;
        this.questionType=3;
        this.comprehension=c;
    }
}

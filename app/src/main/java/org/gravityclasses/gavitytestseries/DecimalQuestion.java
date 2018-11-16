package org.gravityclasses.gavitytestseries;

public class DecimalQuestion extends Question {


    public DecimalQuestion(int id, String question,String image, String subject, String topic, String difficulty, String marking, String correctAns, Solution s)
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
        this.questionType=11;


    }
}

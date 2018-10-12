package org.gravityclasses.gavitytestseries;

import java.util.Vector;

class Question {
    String question,subject,topic,difficulty,marking;
    Vector<QOption> options;
    Solution solution;
    int id,correctAns;

    public Question(int id,String question, String subject, String topic, String difficulty, String marking, int correctAns, Vector options,Solution s)
    {
        this.question=question;
        this.id=id;
        this.subject=subject;
        this.topic=topic;
        this.difficulty=difficulty;
        this.marking=marking;
        this.correctAns=correctAns;
        this.options=options;
        this.solution=s;
    }

}

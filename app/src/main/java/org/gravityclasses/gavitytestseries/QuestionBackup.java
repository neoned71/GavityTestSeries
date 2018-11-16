package org.gravityclasses.gavitytestseries;

import java.util.Vector;

class QuestionBackup {
    public String question,subject,topic,difficulty,marking;
    public Vector<QOption> options;
    public Solution solution;
    public int questionType;
    public int id,correctAns;

    public QuestionBackup(int id, String question, String subject, String topic, String difficulty, String marking, int correctAns, Vector options, Solution s)
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

package org.gravityclasses.gavitytestseries;

import java.util.Vector;

class TestPaper {
    String instructions_pdf,date;
    Vector<Question> questions;
    int totalMarks;
    public TestPaper(String instructions_pdf,String date, int totalMarks,Vector<Question> questions)
    {
        this.instructions_pdf=instructions_pdf;
        this.date=date;
        this.totalMarks=totalMarks;
        this.questions=new Vector(questions);

    }
}

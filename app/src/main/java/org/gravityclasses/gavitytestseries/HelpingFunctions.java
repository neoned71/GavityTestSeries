package org.gravityclasses.gavitytestseries;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;



public class HelpingFunctions extends Application{
    private Typeface normalFont;
    private Typeface boldFont;


    @Override
    public void onCreate() {
        super.onCreate();


    }

    public void setTypeface(TextView textView) {
        if(textView != null) {
            if(textView.getTypeface() != null && textView.getTypeface().isBold()) {
                textView.setTypeface(getBoldFont());
            } else {
                textView.setTypeface(getNormalFont());
            }
        }
    }

    public boolean addPreference(Activity a, String key, String value)
    {
        SharedPreferences sp;
        sp=a.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor spe=sp.edit();
        spe.putString(key,value);
        return spe.commit();
    }

    public String getPreference(Activity a,String key)
    {
        SharedPreferences sp;
        sp=a.getPreferences(MODE_PRIVATE);
        return sp.getString(key,"");
    }


    public boolean removePreference(Activity a, String key){
        SharedPreferences sp;
        sp=a.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor spe=sp.edit();
        spe.remove(key);
        return spe.commit();
    }

    public boolean checkLogin(Activity a)
    {
        String response=getPreference(a,Constants.KEY_USER_JSON);
        if(response.equals("") && Constants.user==null)
        {
            return false;
        }

        if(!response.equals("") && Constants.user==null)
        {
            return login(a,response);
        }
        return true;
    }

    public boolean logout(Activity a)
    {
        Constants.user=null;
        return removePreference(a,"userJson");
    }


    public boolean login(Activity a,String response) {
        try {
            JSONObject jso=new JSONObject(response);
            JSONObject userJson= jso.getJSONObject("user");
//             gender,String dob,int packageId,int classId,int studentId
            JSONObject classInfo= userJson.getJSONObject("class");
//            String name,String className, String stream, String programName

            ClassInfo ci=new ClassInfo(classInfo.getString("name"),classInfo.getString("class"),classInfo.getString("stream"),classInfo.getString("program_name"));
            Constants.user=new User(ci,userJson.getString("name"),userJson.getString("pic_path"),userJson.getString("phone"),userJson.getString("email"),
                    userJson.getString("gender"),
                    userJson.getString("date_of_birth"),
                    userJson.getInt("package_id"),
                    userJson.getInt("class_id"),
                    userJson.getInt("id"));

            addPreference(a,Constants.KEY_USER_JSON,response);

            return true;

        } catch (JSONException e) {
//            Toast.makeText(a,"JSON object problem"+e.getMessage(),Toast.LENGTH_LONG).show();
            Log.i(a.toString(),"JSON object problem");
            e.printStackTrace();
            return false;
        }

    }

    public void abcdef(int a) throws Exception{
        if(a==0)
        {
            throw new ArithmeticException();
        }

    }

    public boolean checkConnectivity(){
        String DEBUG_TAG = "NetworkStatusExample";

        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }
        Log.d(DEBUG_TAG, "Wifi connected: " + isWifiConn);
        Log.d(DEBUG_TAG, "Mobile connected: " + isMobileConn);

        return (isWifiConn | isMobileConn);
    }

    public Test createTestFromJson(JSONObject test) throws JSONException {
        Test t=null;
        JSONObject testStatus;
        JSONObject testResult;
        JSONObject testPaper=test.getJSONObject("test_paper");
        if(!test.isNull("test_status"))
        {
             testStatus=test.getJSONObject("test_status");
        }
        else
        {
            testStatus=null;
        }

        if(!test.isNull("test_result"))
        {
            testResult=test.getJSONObject("test_result");
        }
        else
        {
            testResult=null;
        }



        if(testPaper!=null)
        {
            TestPaper tp=createTestPaperFromJson(testPaper);
            int classId=test.getInt("class_id");
            int id=test.getInt("id");
            String name=test.getString("name");
            int packageId=test.getInt("package_id");
            String date=test.getString("date");
            String durationMinutes=test.getString("duration_minutes");
            t=new Test(name,durationMinutes,id,test.getInt("is_enabled"),classId,packageId,tp);
            if(testStatus!=null)
            {
                t.setTestStatus(createTestStatusFromJson(testStatus));
            }
            if(testResult!=null)
            {
                t.setTestResult(createTestResultFromJson(testResult));
            }
        }
        return t;
    }
//
//    public Test createTestResultFromJson(JSONObject test) throws JSONException {
//        Test t=null;
//        JSONObject testPaper=test.getJSONObject("test_paper");
//        JSONObject testStatus=test.getJSONObject("test_status");
//        JSONObject testResult=test.getJSONObject("test_result");
//        if(testPaper!=null)
//        {
//            TestPaper tp=createTestPaperFromJson(testPaper);
//            int classId=test.getInt("classId");
//            int id=test.getInt("id");
//            String name=test.getString("name");
//            int packageId=test.getInt("package_id");
//            String date=test.getString("date");
//            String durationMinutes=test.getString("duration_minutes");
//            t=new Test(name,durationMinutes,id,test.getInt("is_enabled"),classId,packageId,tp);
//            if(testStatus!=null)
//            {
//                t.setTestStatus(createTestStatusFromJson(testStatus));
//            }
//            if(testResult!=null)
//            {
//                t.setTestResult(createTestResultFromJson(testResult));
//            }
//        }
//
//
//        return t;
//
//
//    }


    public Vector<TestThumbnail> createTestThumbnailsFromJson(JSONArray tests) throws JSONException {
        TestThumbnail t=null;
        JSONObject testStatus,testResult;

        Vector<TestThumbnail> vTest=new Vector<>();

       for(int x=0;x<tests.length();x++)
       {
           JSONObject test=tests.getJSONObject(x);
           int tpId=tests.getJSONObject(x).getInt("test_paper_id");
           if(!test.isNull("test_status"))
           {
               testStatus=test.getJSONObject("test_status");
           }
           else
           {
               testStatus=null;
           }

           if(!test.isNull("test_result"))
           {
               testResult=test.getJSONObject("test_result");
           }
           else
           {
               testResult=null;
           }
           int classId=test.getInt("class_id");
           int id=test.getInt("id");
           String name=test.getString("name");
           int packageId=test.getInt("package_id");

           String durationMinutes=test.getString("duration_minutes");
           t=new TestThumbnail(name,durationMinutes,id,1,classId,packageId,tpId);
           if(testStatus!=null)
           {
               t.setTestStatus(createTestStatusFromJson(testStatus));
           }
           if(testResult!=null)
           {
               t.setTestResult(createTestResultFromJson(testResult));
           }

           vTest.add(t);
       }


        return vTest;
       }
    public TestStatus createTestStatusFromJson(JSONObject testStatus) throws JSONException {
        int studentId = testStatus.getInt("student_id");
        int testId = testStatus.getInt("test_id");
        Vector<QStatus> statuses=new Vector<>();
        JSONArray sArray=testStatus.getJSONArray("test_status");
        for(int x =0; x < sArray.length();x++)
        {
            int choice=sArray.getJSONObject(x).getInt("choice");
            int time=sArray.getJSONObject(x).getInt("time");
            int visited=sArray.getJSONObject(x).getInt("visited");

            statuses.add(new QStatus(choice,time,visited));
        }
        String lrt=testStatus.getString("lrt");

        return new TestStatus(statuses,studentId,testId,lrt);
    }

    public TestResult createTestResultFromJson(JSONObject testResult) throws JSONException {
        int marksObtained;
        int qAttempted = testResult.getInt("questions_attempted");
        int qNegative = testResult.getInt("questions_negative");
        int qPositive = testResult.getInt("questions_positive");
        int rank = testResult.getInt("rank");

        int testId = testResult.getInt("test_id");
        int studentId = testResult.getInt("student_id");
        Vector<QResult> statuses=new Vector<>();
        JSONArray sArray=testResult.getJSONArray("result_json");
        for(int x =0; x < sArray.length();x++)
        {
            int qId=sArray.getJSONObject(x).getInt("question_id");
            int time=sArray.getJSONObject(x).getInt("time");
            int choice=sArray.getJSONObject(x).getInt("choice");
            int correctAns=sArray.getJSONObject(x).getInt("correct_ans");
            marksObtained=sArray.getJSONObject(x).getInt("marks_obtained");
            String subject = sArray.getJSONObject(x).getString("subject");

            statuses.add(new QResult(qId,time,choice,correctAns,marksObtained,subject));
        }
        marksObtained = testResult.getInt("marks_obtained");
        //String date=testResult.getString("date");

        return new TestResult(statuses,studentId,testId,marksObtained,qAttempted,qNegative,qPositive,rank);
    }

    public TestPaper createTestPaperFromJson(JSONObject testPaper) throws JSONException {
//        String instructions_pdf,String date, int totalMarks,Vector questions)
        Vector<Question> questions=new Vector<>();
        JSONArray qArray=testPaper.getJSONArray("questions");
        for(int i=0;i<qArray.length();i++)
        {
            JSONObject question=qArray.getJSONObject(i);
            questions.add(createQuestionSingleFromJson(question));
        }

        String date=testPaper.getString("date");
        String instructionsPdf=testPaper.getString("instructions_pdf");
        int totalMarks=testPaper.getInt("total_marks");
        return new TestPaper(instructionsPdf,date,totalMarks,questions);

    }

    public Solution createSolutionFromJson(JSONObject solution) throws JSONException {
        Solution s= new Solution(solution.getString("text"),solution.has("image")?solution.getString("image"):null,solution.has("link")?solution.getString("link"):null);
        return s;
    }

    public Question createQuestionSingleFromJson(JSONObject question) throws JSONException {
        JSONObject option=null;
        Vector<QOption> vOptions=new Vector<>();

            JSONArray options=question.getJSONArray("options");
            for(int i=0;i<options.length();i++)
            {
                option=options.getJSONObject(i);
                vOptions.add(createOptionFromJson(option));
            }
            Solution s=createSolutionFromJson(question.getJSONObject("solution"));

//        String question, String subject, String topic, String difficulty, String marking, int correctAns, Vector options,Solution s
            Question qo=new Question(question.getInt("id"),question.getString("question"),question.getString("subject"),
                    question.getString("topic"),
                    question.getString("difficulty"),
                    question.getString("marking"),
                    question.getInt("correct_ans"),vOptions,s);
            return qo;

    }

    public QOption createOptionFromJson(JSONObject option) throws JSONException {

            QOption qo=new QOption(option.getString("type"),option.getString("value"));
            return qo;


    }


    private Typeface getNormalFont() {
        if(normalFont == null) {
            normalFont = Typeface.createFromAsset(getAssets(),"fonts/my_font.ttf");
        }
        return this.normalFont;
    }

    private Typeface getBoldFont() {
        if(boldFont == null) {
            boldFont = Typeface.createFromAsset(getAssets(),"fonts/my_font_bold.ttf");
        }
        return this.boldFont;
    }
}

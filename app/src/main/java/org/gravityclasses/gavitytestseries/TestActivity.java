package org.gravityclasses.gavitytestseries;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class TestActivity extends AppCompatActivity {

    Vector<QStatus> status=null;
    Test test;
    int currentQ=0;
    TestStatus ts;
    Vector<QStatus> statuses;
    String timeString;
    int testId;
    RequestQueue mRequestQueue;
    HelpingFunctions hf;
    String TAG="TestActivity";
    QuestionPagerAdapter qpa;
    QuestionViewPager questionPager;
    Thread cachingThread,timeThread;
    boolean initialized=false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hf=(HelpingFunctions)getApplication();
        if(!hf.checkLogin(this))
        {
            Intent i=new Intent(this,LoginActivity.class);
            startActivity(i);
            Log.i(TAG,"finishing up");
            finish();
        }
        testId=getIntent().getIntExtra("testId",0);
        if(testId==0)
        {
            Intent i = new Intent(this,Dashboard.class);
            startActivity(i);
        }
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;

        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_test_page);
        PagerTabStrip pager_header=findViewById(R.id.pager_header);
        pager_header.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        pager_header.setTextColor(Color.WHITE);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        makeRequestTest(7,3);
    }

    class Caching implements Runnable{
        @Override
        public void run() {
            try {
                while(true)
                {
                    doBackup();
                    Thread.sleep(30000,0);

                }

            } catch (InterruptedException e) {
//                e.printStackTrace();

            }
        }
    }


    class Timing implements Runnable{
        @Override
        public void run() {
            try {
                while(true)
                {
                    Thread.sleep(1000,0);
                    tickTock();
                }

            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
        }
    }

    private void tickTock() {

        statuses=ts.status;
        QStatus qs=statuses.get(currentQ);
        qs.time++;

        int min=Integer.parseInt(timeString.split(":")[0]);
        int sec=Integer.parseInt(timeString.split(":")[1]);

        if(sec==0)
        {
            sec=59;
            min--;
            if(min<0)
            {
                submitPaper();
            }
        }
        else
        {
            sec--;
        }

        timeString = min+":"+sec;

        displayTime();

    }



    private void submitPaper(){

    }



    public void setTime(int min)
    {
        timeString = min+":00";
        displayTime();
    }

    public void displayTime()
    {
        TextView tv= findViewById(R.id.time);
        tv.setText(timeString);
    }

    public void startTimeThread()
    {
        Log.i(TAG,"time thread starting");
        if(timeThread==null || !timeThread.isAlive())
        {
            timeThread = new Thread(new Timing());
            timeThread.start();
        }
        else
        {
            Log.i(TAG,"time thread already running");
        }
    }

    public void startCachingThread()
    {
        Log.i(TAG,"cache thread starting");
        if(cachingThread==null || !cachingThread.isAlive())
        {
            cachingThread = new Thread(new Caching());
            cachingThread.start();
        }
        else
        {
            Log.i(TAG,"cache thread already running");
        }


    }

    public void stopTimeThread()
    {
        if(timeThread.isAlive())
        {
            timeThread.interrupt();
        }
        else
        {
            Log.i(TAG,"time thread already dead");
        }
    }

    public void stopCachingThread()
    {
        if(cachingThread.isAlive())
        {
            cachingThread.interrupt();
        }
        else
        {
            Log.i(TAG,"cache thread already dead");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(initialized)
        {
            startCachingThread();
            startTimeThread();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(initialized)
        {
            stopCachingThread();
            stopTimeThread();
        }

    }

    private void doBackup(){
        String status=getStatus();
        if(status!=null)
        {
            makeRequestCache(testId,Constants.user.studentId,status,timeString);
        }
        else
        {
            Log.i(TAG,"status failed");
        }


    }


    public String getStatus(){
        if(initialized)
        {
            statuses=ts.status;
            Gson status=new Gson();

            String s=status.toJson(statuses);

            Log.i(TAG,s);
            return s;
        }
        else{
            return null;
        }

    }

    public void initializeHorizontalList()
    {

    }


    public void changeQuestion(View v)
    {
        Toast.makeText(this,"hello",Toast.LENGTH_LONG).show();
    }



    public void nextPager(View v)
    {
        questionPager.setCurrentItem(questionPager.getCurrentItem()+1,true);
        currentQ= questionPager.getCurrentItem();
    }

    public void prevPager(View v)
    {
        questionPager.setCurrentItem(questionPager.getCurrentItem()-1,true);
        currentQ= questionPager.getCurrentItem();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }



    public void makeRequestTest(final int testId,final int studentId){
        // Instantiate the RequestQueue.
        mRequestQueue = Volley.newRequestQueue(this);
        String url =Constants.GET_TEST_URL;
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG,response);
                        handleTest(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TestActivity.this,"network response failure:"+error.networkResponse.statusCode,Toast.LENGTH_LONG).show();

            }

        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("test_id",testId+"");
                params.put("student_id",studentId+"");


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }};

// Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);
    }

    public void makeRequestCache(final int testId,final int studentId,final String testStatus,final String lrt){
        // Instantiate the RequestQueue.
        mRequestQueue = Volley.newRequestQueue(this);
        String url =Constants.SAVE_TEST_STATUS_URL;
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.i(TAG,response);
                        handleCache(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TestActivity.this,"network response failure:"+error.networkResponse.statusCode,Toast.LENGTH_LONG).show();

            }

        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("test_id",testId+"");
                params.put("student_id",studentId+"");
                params.put("test_status",testStatus);
                params.put("last_registered_time",lrt+"");


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }};

// Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);
    }

    private void handleCache(String response) {
        //Log.i(TAG,response);
        try {
            JSONObject res=new JSONObject(response);
            if(res.getString("status").equals("success"))
            {
                //Log.i(TAG,res.getString("status"));
                //setError("submit successful");
            }
            else
            {

                Log.i(TAG,"cache save failed");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void makeRequestSubmit(final int testId,final int studentId,final String testStatus){
        // Instantiate the RequestQueue.
        mRequestQueue = Volley.newRequestQueue(this);
        String url =Constants.SAVE_TEST_RESULT_URL;
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG,response);
                        handleSubmit(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TestActivity.this,"network response failure:"+error.networkResponse.statusCode,Toast.LENGTH_LONG).show();

            }

        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("test_id",testId+"");
                params.put("student_id",studentId+"");
                params.put("test_status",testStatus);


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }};

// Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);
    }

    private void handleSubmit(String response) {
        Log.i(TAG,response);
        try {
            JSONObject res=new JSONObject(response);
            if(res.getString("status").equals("success"))
            {
                Log.i(TAG,"result submit successful");
                setError("submit successful");
            }else
            {
                setError("some error has occurred in submitting the result");
                Log.i(TAG,"test get failed");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleTest(String response) {
        Log.i("escapedResponse",response);
        try {
            JSONObject res=new JSONObject(response);
            if(res.getString("status").equals("success"))
            {
                JSONObject test=res.getJSONObject("test");
                this.test=hf.createTestFromJson(test);

                if(this.test.tr!=null)
                {
                    Toast.makeText(this,"This test is already submitted by you",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(this,ResultActivity.class);
                    i.putExtra("testId",this.test.id);
                    startActivity(i);
                }
                this.ts=this.test.ts;
                if(this.ts==null)
                {
                    initializeNullStatus();
                }
                Vector<Question> questions = this.test.tp.questions;
                Log.i(TAG,"handleTest");
//
                doInitializePaper();
                //initializeCharts(testResult.tr.qPositive,testResult.tr.qNegative,testResult.tr.qAttempted);
            }
            else
            {
                setError("some error has occurred in getting the test");
                Log.i(TAG,"test get failed");
            }

        } catch (Exception e) {
            //Toast.makeText(this,"json conversion problem dashboard"+e.getMessage(),Toast.LENGTH_LONG).show();
            Log.i("testError",e.getMessage());
            e.printStackTrace();
        }
    }

    private void doInitializePaper() {
        questionPager = findViewById(R.id.questions_pager);
        qpa=new QuestionPagerAdapter(this,test);
        questionPager.setAdapter(qpa);
        questionPager.setOffscreenPageLimit(1);
        Log.i(TAG,"doinitialize");


        setTime(Integer.parseInt(test.durationMinutes));
        startCachingThread();
        startTimeThread();
        //stopTimer();
        //stopCaching();
        initialized=true;

    }

    private void initializeNullStatus() {
        int l=this.test.tp.questions.size();
        Vector<QStatus> qs=new Vector<>(l);
        for(int x =0; x<l; x++){
            qs.add(new QStatus());
        }

        int studentId=Constants.user.studentId;

        this.ts=new TestStatus(qs,studentId,testId,"");

        this.test.ts=this.ts;



    }

    private void setError(String str) {
    }


}

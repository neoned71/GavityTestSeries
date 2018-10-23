package org.gravityclasses.gavitytestseries;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;



import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class TestActivity extends AppCompatActivity {

    Vector<QStatus> status=null;
    Test test;
    TestStatus ts;
    String timeString;
    int testId;
    RequestQueue mRequestQueue;
    HelpingFunctions hf;
    String TAG="TestActivity";




    class Caching implements Runnable{
        HelpingFunctions hf;
        int time=0;
        Context c;
        public Caching(HelpingFunctions hf,Context c)
        {
            this.hf=hf;
            this.c=c;
        }

        public void setTime(int time) {
            this.time = time;
        }

        @Override
        public void run() {
            try {
                while(true)
                {
                    Thread.sleep(300000,0);
                    doBackup();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Timing implements Runnable{
        HelpingFunctions hf;
        int time=0;
        Context c;
        public Timing(HelpingFunctions hf,Context c)
        {
            this.hf=hf;
            this.c=c;
        }

        public void setTime(int time) {
            this.time = time;
        }

        @Override
        public void run() {
            try {
                while(true)
                {
                    Thread.sleep(300000,0);
                    doBackup();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




    private void doBackup(){

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hf=(HelpingFunctions)getApplication();
//        if(!hf.checkLogin(this))
//        {
//            Intent i=new Intent(this,LoginActivity.class);
//            startActivity(i);
//            Log.i(TAG,"finishing up");
//            finish();
//        }
        testId=getIntent().getIntExtra("testId",0);
//        if(testId==0)
//        {
//            Intent i = new Intent(this,Dashboard.class);
//            startActivity(i);
//        }
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;

        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_test_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    public void changeQuestion(View v)
    {
        Toast.makeText(this,"hello",Toast.LENGTH_LONG).show();
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

    private void handleTest(String response) {
        Log.i("escapedResponse",response);
        try {
            JSONObject res=new JSONObject(response);
            JSONObject test=res.getJSONObject("test");
            this.test=hf.createTestFromJson(test);
            Vector<QResult> results=this.test.tr.result;
            if(results!=null)
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
        } catch (Exception e) {
            //Toast.makeText(this,"json conversion problem dashboard"+e.getMessage(),Toast.LENGTH_LONG).show();
            Log.i("testError",e.getMessage());
            e.printStackTrace();
        }
    }

    private void doInitializePaper() {
        Vector<Question> questions = test.tp.questions;


//        startTimer();
//        startCaching();
        //stopTimer();
        //stopCaching();

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

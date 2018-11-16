package org.gravityclasses.gavitytestseries;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

public class ResultActivity extends AppCompatActivity {
ArrayList<QuestionResult> arr;
QuestionExpandableListViewAdapter qelv;
ExpandableListView elv;
String TAG="ResultActivity";
int testId;
Test testResult;
    RequestQueue mRequestQueue;
    HelpingFunctions hf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
//        testId=7;
//        Random r = new Random();
//        r.setSeed(20);
//        r.nextInt();
        //Toast.makeText(this,r.nextInt()+"",Toast.LENGTH_LONG).show();
        hf=(HelpingFunctions)getApplication();
        testId=getIntent().getIntExtra("testId",0);
        if(testId==0)
        {
            Intent i = new Intent(this,Dashboard.class);
            startActivity(i);

        }
        Toast.makeText(this,"result",Toast.LENGTH_LONG).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //initializeCharts(2f,4f,3f);
        NavigationView nv=findViewById(R.id.nav_view);
        ImageView imageview = nv.findViewById(R.id.profile_pic);
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        imageview.setColorFilter(filter);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        makeRequestTest(testId,Constants.user.studentId);
        makeRequestTest(testId,Constants.user.studentId);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                                                                                                                                                                                                        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void logout(View v)
    {
        ((HelpingFunctions)getApplication()).logout(this);
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        this.finish();
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
                Toast.makeText(ResultActivity.this,"network response failure:"+error.networkResponse.statusCode,Toast.LENGTH_LONG).show();

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
        stringRequest.setShouldCache(false);
            mRequestQueue.add(stringRequest);
    }

    private void handleTest(String response) {
        Log.i("escapedResponse",response);
        try {
            JSONObject res=new JSONObject(response);
            JSONObject test=res.getJSONObject("test");
            testResult=hf.createTestFromJson(test);
            Vector<QResult> results=testResult.tr.result;
            Vector<Question> questions = testResult.tp.questions;
            Log.i(TAG,"handleTest");
            ArrayList<QuestionResult> arr=new ArrayList<>();
            if(results.size()!=questions.size())
            {
                setError("improper data from server");
            }
            else
            {
                for(int x = 0; x < results.size() ; x++)
                {
                    arr.add(new QuestionResult(results.get(x),questions.get(x)));
                }
            }
            doInitializeListing(arr);
            initializeCharts(testResult.tr.qPositive,testResult.tr.qNegative,testResult.tr.qAttempted);
        } catch (Exception e) {
            //Toast.makeText(this,"json conversion problem dashboard"+e.getMessage(),Toast.LENGTH_LONG).show();
            Log.i("testError",e.getMessage());
            e.printStackTrace();
        }
    }



    private void doInitializeListing(ArrayList<QuestionResult> t) {
        Log.i("xyz123","sad");
        elv=findViewById(R.id.questions_exp_list_view);
        final int[] prevExpandPosition = {-1};
        elv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (prevExpandPosition[0] >= 0 && prevExpandPosition[0] != groupPosition) {
                    elv.collapseGroup(prevExpandPosition[0]);
                }
                prevExpandPosition[0] = groupPosition;
            }
        });
        arr=t;
        qelv=new QuestionExpandableListViewAdapter(this,arr);
        elv.setAdapter(qelv);

        }


    public void setError(String s)
    {
        Toast.makeText(hf, s, Toast.LENGTH_SHORT).show();
    }


    public void initializeCharts(float a,float b,float c){
        Log.i("ResultsActivity","initializing charts");
        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        yvalues.add(new PieEntry(a, "Wrong"));
        yvalues.add(new PieEntry(b, "Correct"));
        yvalues.add(new PieEntry(c, "Not Attempted"));

        ArrayList<String> xVals = new ArrayList<String>();
        PieDataSet dataSet1 = new PieDataSet(yvalues, "");

        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.argb(255,200,50,50));
        colors.add(Color.argb(255,0,100,100));
        colors.add(Color.DKGRAY);
        dataSet1.setColors(colors);
        dataSet1.setValueTextColor(Color.WHITE);
        dataSet1.setValueTextSize(10);
        PieData data = new PieData(dataSet1);
        PieChart pc=findViewById(R.id.pie_chart);
        pc.getDescription().setEnabled(false);
        pc.setData(data);
        pc.setHoleColor(Color.WHITE);
        pc.setHoleRadius(20);
        pc.setTransparentCircleRadius(30);
        pc.setDrawEntryLabels(false);
        pc.setCenterText("B+");
    }



}

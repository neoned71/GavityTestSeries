package org.gravityclasses.gavitytestseries;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Dashboard extends AppCompatActivity {
    String TAG="DashBoard";
    StringRequest stringRequest; // Assume this exists.
    RequestQueue mRequestQueue;

    ArrayList<TestThumbnail> list;
    TestListAdapter tla;
    HelpingFunctions hf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hf=(HelpingFunctions)getApplication();

        setContentView(R.layout.activity_dashboard);

        NavigationView nv=findViewById(R.id.nav_view);
//        View navHeader=nv.getHeaderView(0);

        ImageView imageview = nv.findViewById(R.id.profile_pic);
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        imageview.setColorFilter(filter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if(hf.checkConnectivity() && hf.checkLogin(this))
        {
            makeRequestTests(Constants.user.classId,Constants.user.packageId,Constants.user.studentId);
        }
        else if(!hf.checkLogin(this))
        {
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }


    public void logout(View v)
    {
        ((HelpingFunctions)getApplication()).logout(this);
        this.finish();
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



    public void go(View v)
    {
        TestListAdapter.ViewHolder vh= (TestListAdapter.ViewHolder) v.getTag();
        if(vh.type==0)
        {
            Intent i = new Intent(this,TestActivity.class);
            i.putExtra("testId",vh.testId);
            startActivity(i);
        }
        else
        {
            Intent i = new Intent(this,ResultActivity.class);
            i.putExtra("testId",vh.testId);
            startActivity(i);

        }
        //Toast.makeText(this,"yes",Toast.LENGTH_LONG).show();
    }






    public void makeRequestTests(final int classId, final int packageId, final int studentId){
        // Instantiate the RequestQueue.
        mRequestQueue = Volley.newRequestQueue(this);
        String url =Constants.GET_TESTS_URL;
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.i(TAG,response);
                        handleTests(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Dashboard.this,"network response failure:"+error.networkResponse.statusCode,Toast.LENGTH_LONG).show();
            }

        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("class_id",classId+"");
                params.put("package_id",packageId+"");
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

    private void handleTests(String response) {
        try {
            JSONObject res=new JSONObject(response);
            JSONArray tests=res.getJSONArray("tests");
            Vector<TestThumbnail> tt=hf.createTestThumbnailsFromJson(tests);
            Toast.makeText(this,tt.size()+"",Toast.LENGTH_LONG).show();
            list=new ArrayList<>();
            for(TestThumbnail tto:tt)
            {
                list.add(tto);
                tla=new TestListAdapter(this,list);
                ListView tlv=findViewById(R.id.test_list);
                tlv.setAdapter(tla);
            }

        } catch (JSONException e) {
            Toast.makeText(this,"json conversion problem dashboard"+e.getMessage(),Toast.LENGTH_LONG).show();
            Log.i("testError",e.getMessage());
            e.printStackTrace();
        }
    }
}

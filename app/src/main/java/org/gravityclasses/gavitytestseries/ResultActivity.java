package org.gravityclasses.gavitytestseries;

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
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
ArrayList<QuestionResult> arr;
QuestionExpandableListViewAdapter qelv;
ExpandableListView elv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toast.makeText(this,"result",Toast.LENGTH_LONG).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeCharts(2f,4f,3f);
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


        elv=findViewById(R.id.questions_exp_list_view);
        arr=new ArrayList<>();
        arr.add(new QuestionResult());
        arr.add(new QuestionResult());
        arr.add(new QuestionResult());
        arr.add(new QuestionResult());
        arr.add(new QuestionResult());
        arr.add(new QuestionResult());
        arr.add(new QuestionResult());
        arr.add(new QuestionResult());
        arr.add(new QuestionResult());
        arr.add(new QuestionResult());
        arr.add(new QuestionResult());
        arr.add(new QuestionResult());
        qelv=new QuestionExpandableListViewAdapter(this,arr);

        elv.setAdapter(qelv);


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
        colors.add(Color.GREEN);
        colors.add(Color.argb(255,0,100,100));


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


//        PieDataSet pds=new PieDataSet(a,"Marks Distribution!");

    }



}

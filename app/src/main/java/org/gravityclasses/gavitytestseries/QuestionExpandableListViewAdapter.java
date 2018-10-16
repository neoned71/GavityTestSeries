package org.gravityclasses.gavitytestseries;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;



import java.util.ArrayList;

import io.github.kexanie.library.MathView;

public class QuestionExpandableListViewAdapter extends BaseExpandableListAdapter {
    ArrayList<QuestionResult> arr;
    LayoutInflater lif;
    Context c;

    public QuestionExpandableListViewAdapter(Context c, ArrayList<QuestionResult> arr)
    {
        this.arr=new ArrayList(arr);
        this.c=c;
        lif=LayoutInflater.from(c);
    }



    @Override
    public int getGroupCount() {
        return arr.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return arr.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view= lif.inflate(R.layout.layout_test_result_group,null);
        }
//        android:id="@+id/test_count"
//        android:id="@+id/subject"
//        android:id="@+id/time"
//        android:id="@+id/marks"
        QResult qr=arr.get(i).qr;
        TextView tv1=view.findViewById(R.id.count);
        tv1.setText(""+(i+1));
//
        TextView tv2=view.findViewById(R.id.subject);
        tv2.setText(qr.subject);

        TextView tv3=view.findViewById(R.id.time);
        tv3.setText(""+qr.time);

        TextView tv4=view.findViewById(R.id.marks);
        tv4.setText(""+qr.marksObtained);




        return view;

    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view= lif.inflate(R.layout.layout_test_result_container,null);
        }

        Question q=arr.get(i).q;

        WebView tv1=view.findViewById(R.id.qstns);
        tv1.getSettings().setJavaScriptEnabled(true);
        tv1.getSettings().setAllowContentAccess(true);
        tv1.loadUrl("file:///android_asset/www/math.html");

//        tv1.config("MathJax.Hub.Config({tex2jax: {inlineMath:[['$','$'],processEscapes: true}});");
//        int first,last;
//        first=q.question.indexOf("$");

//        q.question=q.question.replaceFirst("$","\\(");
//        q.question=q.question.replaceAll("<BR>"," ");
//        q.question=q.question.replaceAll("<br>"," ");
//        q.question=q.question.replace("$","$$");
//        q.question=q.question.replace("$","$$");
////        q.question=q.question.replaceAll("[\\\\]","\\\\");
//        Log.i("quest"+i,q.question);
//        tv1.setText(q.question);
//        tv1.setText("$\\mathop {Lim}\\limits_{x \\to \\infty } ({x^{ - 3}}\\sin 3x + a{x^{ - 2}} + b)$");
//        .replace("$","$$")

//        MathView tv2=view.findViewById(R.id.sltns);
//        tv2.setText(q.solution.text);
//        tv2.config("MathJax.Hub.Config({\n tex2jax: {\ninlineMath:[[\'$\',\'$\'],[\'\\\\(\',\'\\\\)\']],\nprocessEscapes: true\n}\n});");


        return view;
    }


    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}

package org.gravityclasses.gavitytestseries;

import android.content.Context;
import android.graphics.Color;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
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
        tv2.setText(qr.subject.toUpperCase().charAt(0)+"");

        TextView tv3=view.findViewById(R.id.time);
        tv3.setText(""+qr.time+"sec");

        TextView tv4=view.findViewById(R.id.marks);
        tv4.setText(""+qr.marksObtained);

        if(qr.marksObtained>0)
        {

            tv1.setTextColor(Color.GREEN);

            tv4.setTextColor(Color.GREEN);

        }
        else if(qr.marksObtained<0)
        {

            tv1.setTextColor(Color.RED);

            tv4.setTextColor(Color.RED);
        }
        else
        {
            tv1.setTextColor(Color.GRAY);

            tv4.setTextColor(Color.GRAY);
        }




        return view;

    }


    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);

    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view= lif.inflate(R.layout.layout_test_result_container,null);
        }
        ExpandableListView elv=(ExpandableListView)viewGroup;
//        Log.i("elv2","tag::"+i);
//        for(int x =0; x<getGroupCount() ; x++)
//        {
//            if(x!=i)
//            {
//                elv.collapseGroup(x);
//                Log.i("elv","closing"+x);
//            }
//        }

        Question q=arr.get(i).q;

        WebView tv1=view.findViewById(R.id.qstns);
        tv1.getSettings().setJavaScriptEnabled(true);
        tv1.getSettings().setAllowContentAccess(true);
        tv1.getSettings().setAllowFileAccess(true);
        tv1.getSettings().setAllowFileAccessFromFileURLs(true);
        tv1.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        String template="<script type=\"text/x-mathjax-config\">\n" +
                "MathJax.Hub.Config({\n" +
                "  tex2jax: {inlineMath: [['$','$'], ['\\\\(','\\\\)']]},\n" +
                "  TeX: { equationNumbers: { autoNumber: \"AMS\" } }\n" +
                "});\n" +
                "</script>\n" +
                "<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>\n" +
                "<body style='width:80%;margin-left:auto;margin-right:auto;'>";
        String res=template+q.question;
        String encodedHtml = Base64.encodeToString(res.getBytes(), Base64.NO_PADDING);
        tv1.loadData(encodedHtml, "text/html", "base64");


        WebView tv2=view.findViewById(R.id.sltns);
        tv2.getSettings().setJavaScriptEnabled(true);
        tv2.getSettings().setAllowContentAccess(true);
        tv2.getSettings().setAllowFileAccess(true);
        tv2.getSettings().setAllowFileAccessFromFileURLs(true);
        tv2.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        template="<script type=\"text/x-mathjax-config\">\n" +
                "MathJax.Hub.Config({\n" +
                "  tex2jax: {inlineMath: [['$','$'], ['\\\\(','\\\\)']]},\n" +
                "  TeX: { equationNumbers: { autoNumber: \"AMS\" } }\n" +
                "});\n" +
                "</script>\n" +
                "<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>\n" +
                "<body style='width:80%;margin-left:auto;margin-right:auto;'>";

        String res2=template+(q.solution.text==null?"--":q.solution.text);Log.i(TAG,""+res2);
        String encodedHtml2 = Base64.encodeToString(res2.getBytes(), Base64.NO_PADDING);
        tv2.loadData(encodedHtml2, "text/html", "base64");


        return view;
    }
    String TAG="QuestionnELVP";

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}

package org.gravityclasses.gavitytestseries;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Vector;

public class QuestionPagerAdapter extends PagerAdapter {
    private Test test;
    String TAG="QuestionPagerAdapter";
    private LayoutInflater inflater;
    private Context context;
    TestPaper tp;
    TestStatus ts;
    Vector<QStatus> vqs;

    int count=0;

    @Override
    public int getCount() {
        Log.i(TAG,"Length "+test.tp.questions.size());
        return count;
    }
    public QuestionPagerAdapter(Context c, Test t){
        context=c;
        inflater= LayoutInflater.from(c);
        test=t;
        tp=test.tp;
        ts=test.ts;
        vqs=ts.status;
        count=test.tp.questions.size();
        Log.i("SlideShowPagerAdapter",test.tp.questions.size()+"");

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //return super.getPageTitle(position);
        return "Q"+(position+1);
    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView=null;
        Question q=tp.questions.get(position);
        QStatus qs;
        int i=0;

        if(tp.questions.get(position).questionType==1){
            SingleQuestion sq=(SingleQuestion)q;
            itemView = inflater.inflate(R.layout.layout_test_question_pager_single, null);
            itemView.findViewById(R.id.selected_1).setVisibility(View.INVISIBLE);
            itemView.findViewById(R.id.selected_2).setVisibility(View.INVISIBLE);
            itemView.findViewById(R.id.selected_3).setVisibility(View.INVISIBLE);
            itemView.findViewById(R.id.selected_4).setVisibility(View.INVISIBLE);
            if(vqs.get(position).attempted==1)
            {
                if(vqs.get(position).choice.equals("1"))
                {
                    itemView.findViewById(R.id.selected_1).setVisibility(View.VISIBLE);
                }
                if(vqs.get(position).choice.equals("2"))
                {
                    itemView.findViewById(R.id.selected_2).setVisibility(View.VISIBLE);
                }
                if(vqs.get(position).choice.equals("3"))
                {
                    itemView.findViewById(R.id.selected_3).setVisibility(View.VISIBLE);
                }
                if(vqs.get(position).choice.equals("4"))
                {
                    itemView.findViewById(R.id.selected_4).setVisibility(View.VISIBLE);
                }
            }

            TextView num=itemView.findViewById(R.id.qstn_number);
            num.setText("Q"+(position+1)+".");
            TextView sub=itemView.findViewById(R.id.qstn_subject);
            sub.setText(sq.subject);
            TextView marking=itemView.findViewById(R.id.qstn_marking_scheme);
            marking.setText(sq.marking);

            if(sq.image!=null)
            {
                ImageView qim=itemView.findViewById(R.id.qstn_image);
                qim.setVisibility(View.VISIBLE);
                Picasso.get().load(Constants.QUESTION_IMAGE_DIRECTORY+sq.image).placeholder(R.drawable.g_logo_latest1).error(R.drawable.oops).into(qim);

            }
            Picasso.get().setLoggingEnabled(true);
            WebView tv=itemView.findViewById(R.id.qstn);
            tv.getSettings().setJavaScriptEnabled(true);
            tv.getSettings().setAllowContentAccess(true);
            tv.getSettings().setAllowFileAccess(true);
            tv.getSettings().setAllowFileAccessFromFileURLs(true);
            tv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            String template="<script type=\"text/x-mathjax-config\">\n" +
                    "MathJax.Hub.Config({\n" +
                    "  tex2jax: {inlineMath: [['$','$'], ['\\\\(','\\\\)']]},\n" +
                    "  TeX: { equationNumbers: { autoNumber: \"AMS\" } }\n" +
                    "});\n" +
                    "</script>\n" +
                    "<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>\n" +
                    "<body style='width:80%;margin-left:auto;margin-right:auto;'><center>";
            String res=template+sq.question+"</center></body>";
            String encodedHtml = Base64.encodeToString(res.getBytes(), Base64.NO_PADDING);
            tv.loadData(encodedHtml, "text/html", "base64");

            CardView[] o={itemView.findViewById(R.id.card_o_1), itemView.findViewById(R.id.card_o_2),itemView.findViewById(R.id.card_o_3),itemView.findViewById(R.id.card_o_4)};
            for (CardView op:o) {
                Holder h=new Holder();
                h.question=position;
                h.option=i;
                op.setTag(h);

                QOption qop=sq.options.get(i);
                if(qop.type.equals("text"))
                {
                    ImageView iv1;
                    WebView tv1=null;
                    if(i==0)
                    {
                       tv1=itemView.findViewById(R.id.option_1_t);
                    }
                    else if(i==1)
                    {
                        tv1=itemView.findViewById(R.id.option_2_t);
                    }
                    else if(i==2)
                    {
                        tv1=itemView.findViewById(R.id.option_3_t);
                    }
                    else if(i==3)
                    {
                        tv1=itemView.findViewById(R.id.option_4_t);
                    }
                    tv1.setVisibility(View.VISIBLE);
                    tv1.getSettings().setJavaScriptEnabled(true);
                    tv1.getSettings().setAllowContentAccess(true);
                    tv1.getSettings().setAllowFileAccess(true);
                    tv1.getSettings().setAllowFileAccessFromFileURLs(true);
                    tv1.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
                    String template1="<script type=\"text/x-mathjax-config\">\n" +
                            "MathJax.Hub.Config({\n" +
                            "  tex2jax: {inlineMath: [['$','$'], ['\\\\(','\\\\)']]},\n" +
                            "  TeX: { equationNumbers: { autoNumber: \"AMS\" } }\n" +
                            "});\n" +
                            "</script>\n" +
                            "<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>\n" +
                            "<body style='width:80%;margin-left:auto;margin-right:auto;'><center>";
                    String res1=template1+qop.value+"</center></body>";
                    String encodedHtml1 = Base64.encodeToString(res1.getBytes(), Base64.NO_PADDING);
                    tv1.loadData(encodedHtml1, "text/html", "base64");
                }
                else if(qop.type.equals("image"))
                {
                    ImageView tv1=null;
                    if(i==0)
                    {
                        tv1=itemView.findViewById(R.id.option_1_i);
                    }
                    else if(i==1)
                    {
                        tv1=itemView.findViewById(R.id.option_2_i);
                    }
                    else if(i==2)
                    {
                        tv1=itemView.findViewById(R.id.option_3_i);
                    }
                    else if(i==3)
                    {
                        tv1=itemView.findViewById(R.id.option_4_i);
                    }
                    Log.i(TAG,"Picasso starting for option:"+i);
                    Picasso.get().load(Constants.QUESTION_IMAGE_DIRECTORY+qop.value.split("/")[2]).placeholder(R.drawable.g_logo_latest1)
                            .error(R.drawable.oops).into(tv1);
                    tv1.setVisibility(View.VISIBLE);
                }
                i++;
            }
//            if(ts!=null && ts.status!=null)
//            {
//                qs=ts.status.get(position);
//                if(qs.attempted==1)
//                {
//
//                }
//
//            }
        }
        else if(tp.questions.get(position).questionType==2){
            itemView = inflater.inflate(R.layout.layout_test_question_pager_single, null);
            if(ts!=null)
            {
                CardView cv=itemView.findViewById(R.id.card_o_1);

            }
        }
        else if(tp.questions.get(position).questionType==3){
            itemView = inflater.inflate(R.layout.layout_test_question_pager_single, null);
            if(ts!=null)
            {
                CardView cv=itemView.findViewById(R.id.card_o_1);

            }
        }
        else if(tp.questions.get(position).questionType==4){
            itemView = inflater.inflate(R.layout.layout_test_question_pager_single, null);
            if(ts!=null)
            {
                CardView cv=itemView.findViewById(R.id.card_o_1);

            }
        }
        else if(tp.questions.get(position).questionType==5){
            itemView = inflater.inflate(R.layout.layout_test_question_pager_single, null);
            if(ts!=null)
            {
                CardView cv=itemView.findViewById(R.id.card_o_1);

            }
        }
        else if(tp.questions.get(position).questionType==6){
            itemView = inflater.inflate(R.layout.layout_test_question_pager_single, null);
            if(ts!=null)
            {
                CardView cv=itemView.findViewById(R.id.card_o_1);

            }
        }
        else if(tp.questions.get(position).questionType==7){
            itemView = inflater.inflate(R.layout.layout_test_question_pager_single, null);
            if(ts!=null)
            {


            }
        }
        else if(tp.questions.get(position).questionType==8){
            itemView = inflater.inflate(R.layout.layout_test_question_pager_single, null);
            if(ts!=null)
            {


            }
        }
        else if(tp.questions.get(position).questionType==9){
            itemView = inflater.inflate(R.layout.layout_test_question_pager_single, null);
            if(ts!=null)
            {


            }
        }
        else if(tp.questions.get(position).questionType==10){
            itemView = inflater.inflate(R.layout.layout_test_question_pager_single, null);
            if(ts!=null)
            {


            }
        }
        else if(tp.questions.get(position).questionType==11){
            itemView = inflater.inflate(R.layout.layout_test_question_pager_single, null);
            if(ts!=null)
            {


            }
        }


        container.addView(itemView);
        Log.i(TAG,"instantiate"+position);
        return itemView;
    }

class Holder{
        public int question,option;


    }
}

package org.gravityclasses.gavitytestseries;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class QuestionPagerAdapter extends PagerAdapter {
    private Test test;
    String TAG="QuestionPagerAdapter";
    private LayoutInflater inflater;
    private Context context;
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
        View itemView = inflater.inflate(R.layout.layout_test_question_pager, null);
        container.addView(itemView);
        Log.i(TAG,"instantiate"+position);
        return itemView;


    }


}

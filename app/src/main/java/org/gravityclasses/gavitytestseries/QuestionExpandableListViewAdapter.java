package org.gravityclasses.gavitytestseries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;

public class QuestionExpandableListViewAdapter extends BaseExpandableListAdapter {
    ArrayList<QuestionResult> arr;
    LayoutInflater lif;
    Context c;

    public QuestionExpandableListViewAdapter(Context c, ArrayList arr)
    {
        this.arr=new ArrayList<>(arr);
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


        return view;

    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view= lif.inflate(R.layout.layout_test_result_container,null);
        }


        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}

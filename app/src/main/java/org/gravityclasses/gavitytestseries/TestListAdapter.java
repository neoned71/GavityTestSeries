package org.gravityclasses.gavitytestseries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TestListAdapter extends BaseAdapter {
    ArrayList<TestThumbnail> list;
    Context c;
    LayoutInflater lif;

    public TestListAdapter(Context c,ArrayList<TestThumbnail> list)
    {
        this.list=list;
        this.c=c;
        lif=LayoutInflater.from(c);

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
           view= lif.inflate(R.layout.layout_test_item,null);
        }
        TestThumbnail tt=list.get(i);
        /*
        android:id="@+id/test_count"
        android:id="@+id/test_name"
        android:id="@+id/marks"
        android:id="@+id/rank"
        android:id="@+id/subject"



         */
        TextView tv1=view.findViewById(R.id.test_count);
        tv1.setText(""+(i+1));
        TextView tv2=view.findViewById(R.id.test_name);
        tv2.setText(tt.name);
        int  type=0;
        if(tt.tr!=null){
            TextView tv3=view.findViewById(R.id.marks);
            tv3.setText(tt.tr.marksObtained+"");
            TextView tv4=view.findViewById(R.id.rank);
            tv4.setText(tt.tr.rank+"");
            type=1;
        }
        view.findViewById(R.id.proceed).setTag(new TestListAdapter.ViewHolder(tt.id,type));
        return view;
    }

    class ViewHolder{
        int testId;
        int type;

        public ViewHolder(int testId,int type)
        {
            this.type=type;
            this.testId=testId;
        }
    }
}

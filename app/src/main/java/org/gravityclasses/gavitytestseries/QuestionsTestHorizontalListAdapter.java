package org.gravityclasses.gavitytestseries;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Vector;

public class QuestionsTestHorizontalListAdapter extends RecyclerView.Adapter<QuestionsTestHorizontalListAdapter.MyView> {
    Vector<QStatus> vqs;
    int current;
    public QuestionsTestHorizontalListAdapter(Vector<QStatus> vqs)
    {
        this.vqs=vqs;
    }

    public class MyView extends RecyclerView.ViewHolder {
        public TextView title;


        public MyView(View view,int i) {
            super(view);
            title=view.findViewById(R.id.tv);

        }
    }



    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_test_question_list, viewGroup, false);

        return new MyView(itemView,i);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsTestHorizontalListAdapter.MyView myView, int i) {
        TextView title=myView.title;
        title.setTag(i);
        if(current==i){
            title.setBackgroundResource(R.drawable.background_circular_white);
        }
        else if(vqs.get(i).visited==1 && vqs.get(i).attempted!=1)
        {
            title.setBackgroundResource(R.drawable.background_circular_red);
        }
        else if(vqs.get(i).attempted==1)
        {
            title.setBackgroundResource(R.drawable.background_circular_green);
        }
        else
        {
            title.setBackgroundResource(R.drawable.background_circular_black);
        }
        title.setText(String.format("%02d", (i+1)));
    }

    public void setCurrent(int i){
        current=i;
    }

    @Override
    public int getItemCount() {
        return vqs.size();
    }
}

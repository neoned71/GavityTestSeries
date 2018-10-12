package org.gravityclasses.gavitytestseries;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class SlideShowPagerAdapter extends PagerAdapter {
    private ArrayList<Integer> IMAGES;
    private LayoutInflater inflater;
    private Context context;

    @Override
    public int getCount() {
        return IMAGES.size();
    }
    public SlideShowPagerAdapter(Context c,ArrayList<Integer> a){
        context=c;
        inflater= LayoutInflater.from(c);
        IMAGES = new ArrayList<>(a);
        Log.i("SlideShowPagerAdapter",IMAGES.size()+"");

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View itemView = inflater.inflate(R.layout.layout_item_slideshow, null, false);

        ImageView imageView = itemView.findViewById(R.id.image);
        imageView.setImageResource(IMAGES.get(position));


        container.addView(itemView);
        return itemView;


    }


}

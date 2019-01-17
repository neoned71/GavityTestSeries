package org.gravityclasses.gavitytestseries;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NewCardView extends CardView {
    public NewCardView(@NonNull Context context) {
        super(context);
    }

    public NewCardView(@NonNull Context context,@NonNull AttributeSet attributeSet) {
        super(context,attributeSet);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}

package cn.monica.missionimpossible.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.hedgehog.ratingbar.RatingBar;

public class MyRatingBar extends RatingBar {
    private float count = 0;
    public MyRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                count =  RatingCount;
            }
        });
    }

    public float getCount() {
        return count;
    }
}

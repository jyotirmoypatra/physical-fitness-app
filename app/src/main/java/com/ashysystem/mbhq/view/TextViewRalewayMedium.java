package com.ashysystem.mbhq.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by android-krishnendu on 5/4/16.
 */
public class TextViewRalewayMedium extends TextView {

    public TextViewRalewayMedium(Context context) {
        super(context);
        overrideWithCustomFont();
    }

    public TextViewRalewayMedium(Context context, AttributeSet attrs) {
        super(context, attrs);overrideWithCustomFont();
    }

    public TextViewRalewayMedium(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        overrideWithCustomFont();
    }

    private void overrideWithCustomFont() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Raleway-Medium.ttf");
            setTypeface(tf,0);
        }
    }
}

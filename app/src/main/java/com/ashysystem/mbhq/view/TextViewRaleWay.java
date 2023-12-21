package com.ashysystem.mbhq.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class TextViewRaleWay extends TextView {

    public TextViewRaleWay(Context context) {
        super(context);
        overrideWithCustomFont();
    }

    public TextViewRaleWay(Context context, AttributeSet attrs) {
        super(context, attrs);overrideWithCustomFont();
        overrideWithCustomFont();
    }

    public TextViewRaleWay(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        overrideWithCustomFont();
    }

    private void overrideWithCustomFont() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Raleway-Regular.ttf");
            setTypeface(tf,0);
        }
    }
}

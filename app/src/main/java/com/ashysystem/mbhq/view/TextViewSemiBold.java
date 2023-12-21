package com.ashysystem.mbhq.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by android-krishnendu on 13/1/16.
 */
@SuppressLint("AppCompatCustomView")
public class TextViewSemiBold extends TextView {
    public TextViewSemiBold(Context context) {
        super(context);
        overrideWithCustomFont();
    }

    public TextViewSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        overrideWithCustomFont();
    }

    public TextViewSemiBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        overrideWithCustomFont();

    }

    private void overrideWithCustomFont() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Raleway-SemiBold.ttf");
            setTypeface(tf,0);
        }
    }
}

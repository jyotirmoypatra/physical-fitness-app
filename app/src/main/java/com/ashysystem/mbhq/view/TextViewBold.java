package com.ashysystem.mbhq.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by android-krishnendu on 7/4/16.
 */
public class TextViewBold extends TextView {
    public TextViewBold(Context context) {
        super(context);
        overrideWithCustomFont();
    }

    public TextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);overrideWithCustomFont();
    }

    public TextViewBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        overrideWithCustomFont();
    }

    private void overrideWithCustomFont() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Raleway-Bold.ttf");
            setTypeface(tf,0);
        }
    }
}

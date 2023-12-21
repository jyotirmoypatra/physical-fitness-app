package com.ashysystem.mbhq.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by android-krishnendu on 27/12/17.
 */

@SuppressLint("AppCompatCustomView")
public class TextViewOswaldRegular extends TextView {

    public TextViewOswaldRegular(Context context) {
        super(context);
        overrideWithCustomFont();
    }

    public TextViewOswaldRegular(Context context, AttributeSet attrs) {
        super(context, attrs);overrideWithCustomFont();
        overrideWithCustomFont();
    }

    public TextViewOswaldRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        overrideWithCustomFont();
    }

    private void overrideWithCustomFont() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Oswald-Regular.ttf");
            setTypeface(tf,0);
        }
    }

}

package com.ashysystem.mbhq.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by android-krishnendu on 28/12/17.
 */

@SuppressLint("AppCompatCustomView")
public class TextViewOswaldExtraLight extends TextView {

    public TextViewOswaldExtraLight(Context context) {
        super(context);
        overrideWithCustomFont();
    }

    public TextViewOswaldExtraLight(Context context, AttributeSet attrs) {
        super(context, attrs);overrideWithCustomFont();
        overrideWithCustomFont();
    }

    public TextViewOswaldExtraLight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        overrideWithCustomFont();
    }

    private void overrideWithCustomFont() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Oswald-ExtraLight.ttf");
            setTypeface(tf,0);
        }
    }

}

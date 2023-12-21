package com.ashysystem.mbhq.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by android-krishnendu on 26/12/17.
 */

@SuppressLint("AppCompatCustomView")
public class TextViewOswaldRegularItalic extends TextView {

    public TextViewOswaldRegularItalic(Context context) {
        super(context);
        overrideWithCustomFont();
    }

    public TextViewOswaldRegularItalic(Context context, AttributeSet attrs) {
        super(context, attrs);overrideWithCustomFont();
        overrideWithCustomFont();
    }

    public TextViewOswaldRegularItalic(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        overrideWithCustomFont();
    }

    private void overrideWithCustomFont() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Oswald-RegularItalic.ttf");
            setTypeface(tf,0);
        }
    }

}

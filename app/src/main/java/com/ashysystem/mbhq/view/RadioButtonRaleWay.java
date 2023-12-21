package com.ashysystem.mbhq.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * Created by android-krishnendu on 9/3/16.
 */
public class RadioButtonRaleWay extends RadioButton {
    public RadioButtonRaleWay(Context context) {
        super(context);
        overrideWithCustomFont();
    }

    public RadioButtonRaleWay(Context context, AttributeSet attrs) {
        super(context, attrs);
        overrideWithCustomFont();
    }

    public RadioButtonRaleWay(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        overrideWithCustomFont();
    }

    private void overrideWithCustomFont() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Raleway-Bold.ttf");
            setTypeface(tf);
        }
    }
}

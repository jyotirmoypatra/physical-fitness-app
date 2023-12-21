package com.ashysystem.mbhq.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class CheckBoxOswaldRegular extends CheckBox {
    public CheckBoxOswaldRegular(Context context) {
        super(context);
    }

    public CheckBoxOswaldRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckBoxOswaldRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        overrideWithCustomFont();
    }

    private void overrideWithCustomFont() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Oswald-Regular.ttf");
            setTypeface(tf);
        }
    }
}

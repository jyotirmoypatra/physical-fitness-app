package com.ashysystem.mbhq.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;

public class CheckBoxRaleWay extends AppCompatCheckBox {
    public CheckBoxRaleWay(Context context) {
        super(context);
    }

    public CheckBoxRaleWay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckBoxRaleWay(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        overrideWithCustomFont();
    }

    private void overrideWithCustomFont() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Raleway-Regular.ttf");
            setTypeface(tf);
        }
    }
}

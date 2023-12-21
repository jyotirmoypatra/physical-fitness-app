package com.ashysystem.mbhq.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class EdittextSemiBold extends AppCompatEditText {
    public EdittextSemiBold(Context context) {
        super(context);
    }

    public EdittextSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EdittextSemiBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        overrideWithCustomFont();
    }

    private void overrideWithCustomFont() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Raleway-SemiBold.ttf");
            setTypeface(tf);
        }
    }
}

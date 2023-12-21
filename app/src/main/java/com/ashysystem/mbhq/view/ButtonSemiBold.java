package com.ashysystem.mbhq.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class ButtonSemiBold extends AppCompatButton {
    public ButtonSemiBold(Context context) {
        super(context);
    }

    public ButtonSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonSemiBold(Context context, AttributeSet attrs, int defStyleAttr) {
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

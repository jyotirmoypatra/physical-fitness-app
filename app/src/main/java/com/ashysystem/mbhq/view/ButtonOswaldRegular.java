package com.ashysystem.mbhq.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by AQB Solutions PVT. LTD. on 8/6/17.
 */

@SuppressLint("AppCompatCustomView")
public class ButtonOswaldRegular extends Button {
    public ButtonOswaldRegular(Context context) {
        this(context, null, 0);
    }

    public ButtonOswaldRegular(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ButtonOswaldRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpFont();
    }

    private void setUpFont() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Oswald-Regular.ttf");
            setTypeface(tf, 1);
        }
    }
}

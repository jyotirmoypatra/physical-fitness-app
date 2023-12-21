package com.ashysystem.mbhq.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;

import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * Created by AQB Solutions PVT. LTD. on 7/6/17.
 */

@SuppressLint("AppCompatCustomView")
public class TextViewRobotoBold extends TextView {
    public TextViewRobotoBold(Context context) {
        this(context, null, 0);
    }

    public TextViewRobotoBold(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewRobotoBold(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpFont();
    }

    private void setUpFont() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Bold.ttf");
            setTypeface(tf, 1);
        }
    }
}

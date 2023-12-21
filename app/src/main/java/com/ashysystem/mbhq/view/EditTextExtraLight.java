package com.ashysystem.mbhq.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by android-krishnendu on 9/1/18.
 */

public class EditTextExtraLight extends EditText {
    public EditTextExtraLight(Context context) {
        super(context);
        overrideWithCustomFont();
    }

    public EditTextExtraLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        overrideWithCustomFont();
    }

    public EditTextExtraLight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        overrideWithCustomFont();
    }

    private void overrideWithCustomFont() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Oswald-ExtraLight.ttf");
            setTypeface(tf);
        }
    }
}
